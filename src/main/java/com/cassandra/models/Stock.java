package com.cassandra.models;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

/**
 * Created by manisha on 07/10/2016.
 */
@Table(keyspace = "thehood", name = "Stock")
public class Stock {

    @Column( name = "S_W_ID" )
    private int warehouseId;

    @Column( name = "S_I_ID" )
    private int itemId;

    @Column( name = "S_DIST_01" )
    private String dist01;

    @Column( name = "S_DIST_02" )
    private String dist02;

    @Column( name = "S_DIST_03" )
    private String dist03;

    @Column( name = "S_DIST_04" )
    private String dist04;

    @Column( name = "S_DIST_05" )
    private String dist05;

    @Column( name = "S_DIST_06" )
    private String dist06;

    @Column( name = "S_DIST_07" )
    private String dist07;

    @Column( name = "S_DIST_08" )
    private String dist08;

    @Column( name = "S_DIST_09" )
    private String dist09;

    @Column( name = "S_DIST_10" )
    private String dist10;

    @Column( name = "S_DATA" )
    private String data;

    @Column( name = "S_I_NAME" )
    private String itemName;

    @Column( name = "S_I_IM_D" )
    private int itemImgIdentifier;

    @Column( name = "S_I_PRICE" )
    private double itemPrice;

    @Column( name = "S_I_DATA" )
    private String itemData;

    public Stock(int warehouseId, int itemId, String dist01, String dist02, String dist03, String dist04, String dist05, String dist06, String dist07, String dist08, String dist09, String dist10, String data, String itemName, int itemImgIdentifier, double itemPrice, String itemData) {
        this.warehouseId = warehouseId;
        this.itemId = itemId;
        this.dist01 = dist01;
        this.dist02 = dist02;
        this.dist03 = dist03;
        this.dist04 = dist04;
        this.dist05 = dist05;
        this.dist06 = dist06;
        this.dist07 = dist07;
        this.dist08 = dist08;
        this.dist09 = dist09;
        this.dist10 = dist10;
        this.data = data;
        this.itemName = itemName;
        this.itemImgIdentifier = itemImgIdentifier;
        this.itemPrice = itemPrice;
        this.itemData = itemData;
    }
}
