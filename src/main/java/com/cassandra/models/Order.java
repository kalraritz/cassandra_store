package com.cassandra.models;

import com.cassandra.beans.Item;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;
import java.util.Set;

/**
 * Created by manisha on 25/09/2016.
 */
@Table(keyspace = "thehood", name = "New_Order_Transaction")
public class Order {

    @Column( name = "O_W_ID" )
    private int warehouseId;

    @Column( name = "O_D_ID" )
    private int districtId;

    @Column( name = "O_ID" )
    private int orderId;

    @Column( name = "O_ENTRY_D" )
    private Date entryDate;

    @Column( name = "O_CARRIER_ID" )
    private int carrierId;

    @Column( name = "O_C_ID" )
    private int customerId;

    @Column( name = "O_OL_CNT" )
    private double olCount;

    @Column( name = "O_ALL_LOCAL" )
    private double oallLocal;

    @Frozen
    @Column( name = "O_ITEMS" )
    private Set<Item> items;


    public Order(int warehouseId, int districtId, int orderId, Date entryDate, int carrierId, int customerId, double olCount, double oallLocal, Set<Item> items) {
        this.warehouseId = warehouseId;
        this.districtId = districtId;
        this.orderId = orderId;
        this.entryDate = entryDate;
        this.carrierId = carrierId;
        this.customerId = customerId;
        this.olCount = olCount;
        this.oallLocal = oallLocal;
        this.items = items;
    }
}
