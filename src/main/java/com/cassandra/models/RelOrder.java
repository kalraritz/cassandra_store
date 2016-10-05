package com.cassandra.models;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;

/**
 * Created by manisha on 05/10/2016.
 */
@Table(keyspace = "orders", name = "rel_order")
public class RelOrder {

    @Column( name = "O_ID" )
    private int id;

    @Column( name = "O_ID" )
    private int warehouseId;

    @Column( name = "O_D_ID" )
    private int districtId;

    @Column( name = "O_C_ID" )
    private int customerId;

    @Column( name = "O_CARRIER_ID" )
    private int carrierId;

    @Column( name = "O_OL_CNT" )
    private double olCount;

    @Column( name = "O_ALL_LOCAL" )
    private double allLocal;

    @Column( name = "O_ENTRY_D" )
    private Date entryDate;

    public RelOrder(int id, int warehouseId, int districtId, int customerId, int carrierId, double olCount, double allLocal, Date entryDate) {
        this.id = id;
        this.warehouseId = warehouseId;
        this.districtId = districtId;
        this.customerId = customerId;
        this.carrierId = carrierId;
        this.olCount = olCount;
        this.allLocal = allLocal;
        this.entryDate = entryDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
    }

    public double getOlCount() {
        return olCount;
    }

    public void setOlCount(double olCount) {
        this.olCount = olCount;
    }

    public double getAllLocal() {
        return allLocal;
    }

    public void setAllLocal(double allLocal) {
        this.allLocal = allLocal;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
}
