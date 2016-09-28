package com.cassandra.models;

/**
 * Created by Sachin on 9/28/2016.
 */
public class District {
    private String D_W_ID;
    private String D_ID;
    private String D_NAME;
    private String D_STREET_1;
    private String D_STREET_2;
    private String D_CITY;
    private String D_STATE;
    private String D_ZIP;
    private String D_TAX;
    private String D_YTD;
    private String D_NEXT_O_ID;

    public String getD_W_ID() {
        return this.D_W_ID;
    }

    public void setD_W_ID(String d_W_ID) {
        this.D_W_ID = d_W_ID;
    }

    public String getD_ID() {
        return this.D_ID;
    }

    public void setD_ID(String d_ID) {
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

    public String getD_TAX() {
        return this.D_TAX;
    }

    public void setD_TAX(String d_TAX) {
        this.D_TAX = d_TAX;
    }

    public String getD_YTD() {
        return this.D_YTD;
    }

    public void setD_YTD(String d_YTD) {
        this.D_YTD = d_YTD;
    }

    public String getD_NEXT_O_ID() {
        return this.D_NEXT_O_ID;
    }

    public void setD_NEXT_O_ID(String d_NEXT_O_ID) {
        this.D_NEXT_O_ID = d_NEXT_O_ID;
    }

    public District(String d_W_ID, String d_ID, String d_NAME, String d_STREET_1, String d_STREET_2, String d_CITY, String d_STATE, String d_ZIP, String d_TAX, String d_YTD, String d_NEXT_O_ID) {
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