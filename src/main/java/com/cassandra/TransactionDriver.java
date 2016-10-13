package com.cassandra;

import com.cassandra.CassandraInit;
import com.cassandra.CassandraSession;
import com.cassandra.transactions.NewOrderTransaction;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

import com.cassandra.beans.Item;
import com.cassandra.beans.ItemCodec;
import com.cassandra.transactions.*;
import com.cassandra.utilities.Lucene;
import com.datastax.driver.core.*;
import org.apache.log4j.BasicConfigurator;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TransactionDriver {

    public static String dir = null;
    public static String transactionDir=null;
    private static Logger logger = Logger.getLogger(CassandraSession.class);


    private static Cluster cluster;
    private static Session session;
    private static String keyspace;

    public static void main(String args[]) {
        Lucene lucene = new Lucene();
        try {
            InputStream inputStream = new FileInputStream("/Users/manisha/NUS/DD/gitProjects/cassandra_store/src/main/resources/config.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            dir = properties.getProperty("csv_files_path");
            transactionDir = properties.getProperty("transactions_dir");
            lucene.initSearch(properties);
            ClassLoader classLoader = CassandraInit.class.getClassLoader();
            PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));
            session = CassandraSession.getSession(properties);

            if (session == null) {
                logger.error("Could not create a session");
                return;
            }
            TransactionDriver t = new TransactionDriver();
            t.readTransactionFiles(lucene);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CassandraSession.closeSession();
        }


    }

    public void readTransactionFiles(Lucene lucene) {
        File folder = new File(transactionDir);
        File[] listOfFiles = folder.listFiles();
        String line = "";
        for (File file : listOfFiles) {
            ArrayList<String> inputs = new ArrayList<String>();
            String fname = file.getName();
            try (BufferedReader br = new BufferedReader(new FileReader(transactionDir + fname))) {
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
//                            case "N":
//                                c_id = Integer.parseInt(content[1]);
//                                w_id = Integer.parseInt(content[2]);
//                                d_id = Integer.parseInt(content[3]);
//                                int m = Integer.parseInt(content[4]);
//                                ArrayList<String> itemlineinfo = new ArrayList<String>();
//                                // read m line
//                                while (m>0) {
//                                    --m;
//                                    String itemline = br.readLine();
//                                    if(itemline == null)
//                                        throw new RuntimeException("items empty before iteration line->" + line);
//                                    itemlineinfo.add(itemline);
//                                }
//                                new NewOrderTransaction().newOrderTransaction(w_id, d_id, c_id, itemlineinfo, session);
//
//                            case "P":
//                                w_id = Integer.parseInt(content[1]);
//                                d_id = Integer.parseInt(content[2]);
//                                c_id = Integer.parseInt(content[3]);
//                                payment = Double.parseDouble(content[4]);
//                                new PaymentTransaction().readOrderStatus(w_id,d_id,c_id,payment,session);
//
                            case "D":
                                w_id = Integer.parseInt(content[1]);
                                carrier_id = Integer.parseInt(content[2]);
                                new DeliveryTransaction().readDeliveryTransaction(w_id,carrier_id,session);
//
//                            case "O":
//                                w_id = Integer.parseInt(content[1]);
//                                d_id = Integer.parseInt(content[2]);
//                                c_id = Integer.parseInt(content[3]);
//                                new OrderStatusTransaction().readOrderStatus(w_id,d_id,c_id,session,lucene);
////
//                        case "S":
//                            w_id = Integer.parseInt(content[1]);
//                            d_id = Integer.parseInt(content[2]);
//                            threshold = Integer.parseInt(content[3]);
//                            lastLOrders = Integer.parseInt(content[4]);
//                            new StockLevelTransaction().checkStockThreshold(w_id, d_id, threshold, lastLOrders, session);
//                            case "I":
//                                w_id = Integer.parseInt(content[1]);
//                                d_id = Integer.parseInt(content[2]);
//                                lastLOrders = Integer.parseInt(content[4]);
//                                new PopularItemTransaction().checkPopularItem(w_id,d_id,lastLOrders,session);
//                            case "T":
//                                w_id = Integer.parseInt(content[1]);
//                                d_id = Integer.parseInt(content[2]);
//                                c_id = Integer.parseInt(content[3]);
//                                new TopBalanceTransaction().gettopBalance(w_id,d_id,c_id,session);
                    }
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
