package com.cassandra.models;

import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.UDT;

import java.sql.Timestamp;

/**
 * Created by manisha on 25/09/2016.
 */
@UDT(name = "item", keyspace = "orders")
public class ItemUDT {

    @Field(name = "OL_I_ID")
    private int olItemId;

    @Field(name = "OL_I_NAME")
    private String olItemName;

    @Field(name = "OL_NUMBER")
    private int olNumber;

    @Field(name = "OL_S_W_ID")
    private int olSuppWid;

    @Field(name = "OL_QUANTITY")
    private int olQty;

    @Field(name = "OL_DELIVERY_D")
    private Timestamp olDelDate;

    @Field(name = "OL_DIST_INFO")
    private String olDistInfo;

    @Field(name = "OL_AMOUNT")
    private double olAmount;


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

    public int getOlSuppWid() {
        return olSuppWid;
    }

    public void setOlSuppWid(int olSuppWid) {
        this.olSuppWid = olSuppWid;
    }

    public int getOlQty() {
        return olQty;
    }

    public void setOlQty(int olQty) {
        this.olQty = olQty;
    }

    public Timestamp getOlDelDate() {
        return olDelDate;
    }

    public void setOlDelDate(Timestamp olDelDate) {
        this.olDelDate = olDelDate;
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
