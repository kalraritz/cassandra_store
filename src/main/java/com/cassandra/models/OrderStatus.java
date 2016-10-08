package com.cassandra.models;

import com.cassandra.beans.Item;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;
import java.util.Set;

/**
 * Created by manisha on 08/10/2016.
 */
@Table(keyspace = "thehood", name = "Order_Status_Transaction")
public class OrderStatus {

    @Column( name = "OS_W_ID" )
    private int warehouseId;

    @Column( name = "OS_D_ID" )
    private int districtId;

    @Column( name = "OS_C_ID" )
    private int customerId;

    @Column( name = "OS_O_ID" )
    private int orderId;

    @Column( name = "OS_C_BALANCE" )
    private double customerBalance;

    @Column( name = "OS_O_ENTRY_D" )
    private Date orderEntryDate;

    @Column( name = "OS_O_CARRIER_ID" )
    private int orderCarrierId;

    @Frozen
    @Column( name = "OS_O_ITEMS" )
    private Set<Item> items;

    public OrderStatus(int warehouseId, int districtId, int customerId, int orderId, double customerBalance, Date orderEntryDate, int orderCarrierId, Set<Item> items) {
        this.warehouseId = warehouseId;
        this.districtId = districtId;
        this.customerId = customerId;
        this.orderId = orderId;
        this.customerBalance = customerBalance;
        this.orderEntryDate = orderEntryDate;
        this.orderCarrierId = orderCarrierId;
        this.items = items;
    }
}
