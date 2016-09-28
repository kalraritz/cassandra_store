package com.cassandra.beans;

import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.UDT;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by manisha on 24/09/2016.
 */
public class Item {
    private int I_ID;
    private String I_NAME;
    private double I_PRICE;
    private String I_IM_ID;
    private String I_DATA;

    public Item(int i_ID, String i_NAME, double i_PRICE, String i_IM_ID, String i_DATA) {
        this.I_ID = i_ID;
        this.I_NAME = i_NAME;
        this.I_PRICE = i_PRICE;
        this.I_IM_ID = i_IM_ID;
        this.I_DATA = i_DATA;
    }

    public int getI_ID() {
        return I_ID;
    }

    public void setI_ID(int i_ID) {
        I_ID = i_ID;
    }

    public String getI_NAME() {
        return I_NAME;
    }

    public void setI_NAME(String i_NAME) {
        I_NAME = i_NAME;
    }

    public double getI_PRICE() {
        return I_PRICE;
    }

    public void setI_PRICE(double i_PRICE) {
        I_PRICE = i_PRICE;
    }

    public String getI_IM_ID() {
        return I_IM_ID;
    }

    public void setI_IM_ID(String i_IM_ID) {
        I_IM_ID = i_IM_ID;
    }

    public String getI_DATA() {
        return I_DATA;
    }

    public void setI_DATA(String i_DATA) {
        I_DATA = i_DATA;
    }
}