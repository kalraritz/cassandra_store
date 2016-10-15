package com.cassandra;

import com.cassandra.transactions.NewOrderTransaction;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;

import com.cassandra.beans.Item;
import com.cassandra.beans.ItemCodec;
import com.cassandra.transactions.*;
import com.cassandra.utilities.Lucene;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class TransactionDriver extends Thread {


    private static Logger logger = Logger.getLogger(TransactionDriver.class);
    private Session session;
    private Lucene lucene;
    private PrintWriter printWriter;
    private String threadName;
    private Date startTimeOfTransaction;
    private Date endTimeOfTransaction;
    private Thread thread;
    private int currentThread;
    private String transactionDir;


    public TransactionDriver(Session session, Lucene lucene, PrintWriter printWriter, String threadName, Date startTime, int currentThread, String transactionDir) {
        this.session = session;
        this.lucene = lucene;
        this.printWriter = printWriter;
        this.threadName = threadName;
        this.startTimeOfTransaction = startTime;
        this.currentThread = currentThread;
        this.transactionDir = transactionDir;

    }

    public void run() {
        int noOfTransactionsExecuted = readTransactionFiles(lucene, printWriter);
        endTimeOfTransaction = new Date();
        logger.info("Ended executing transactions for the thread ::"+ threadName);
        //Calculating time for transactions...
        this.endTimeOfTransaction = new Date();
        long transactionTimeInMillis = endTimeOfTransaction.getTime() - startTimeOfTransaction.getTime();
        TransactionClient.totalNumberOfTransactionsProcessed += noOfTransactionsExecuted;
        logger.info("Total Number of transactions processed: "+ TransactionClient.totalNumberOfTransactionsProcessed);
        double throuput = (double)TransactionClient.totalNumberOfTransactionsProcessed/transactionTimeInMillis;
        double throughputPerSec = throuput/1000;
        logger.info("Transaction throughput (number of transactions processed per second)::"+ throughputPerSec);
        session.close();
        System.exit(0);

    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }


    public int readTransactionFiles(Lucene lucene, PrintWriter printWriter) {
        logger.info("Started executing transactions for the thread ::"+ threadName);
        int noOfTransactionsExecuted = 0;
        String line = "";
        int cnt = 0;
        int ncnt = 0;
        String fname = currentThread + ".txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(transactionDir + fname));
            while ((line = br.readLine()) != null) {
                String[] content = line.split(",");
                String tranType = content[0];
                int w_id = 0;
                int d_id = 0;
                int c_id = 0;
                double payment = 0;
                int carrier_id = 0;
                double threshold = 0.0;
                int lastLOrders = 0;
                switch (tranType) {
//                    case "N":
//                        ncnt++;
//                        if (ncnt % 1000 == 0)
//                            System.out.println(ncnt + " N ran out of " + cnt);
//                        c_id = Integer.parseInt(content[1]);
//                        w_id = Integer.parseInt(content[2]);
//                        d_id = Integer.parseInt(content[3]);
//                        int m = Integer.parseInt(content[4]);
//                        ArrayList<String> itemlineinfo = new ArrayList<String>();
//                        // read m line
//                        while (m > 0) {
//                            --m;
//                            String itemline = br.readLine();
//                            if (!(itemline == null))
//                                itemlineinfo.add(itemline);
//                        }
//                        new NewOrderTransaction().newOrderTransaction(w_id, d_id, c_id, itemlineinfo, session, lucene, printWriter);
//                        noOfTransactionsExecuted++;
//                        break;
                    case "P":
                        w_id = Integer.parseInt(content[1]);
                        d_id = Integer.parseInt(content[2]);
                        c_id = Integer.parseInt(content[3]);
                        payment = Double.parseDouble(content[4]);
                        new PaymentTransaction().readOrderStatus(w_id, d_id, c_id, payment, session, lucene, printWriter);
                        noOfTransactionsExecuted++;
                        break;
                    case "D":
                        w_id = Integer.parseInt(content[1]);
                        carrier_id = Integer.parseInt(content[2]);
                        new DeliveryTransaction().readDeliveryTransaction(w_id, carrier_id, session, printWriter);
                        noOfTransactionsExecuted++;
                        break;
                    case "O":
                        w_id = Integer.parseInt(content[1]);
                        d_id = Integer.parseInt(content[2]);
                        c_id = Integer.parseInt(content[3]);
                        new OrderStatusTransaction().readOrderStatus(w_id, d_id, c_id, session, lucene, printWriter);
                        noOfTransactionsExecuted++;
                        break;
                    case "S":
                        w_id = Integer.parseInt(content[1]);
                        d_id = Integer.parseInt(content[2]);
                        threshold = Integer.parseInt(content[3]);
                        lastLOrders = Integer.parseInt(content[4]);
                        new StockLevelTransaction().checkStockThreshold(w_id, d_id, threshold, lastLOrders, session, lucene, printWriter);
                        noOfTransactionsExecuted++;
                        break;
                    case "I":
                        w_id = Integer.parseInt(content[1]);
                        d_id = Integer.parseInt(content[2]);
                        lastLOrders = Integer.parseInt(content[3]);
                        new PopularItemTransaction().checkPopularItem(w_id, d_id, lastLOrders, session, printWriter);
                        noOfTransactionsExecuted++;
                        break;
                    case "T":
                        new TopBalanceTransaction().getTopBalance(session, printWriter);
                        noOfTransactionsExecuted++;
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Failure in excecuting transaction for thread: "+ threadName);
        }
        System.exit(0);

    return noOfTransactionsExecuted;
    }

    // OLD read trasnactionFiles method (kept as a backup)
//    public void readTransactionFiles(Lucene lucene, PrintWriter printWriter) {
//        File folder = new File(transactionDir);
//        File[] listOfFiles = folder.listFiles();
//        String line = "";
//        int cnt = 0;
//        int ncnt = 0;
//        Date dateobj = new Date();
//        for (File file : listOfFiles) {
//            String fname = file.getName();
//            try (BufferedReader br = new BufferedReader(new FileReader(transactionDir + fname))) {
//                while ((line = br.readLine()) != null) {
//                    cnt++;
//                    if(cnt % 1000 == 0) {
//                        Date dateobj1 = new Date();
//                        long diff = dateobj1.getTime() - dateobj.getTime();
//                        System.out.println(cnt + " Time : " + diff/60000 + " min");
//                    }
//                    String[] content = line.split(",");
//                    String tranType = content[0];
//                    int w_id = 0;
//                    int d_id = 0;
//                    int c_id = 0;
//                    double payment = 0;
//                    int carrier_id = 0;
//                    double threshold = 0.0;
//                    int lastLOrders = 0;
//                    switch (tranType) {
//                        case "N":
//                            ncnt++;
//                            if(ncnt % 1000 == 0)
//                                System.out.println(ncnt + " N ran out of " + cnt);
//                            c_id = Integer.parseInt(content[1]);
//                            w_id = Integer.parseInt(content[2]);
//                            d_id = Integer.parseInt(content[3]);
//                            int m = Integer.parseInt(content[4]);
//                            ArrayList<String> itemlineinfo = new ArrayList<String>();
//                            // read m line
//                            while (m>0) {
//                                --m;
//                                String itemline = br.readLine();
//                                if(!(itemline == null))
//                                    itemlineinfo.add(itemline);
//                            }
//                            new NewOrderTransaction().newOrderTransaction(w_id, d_id, c_id, itemlineinfo, session,lucene, printWriter);
//                            break;
//                        case "P":
//                            w_id = Integer.parseInt(content[1]);
//                            d_id = Integer.parseInt(content[2]);
//                            c_id = Integer.parseInt(content[3]);
//                            payment = Double.parseDouble(content[4]);
//                            new PaymentTransaction().readOrderStatus(w_id,d_id,c_id,payment,session,lucene, printWriter);
//                            break;
//                        case "D":
//                            w_id = Integer.parseInt(content[1]);
//                            carrier_id = Integer.parseInt(content[2]);
//                            new DeliveryTransaction().readDeliveryTransaction(w_id,carrier_id,session, printWriter);
//                            break;
//                        case "O":
//                            w_id = Integer.parseInt(content[1]);
//                            d_id = Integer.parseInt(content[2]);
//                            c_id = Integer.parseInt(content[3]);
//                            new OrderStatusTransaction().readOrderStatus(w_id,d_id,c_id,session,lucene, printWriter);
//                            break;
//                        case "S":
//                            w_id = Integer.parseInt(content[1]);
//                            d_id = Integer.parseInt(content[2]);
//                            threshold = Integer.parseInt(content[3]);
//                            lastLOrders = Integer.parseInt(content[4]);
//                            new StockLevelTransaction().checkStockThreshold(w_id, d_id, threshold, lastLOrders, session,lucene, printWriter);
//                            break;
//                        case "I":
//                            w_id = Integer.parseInt(content[1]);
//                            d_id = Integer.parseInt(content[2]);
//                            lastLOrders = Integer.parseInt(content[3]);
//                            new PopularItemTransaction().checkPopularItem(w_id,d_id,lastLOrders,session, printWriter);
//                            break;
//                        case "T":
//                            new TopBalanceTransaction().getTopBalance(session, printWriter);
//                            break;
//                    }
//                }
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println(cnt);
//            Date dateobj1 = new Date();
//            long diff = dateobj1.getTime() - dateobj.getTime();
//            System.out.println("Time : "+diff);
//            System.exit(0);
//        }
//
//    }

//    public static void main(String args[]) {
//        Lucene lucene = new Lucene();
//        try {
//            String configFilePath = System.getenv("DD_CONFIG_FILE");
//            //export DD_CONFIG_FILE="/Users/ritesh/Documents/projects/nus/config.properties"
//            InputStream inputStream = new FileInputStream(configFilePath);
//            Properties properties = new Properties();
//            properties.load(inputStream);
//            String output_path = properties.getProperty("output_path");
//            pw = new PrintWriter(new File(output_path));
//            dir = properties.getProperty("csv_files_path");
//            transactionDir = properties.getProperty("transactions_dir");
//            lucene.initSearch(properties);
//            ClassLoader classLoader = CassandraInit.class.getClassLoader();
//            PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));
//            session = CassandraSession.getSession(properties);
//
//            if (session == null) {
//                logger.error("Could not create a session");
//                return;
//            }
//            TransactionDriver t = new TransactionDriver();
//            t.readTransactionFiles(lucene);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            CassandraSession.closeSession();
//        }
//
//
//    }

}
