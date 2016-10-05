package com.cassandra.models;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.UDT;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by manisha on 24/09/2016.
 */
@Table(keyspace = "orders", name = "rel_item")
public class RelItem {

    @Column( name = "I_ID" )
    private int id;

    @Column( name = "I_NAME" )
    private String name;

    @Column( name = "I_PRICE" )
    private double price;

    @Column( name = "I_IM_ID" )
    private int imId;

    @Column( name = "I_DATA" )
    private String data;

    public RelItem(int id, String name, double price, int imId, String data) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imId = imId;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getImId() {
        return imId;
    }

    public void setImId(int imId) {
        this.imId = imId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}