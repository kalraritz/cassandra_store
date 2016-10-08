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
}
