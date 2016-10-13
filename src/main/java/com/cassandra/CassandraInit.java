package com.cassandra;

import com.cassandra.utilities.Lucene;
import com.datastax.driver.core.Session;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class CassandraInit {
//    private static Logger logger = Logger.getLogger(CassandraInit.class);

    private static String luceneIndexFolder;
    private static String csv_files_path;
    private static String csv_dump_path;



    public static void main(String[] args) {
        ClassLoader classLoader = CassandraInit.class.getClassLoader();
        System.out.println("log4j.properties=" + classLoader.getResource("log4j.properties"));
        PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));
//        Session session = CassandraSession.getSession();
//        if (session == null) {
////            logger.error("Could not create a session");
//            return;
//
//        }

        // dump static tables
//        new DumpWarehouse().dump();
//        new DumpDistrict().dump();
//        new DumpCustomer().dump();
//        new DumpStock().dump();

        //Dump dynamic tables
//        new DumpCustomerData().dump();
//        new DumpStockLevelTransaction().dump();
//        new DumpNextOrder().dump();
//        new DumpNewOrderTransaction().dump();
//        new DumpOrderStatusTransaction().dump();
//        new DumpCustomer().dump();
        CassandraSession.closeSession();
//        logger.info("Program Ended!");
        System.exit(0);
    }

}
