package com.cassandra.beans;

import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.UDT;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by manisha on 24/09/2016.
 */
@UDT(keyspace = "thehood", name = "item")
public class Item {

    @Field(name = "OL_I_ID")
    private int olItemId;
    @Field(name = "OL_I_NAME")
    private String olItemName;
    @Field(name = "OL_NUMBER")
    private int olNumber;
    @Field(name = "OL_S_W_ID")
    private int olSuppWarehouseId;
    @Field(name = "OL_QUANTITY")
    private double olQuantity;
    @Field(name = "OL_DELIVERY_D")
    private Date olDeliveryDate;
    @Field(name = "OL_DIST_INFO")
    private String olDistInfo;
    @Field(name = "OL_AMOUNT")
    private double olAmount;

    public Item(int olItemId, String olItemName, int olNumber, int olSuppWarehouseId, double olQuantity, Date olDeliveryDate, String olDistInfo, double olAmount) {
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

    public double getOlQuantity() {
        return olQuantity;
    }

    public void setOlQuantity(double olQuantity) {
        this.olQuantity = olQuantity;
    }

    public Date getOlDeliveryDate() {
        return olDeliveryDate;
    }

    public void setOlDeliveryDate(Date olDeliveryDate) {
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
