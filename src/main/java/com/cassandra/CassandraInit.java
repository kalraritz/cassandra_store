package com.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * Created by manisha on 25/9/16.
 */
public class CassandraInit {

    public static void main(String[] args) {



        DumpOrderData dump = new DumpOrderData();
        dump.dumpNewOrderTransactionData();

    }


    public Session cassandraInit()
    {
        Cluster cluster = null;
        Session session = null;
        try {
            cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
            session = cluster.connect("thehood");
            System.out.println(session.getCluster().getClusterName());
        }
        catch (Exception e)
        {
        }
        return session;
    }
    //        dump.dumpNewOrderTransactionData();
//        dump.dumpNextOrderData();
//        dump.test();

}
