package com.cassandra.models;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;

/**
 * Created by manisha on 05/10/2016.
 */
@Table(keyspace = "orders", name = "rel_order_line")
public class RelOrderLine {

    @Column( name = "OL_W_ID" )
    private int warehouseId;

    @Column( name = "OL_D_ID" )
    private int districtId;

    @Column( name = "OL_O_ID" )
    private int orderId;

    @Column( name = "OL_NUMBER" )
    private int orderLineNumber;

    @Column( name = "OL_I_ID" )
    private int itemId;

    @Column( name = "OL_DELIVERY_D" )
    private Date deliveryDate;

    @Column( name = "OL_AMOUNT" )
    private double amount;

    @Column( name = "OL_SUPPLY_W_ID" )
    private int supplierWarehouseId;

    @Column( name = "OL_QUANTITY" )
    private double quantity;

    @Column( name = "OL_DIST_INFO" )
    private String distInfo;

    public RelOrderLine(int warehouseId, int districtId, int orderId, int orderLineNumber, int itemId, Date deliveryDate, double amount, int supplierWarehouseId, double quantity, String distInfo) {
        this.warehouseId = warehouseId;
        this.districtId = districtId;
        this.orderId = orderId;
        this.orderLineNumber = orderLineNumber;
        this.itemId = itemId;
        this.deliveryDate = deliveryDate;
        this.amount = amount;
        this.supplierWarehouseId = supplierWarehouseId;
        this.quantity = quantity;
        this.distInfo = distInfo;
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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderLineNumber() {
        return orderLineNumber;
    }

    public void setOrderLineNumber(int orderLineNumber) {
        this.orderLineNumber = orderLineNumber;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getSupplierWarehouseId() {
        return supplierWarehouseId;
    }

    public void setSupplierWarehouseId(int supplierWarehouseId) {
        this.supplierWarehouseId = supplierWarehouseId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getDistInfo() {
        return distInfo;
    }

    public void setDistInfo(String distInfo) {
        this.distInfo = distInfo;
    }
}
