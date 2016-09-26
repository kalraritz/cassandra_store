package com.cassandra.beans;

import java.io.Serializable;
import java.sql.Timestamp;

import static com.datastax.driver.core.DataType.Name.UDT;

/**
 * Created by manisha on 24/09/2016.
 */
public class Item implements Serializable {
    private int olItemId;
    private String olItemName;
    private int olNumber;
    private int olSuppWarehouseId;
    private int olQuantity;
    private Timestamp olDeliveryDate;
    private String olDistInfo;
    private double olAmount;

    public Item(int olItemId, String olItemName, int olNumber, int olSuppWarehouseId, int olQuantity, Timestamp olDeliveryDate, String olDistInfo, double olAmount) {
        this.olItemId = olItemId;
        this.olItemName = olItemName;
        this.olNumber = olNumber;
        this.olSuppWarehouseId = olSuppWarehouseId;
        this.olQuantity = olQuantity;
        this.olDeliveryDate = olDeliveryDate;
        this.olDistInfo = olDistInfo;
        this.olAmount = olAmount;
    }

    public int getOlItemId() {
        return olItemId;
    }

    public void setOlItemId(int olItemId) {
        this.olItemId = olItemId;
    }

    public String getOlItemName() {
        return olItemName;
    }

    public void setOlItemName(String olItemName) {
        this.olItemName = olItemName;
    }

    public int getOlNumber() {
        return olNumber;
    }

    public void setOlNumber(int olNumber) {
        this.olNumber = olNumber;
    }

    public int getOlSuppWarehouseId() {
        return olSuppWarehouseId;
    }

    public void setOlSuppWarehouseId(int olSuppWarehouseId) {
        this.olSuppWarehouseId = olSuppWarehouseId;
    }

    public int getOlQuantity() {
        return olQuantity;
    }

    public void setOlQuantity(int olQuantity) {
        this.olQuantity = olQuantity;
    }

    public Timestamp getOlDeliveryDate() {
        return olDeliveryDate;
    }

    public void setOlDeliveryDate(Timestamp olDeliveryDate) {
        this.olDeliveryDate = olDeliveryDate;
    }

    public String getOlDistInfo() {
        return olDistInfo;
    }

    public void setOlDistInfo(String olDistInfo) {
        this.olDistInfo = olDistInfo;
    }

    public double getOlAmount() {
        return olAmount;
    }

    public void setOlAmount(double olAmount) {
        this.olAmount = olAmount;
    }
}
