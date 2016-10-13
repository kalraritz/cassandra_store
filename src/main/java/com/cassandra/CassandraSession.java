package com.cassandra;

import com.cassandra.beans.Item;
import com.cassandra.beans.ItemCodec;
import com.datastax.driver.core.*;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * Created by ritesh on 05/10/16.
 */
public final class CassandraSession {
    private static Session session = null;
    private static Logger logger = Logger.getLogger(CassandraSession.class);
    private CassandraSession() {
    }

    private static void createSession(Properties properties) throws Exception {
        Cluster cluster = Cluster.builder().addContactPoint(properties.getProperty("cassandra_ip")).build();
        session = cluster.connect(properties.getProperty("keyspace_name"));
        CodecRegistry codecregisty = CodecRegistry.DEFAULT_INSTANCE;
        System.out.println(session.getCluster().getClusterName());
        UserType itemType = cluster.getMetadata().getKeyspace(properties.getProperty("keyspace_name")).getUserType("item");
        TypeCodec<UDTValue> itemTypeCodec = codecregisty.codecFor(itemType);
        ItemCodec itemcodec = new ItemCodec(itemTypeCodec, Item.class);
        codecregisty.register(itemcodec);
        logger.info("Session connected to " + session.getCluster().getClusterName());
    }

    public static Session getSession(Properties properties) {
        if (session == null) {
            try {
                createSession(properties);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return session;
    }

    public static void closeSession() {
        if (session != null) {
            session.close();
            logger.info("Session closed to " + session.getCluster().getClusterName());
        }
    }
}
