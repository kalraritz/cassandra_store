package com.cassandra;

import com.cassandra.dump.DumpWarehouse;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CassandraInit {
    private static Logger logger = Logger.getLogger(CassandraSession.class);

    public static void main(String[] args) {
        ClassLoader classLoader = CassandraInit.class.getClassLoader();
        PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));
        Session session = CassandraSession.getSession();
        if (session == null) {
            logger.error("Could not create a session");
            return;
        }

        // dump data
        new DumpWarehouse().dump();

        CassandraSession.closeSession();
        System.out.println("Program Ended!");
        System.exit(0);
    }
}
