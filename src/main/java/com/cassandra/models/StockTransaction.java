package com.cassandra.models;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

/**
 * Created by manisha on 08/10/2016.
 */
@Table(keyspace = "thehood", name = "Stock_Level_Transaction")
public class StockTransaction {

    @Column( name = "S_I_ID" )
    private int itemId;

    @Column( name = "S_W_ID" )
    private int warehouseId;

    @Column( name = "S_QUANTITY" )
    private double quantity;

    @Column( name = "S_YTD" )
    private double ytd;

    @Column( name = "S_ORDER_CNT" )
    private int orderCount;

    @Column( name = "S_REMOTE_CNT" )
    private int remoteCount;


    public StockTransaction(int itemId, int warehouseId, double quantity, double ytd, int orderCount, int remoteCount) {
        this.itemId = itemId;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
        this.ytd = ytd;
        this.orderCount = orderCount;
        this.remoteCount = remoteCount;
    }
}
