    package com.cassandra;

    import java.io.BufferedReader;
    import java.io.File;
    import java.io.FileReader;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.Map;

    import com.cassandra.transactions.*;
    import org.apache.log4j.BasicConfigurator;

    import com.datastax.driver.core.BatchStatement;
    import com.datastax.driver.core.Cluster;
    import com.datastax.driver.core.Metadata;
    import com.datastax.driver.core.PreparedStatement;
    import com.datastax.driver.core.Session;
    import com.datastax.driver.core.Statement;
    import org.apache.log4j.Logger;
    import org.apache.log4j.PropertyConfigurator;

    public class TransactionDriver{

        public static final String dir = "C:\\DD\\D8-data\\";
        public static final String transactionDir = "C:\\DD\\D8-xact-revised\\";
        private static Logger logger = Logger.getLogger(CassandraSession.class);


        private static Cluster cluster;
        private static Session session;
        private static String keyspace;

        public static void main(String args[])
        {
            TransactionDriver t = new TransactionDriver();
            t.init();
            t.readTransactionFiles();
        }

        public  void init()
        {
            ClassLoader classLoader = CassandraInit.class.getClassLoader();
            PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));
            Session session = CassandraSession.getSession();
            if (session == null) {
                logger.error("Could not create a session");
                return;
            }
        }

        public void readTransactionFiles()
        {
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
                        int w_id = 0;int d_id =0;int c_id =0;
                        double payment = 0;
                        int carrier_id = 0;
                        int threshold = 0;int lastLOrders = 0;
                        switch(tranType)
                        {
                            case "P":
                                w_id = Integer.parseInt(content[1]);
                                d_id = Integer.parseInt(content[2]);
                                c_id = Integer.parseInt(content[3]);
                                payment = Double.parseDouble(content[4]);
                                new PaymentTransaction().readOrderStatus(w_id,d_id,c_id,payment);
                            case "D":
                                w_id = Integer.parseInt(content[1]);
                                carrier_id = Integer.parseInt(content[2]);
                                new DeliveryTransaction().readDeliveryTransaction(w_id,carrier_id);
                            case "O":
                                w_id = Integer.parseInt(content[1]);
                                d_id = Integer.parseInt(content[2]);
                                c_id = Integer.parseInt(content[3]);
                                new OrderStatusTransaction().readOrderStatus(w_id,d_id,c_id);
                            case "S":
                                w_id = Integer.parseInt(content[1]);
                                d_id = Integer.parseInt(content[2]);
                                threshold = Integer.parseInt(content[3]);
                                lastLOrders = Integer.parseInt(content[4]);
                                new StockLevelTransaction().checkStockThreshold(w_id,d_id,threshold,lastLOrders);
                            case "I":
                                w_id = Integer.parseInt(content[1]);
                                d_id = Integer.parseInt(content[2]);
                                lastLOrders = Integer.parseInt(content[4]);
                                new PopularItemTransaction().checkPopularItem(w_id,d_id,lastLOrders);
                            case "T":
                                w_id = Integer.parseInt(content[1]);
                                d_id = Integer.parseInt(content[2]);
                                c_id = Integer.parseInt(content[3]);
                                new TopBalanceTransaction().gettopBalance(w_id,d_id,c_id);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }