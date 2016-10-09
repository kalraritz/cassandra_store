package com.cassandra;

import com.cassandra.beans.Item;
import com.datastax.driver.core.*;
import org.apache.log4j.Logger;

/**
 * Created by ritesh on 05/10/16.
 */
public final class CassandraSession {
    private static Session session = null;
    private static Logger logger = Logger.getLogger(CassandraSession.class);
    private static final String ip = "192.168.48.247";
    private CassandraSession() {
    }

    private static void createSession() throws Exception {
        Cluster cluster = Cluster.builder().addContactPoint(ip).build();
        session = cluster.connect("thehood");
//        CodecRegistry codecregisty = CodecRegistry.DEFAULT_INSTANCE;
//        System.out.println(session.getCluster().getClusterName());
//        UserType itemType = cluster.getMetadata().getKeyspace("thehood").getUserType("item");
//        TypeCodec<UDTValue> itemTypeCodec = codecregisty.codecFor(itemType);
//        ItemCodec itemcodec = new ItemCodec(itemTypeCodec, Item.class);
//        codecregisty.register(itemcodec);
        logger.info("Session connected to " + session.getCluster().getClusterName());
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
            logger.info("Session closed to " + session.getCluster().getClusterName());
        }
    }
}
