package com.cassandra;

import com.cassandra.utilities.Lucene;
import com.datastax.driver.core.Session;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

/**
 * Created by manisha on 16/10/2016.
 */
public class TransactionClient {

    private static Logger logger = Logger.getLogger(TransactionClient.class);

    public static int totalNumberOfTransactionsProcessed = 0;
    public static void main(String[] args) {
        try{
            String configFilePath = System.getenv("DD_CONFIG_FILE");
            //export DD_CONFIG_FILE="/Users/ritesh/Documents/projects/nus/config.properties"
            InputStream inputStream = new FileInputStream(configFilePath);
            Properties properties = new Properties();
            properties.load(inputStream);
            Session session = CassandraSession.getSession(properties);
            Lucene lucene = new Lucene();
            lucene.initSearch(properties);
            PrintWriter printWriter = new PrintWriter(new File(properties.getProperty("output_path")));
            String transactionDir = properties.getProperty("transactions_dir");
           // System.out.println("Enter the number of clients:");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            int numberOfClients = Integer.parseInt(bufferedReader.readLine());
            logger.info("---------------Starting executing transactions for "+ numberOfClients+" clients------------------------");
            TransactionDriver[] drivers = new TransactionDriver[numberOfClients];
            long startTime = System.currentTimeMillis();
            int i = Integer.parseInt(args[0]);
            new TransactionDriver(session, lucene, printWriter, "thread_"+i, new Date(), i, transactionDir).run();
            /*
            for(int i=0; i<numberOfClients; i++){
                drivers[i] = new TransactionDriver(session, lucene, printWriter, "thread_"+i, new Date(), i, transactionDir);
                drivers[i].start();
            }
            for(int i=0; i<numberOfClients; i++){
                drivers[i].join();
            }*/
            long timeInMs = System.currentTimeMillis() - startTime;
            String diff = format("%02dmin%02dsec", TimeUnit.MILLISECONDS.toMinutes(timeInMs),
                    TimeUnit.MILLISECONDS.toSeconds(timeInMs) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMs))
            );
            logger.info("Total Number of transactions processed: " + TransactionClient.totalNumberOfTransactionsProcessed + " in "+ diff);
            double transactionsPerSecond = (double) TransactionClient.totalNumberOfTransactionsProcessed / TimeUnit.MILLISECONDS.toSeconds(timeInMs);
            logger.info("Transaction throughput (number of transactions processed per second)::" + transactionsPerSecond);
            logger.info("---------------Ended executing transactions for "+ numberOfClients+" clients------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
