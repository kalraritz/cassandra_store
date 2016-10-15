package com.cassandra.transactions;

import com.cassandra.CassandraSession;
import com.cassandra.TransactionDriver;
import com.cassandra.csv.DistrictCsv;
import com.cassandra.utilities.Lucene;
import com.datastax.driver.core.Session;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Date;
import java.util.Properties;

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
            logger.info("Enter the number of clients");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String numberOfClients = bufferedReader.readLine();
            logger.info("---------------Starting executing transactions for "+ numberOfClients+"------------------------");
            for(int i=0; i<numberOfClients.length(); i++){
                TransactionDriver transactionDriverThread = new TransactionDriver(session, lucene, printWriter, "thread_"+i, new Date(), i, transactionDir);
                transactionDriverThread.start();
            }
            logger.info("---------------Ended executing transactions for "+ numberOfClients+"------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
