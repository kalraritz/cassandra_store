package com.cassandra.models;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

/**
 * Created by manisha on 08/10/2016.
 */
@Table(keyspace = "thehood", name = "Customer_Data")
public class CustomerData {

    @Column( name = "C_ID" )
    private int id;

    @Column( name = "C_W_ID" )
    private int warehouseId;

    @Column( name = "C_D_ID" )
    private int districtId;

    @Column( name = "C_BALANCE" )
    private double balance;

    @Column( name = "C_DELIVERY_CNT" )
    private int deliveryCount;

    @Column( name = "C_YTD_PAYMENT" )
    private double ytd;

    @Column( name = "C_PAYMENT_CNT" )
    private int paymentCount;

    public CustomerData(int id, int warehouseId, int districtId, double balance, int deliveryCount, double ytd, int paymentCount) {
        this.id = id;
        this.warehouseId = warehouseId;
        this.districtId = districtId;
        this.balance = balance;
        this.deliveryCount = deliveryCount;
        this.ytd = ytd;
        this.paymentCount = paymentCount;
    }
}
