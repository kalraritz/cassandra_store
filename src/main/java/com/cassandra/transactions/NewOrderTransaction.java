package com.cassandra.transactions;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

/**
 * Created by manisha on 24/09/2016.
 */
public class NewOrderTransaction {

    public void newOrderTransaction(int warehouseId, int districtId, int customerId, int numOfItems,
                                    int supplierWrehouseId, double quantity
    ) {
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        Session session = cluster.connect("orders");
        // Creating new order transaction



    }
}

