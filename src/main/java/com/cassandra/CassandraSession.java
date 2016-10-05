package com.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import org.apache.log4j.Logger;

/**
 * Created by ritesh on 05/10/16.
 */
public final class CassandraSession {
    private static Session session = null;
    private static Logger logger = Logger.getLogger(CassandraSession.class);

    private CassandraSession() {
    }

    private static void createSession() throws Exception {
        Cluster cluster = Cluster.builder().addContactPoint("192.168.48.248").build();
        session = cluster.connect("thehood");
        logger.info("Connected to " + session.getCluster().getClusterName());
        for (Row row : session.execute("SELECT * FROM sql_warehouse")) {
            logger.info(row);
        }
    }

    public static Session getSession() {
        if (session == null) {
            try {
                createSession();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return session;
    }

    public static void closeSession() {
        if (session != null) {
            session.close();
        }
    }
}
