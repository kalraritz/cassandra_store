package com.cassandra.transactions;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class NewOrderTransaction {

    public void newOrderTransaction(int warehouseId, int districtId, int customerId, int numOfItems,
                                    int supplierWrehouseId, double quantity) {
        // Creating new order transaction

    }

}

