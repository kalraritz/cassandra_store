package com.cassandra;

import com.datastax.driver.core.*;

public class Hello {
    public static void main(String[] args) {
        Cluster cluster;
        cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        Session session = cluster.connect("orders");

//        PreparedStatement statement = session.prepare(
//
//                "INSERT INTO users" + "(O_W_ID, O_D_ID, O_ID, O_ENTRY_D, O_CARRIER_D, O_C_ID, O_OL_CNT, O_ALL_LOCAL," +
//                        "O_ITEMS)"
//                        + "VALUES (?,?,?,?,?);");
//
//        BoundStatement boundStatement = new BoundStatement(statement);
//
//        session.execute(boundStatement.bind(1, 35, "Austin",
//                "bob@example.com", "Bob"));

//        List<KeyspaceMetadata> list =cluster.getMetadata().getKeyspaces();
//        for(KeyspaceMetadata key: list){
//            System.out.println(key.getName());
//        }

    }
}
