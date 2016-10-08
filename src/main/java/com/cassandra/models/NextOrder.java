package com.cassandra.models;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

/**
 * Created by manisha on 08/10/2016.
 */
@Table(keyspace = "thehood", name = "Next_Order")
public class NextOrder {

    @Column( name = "NO_W_ID" )
    private int warehouseId;

    @Column( name = "NO_D_ID" )
    private int districtId;

    @Column( name = "NO_D_NEXT_O_ID" )
    private int nextOrderId;

    @Column( name = "NO_W_YTD" )
    private double warehouseYtd;

    @Column( name = "NO_D_YTD" )
    private double districtYtd;

    public NextOrder(int warehouseId, int districtId, int nextOrderId, double warehouseYtd, double districtYtd) {
        this.warehouseId = warehouseId;
        this.districtId = districtId;
        this.nextOrderId = nextOrderId;
        this.warehouseYtd = warehouseYtd;
        this.districtYtd = districtYtd;
    }
}
