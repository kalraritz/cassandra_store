package com.cassandra.models;

/**
 * Created by Sachin on 9/28/2016.
 */

public class Warehouse {
    private int W_ID;
    private String W_NAME;
    private String W_STREET_1;
    private String W_STREET_2;
    private String W_CITY;
    private String W_STATE;
    private String W_ZIP;
    private double W_TAX;
    private double W_YTD;

    public Warehouse(int w_ID, String w_NAME, String w_STREET_1, String w_STREET_2, String w_CITY, String w_STATE, String w_ZIP, double w_TAX, double w_YTD) {
        this.W_ID = w_ID;
        this.W_NAME = w_NAME;
        this.W_STREET_1 = w_STREET_1;
        this.W_STREET_2 = w_STREET_2;
        this.W_CITY = w_CITY;
        this.W_STATE = w_STATE;
        this.W_ZIP = w_ZIP;
        this.W_TAX = w_TAX;
        this.W_YTD = w_YTD;
    }

    public int getW_ID() {
        return this.W_ID;
    }

    public void setW_ID(int w_ID) {
        this.W_ID = w_ID;
    }

    public String getW_NAME() {
        return this.W_NAME;
    }

    public void setW_NAME(String w_NAME) {
        this.W_NAME = w_NAME;
    }

    public String getW_STREET_1() {
        return this.W_STREET_1;
    }

    public void setW_STREET_1(String w_STREET_1) {
        this.W_STREET_1 = w_STREET_1;
    }

    public String getW_STREET_2() {
        return this.W_STREET_2;
    }

    public void setW_STREET_2(String w_STREET_2) {
        this.W_STREET_2 = w_STREET_2;
    }

    public String getW_CITY() {
        return this.W_CITY;
    }

    public void setW_CITY(String w_CITY) {
        this.W_CITY = w_CITY;
    }

    public String getW_STATE() {
        return this.W_STATE;
    }

    public void setW_STATE(String w_STATE) {
        this.W_STATE = w_STATE;
    }

    public String getW_ZIP() {
        return this.W_ZIP;
    }

    public void setW_ZIP(String w_ZIP) {
        this.W_ZIP = w_ZIP;
    }

    public double getW_TAX() {
        return this.W_TAX;
    }

    public void setW_TAX(double w_TAX) {
        this.W_TAX = w_TAX;
    }

    public double getW_YTD() {
        return this.W_YTD;
    }

    public void setW_YTD(double w_YTD) {
        this.W_YTD = w_YTD;
    }
}