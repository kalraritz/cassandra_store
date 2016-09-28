package com.cassandra.models;

/**
 * Created by Sachin on 9/28/2016.
 */
public class District {
    private int D_W_ID;
    private int D_ID;
    private String D_NAME;
    private String D_STREET_1;
    private String D_STREET_2;
    private String D_CITY;
    private String D_STATE;
    private String D_ZIP;
    private double D_TAX;
    private double D_YTD;
    private int D_NEXT_O_ID;

    public int getD_W_ID() {
        return this.D_W_ID;
    }

    public void setD_W_ID(int d_W_ID) {
        this.D_W_ID = d_W_ID;
    }

    public int getD_ID() {
        return this.D_ID;
    }

    public void setD_ID(int d_ID) {
        this.D_ID = d_ID;
    }

    public String getD_NAME() {
        return this.D_NAME;
    }

    public void setD_NAME(String d_NAME) {
        this.D_NAME = d_NAME;
    }

    public String getD_STREET_1() {
        return this.D_STREET_1;
    }

    public void setD_STREET_1(String d_STREET_1) {
        this.D_STREET_1 = d_STREET_1;
    }

    public String getD_STREET_2() {
        return this.D_STREET_2;
    }

    public void setD_STREET_2(String d_STREET_2) {
        this.D_STREET_2 = d_STREET_2;
    }

    public String getD_CITY() {
        return this.D_CITY;
    }

    public void setD_CITY(String d_CITY) {
        this.D_CITY = d_CITY;
    }

    public String getD_STATE() {
        return this.D_STATE;
    }

    public void setD_STATE(String d_STATE) {
        this.D_STATE = d_STATE;
    }

    public String getD_ZIP() {
        return this.D_ZIP;
    }

    public void setD_ZIP(String d_ZIP) {
        this.D_ZIP = d_ZIP;
    }

    public double getD_TAX() {
        return this.D_TAX;
    }

    public void setD_TAX(double d_TAX) {
        this.D_TAX = d_TAX;
    }

    public double getD_YTD() {
        return this.D_YTD;
    }

    public void setD_YTD(double d_YTD) {
        this.D_YTD = d_YTD;
    }

    public int getD_NEXT_O_ID() {
        return this.D_NEXT_O_ID;
    }

    public void setD_NEXT_O_ID(int d_NEXT_O_ID) {
        this.D_NEXT_O_ID = d_NEXT_O_ID;
    }

    public District(int d_W_ID, int d_ID, String d_NAME, String d_STREET_1, String d_STREET_2, String d_CITY, String d_STATE, String d_ZIP, double d_TAX, double d_YTD, int d_NEXT_O_ID) {
        this.D_W_ID = d_W_ID;
        this.D_ID = d_ID;
        this.D_NAME = d_NAME;
        this.D_STREET_1 = d_STREET_1;
        this.D_STREET_2 = d_STREET_2;
        this.D_CITY = d_CITY;
        this.D_STATE = d_STATE;
        this.D_ZIP = d_ZIP;
        this.D_TAX = d_TAX;
        this.D_YTD = d_YTD;
        this.D_NEXT_O_ID = d_NEXT_O_ID;
    }
}