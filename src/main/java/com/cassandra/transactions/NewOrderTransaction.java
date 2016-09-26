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

        PreparedStatement statement = session.prepare(
                "INSERT INTO New_Order_Transaction" + "(O_W_ID, O_D_ID, O_ID, O_ENTRY_D, O_CARRIER_ID, O_C_ID, O_OL_CNT, O_ALL_LOCAL," +
                        "O_ITEMS)"
                        + "VALUES (?,?,?,?,?,?,?,?,?);");


    }
}

