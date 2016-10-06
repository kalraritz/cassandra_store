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
    private Long orderId;

    @Column( name = "O_ENTRY_D" )
    private Date entryDate;

    @Column( name = "O_CARRIER_ID" )
    private Integer carrierId;

    @Column( name = "O_C_ID" )
    private Long customerId;

    @Column( name = "O_OL_CNT" )
    private int olCount;

    @Column( name = "O_ALL_LOCAL" )
    private int oallLocal;

    @Frozen
    @Column( name = "O_ITEMS" )
    private Set<Item> items;

    public Order(int warehouseId, int districtId, Long orderId, Date entryDate, Integer carrierId, Long customerId, int olCount, int oallLocal, Set<Item> items) {
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(Integer carrierId) {
        this.carrierId = carrierId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public int getOlCount() {
        return olCount;
    }

    public void setOlCount(int olCount) {
        this.olCount = olCount;
    }

    public int getOallLocal() {
        return oallLocal;
    }

    public void setOallLocal(int oallLocal) {
        this.oallLocal = oallLocal;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
