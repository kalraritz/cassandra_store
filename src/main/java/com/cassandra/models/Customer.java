package com.cassandra.models;

import java.sql.Timestamp;

/**
 * Created by Sachin on 9/28/2016.
 */

public class Customer {
    private int C_W_ID;
    private int C_D_ID;
    private int C_ID;
    private String C_FIRST;
    private String C_MIDDLE;
    private String C_LAST;
    private String C_STREET_1;
    private String C_STREET_2;
    private String C_CITY;
    private String C_STATE;
    private String C_ZIP;
    private String C_PHONE;
    private Timestamp C_SINCE;
    private String C_CREDIT;
    private double C_CREDIT_LIM;
    private double C_DISCOUNT;
    private double C_BALANCE;
    private double C_YTD_PAYMENT;
    private int C_PAYMENT_CNT;
    private int C_DELIVERY_CNT;
    private String C_DATA;

    public int getC_W_ID() {
        return this.C_W_ID;
    }

    public void setC_W_ID(int c_W_ID) {
        this.C_W_ID = c_W_ID;
    }

    public int getC_D_ID() {
        return this.C_D_ID;
    }

    public void setC_D_ID(int c_D_ID) {
        this.C_D_ID = c_D_ID;
    }

    public int getC_ID() {
        return this.C_ID;
    }

    public void setC_ID(int c_ID) {
        this.C_ID = c_ID;
    }

    public String getC_FIRST() {
        return this.C_FIRST;
    }

    public void setC_FIRST(String c_FIRST) {
        this.C_FIRST = c_FIRST;
    }

    public String getC_MIDDLE() {
        return this.C_MIDDLE;
    }

    public void setC_MIDDLE(String c_MIDDLE) {
        this.C_MIDDLE = c_MIDDLE;
    }

    public String getC_LAST() {
        return this.C_LAST;
    }

    public void setC_LAST(String c_LAST) {
        this.C_LAST = c_LAST;
    }

    public String getC_STREET_1() {
        return this.C_STREET_1;
    }

    public void setC_STREET_1(String c_STREET_1) {
        this.C_STREET_1 = c_STREET_1;
    }

    public String getC_STREET_2() {
        return this.C_STREET_2;
    }

    public void setC_STREET_2(String c_STREET_2) {
        this.C_STREET_2 = c_STREET_2;
    }

    public String getC_CITY() {
        return this.C_CITY;
    }

    public void setC_CITY(String c_CITY) {
        this.C_CITY = c_CITY;
    }

    public String getC_STATE() {
        return this.C_STATE;
    }

    public void setC_STATE(String c_STATE) {
        this.C_STATE = c_STATE;
    }

    public String getC_ZIP() {
        return this.C_ZIP;
    }

    public void setC_ZIP(String c_ZIP) {
        this.C_ZIP = c_ZIP;
    }

    public String getC_PHONE() {
        return this.C_PHONE;
    }

    public void setC_PHONE(String c_PHONE) {
        this.C_PHONE = c_PHONE;
    }

    public Timestamp getC_SINCE() {
        return this.C_SINCE;
    }

    public void setC_SINCE(Timestamp c_SINCE) {
        this.C_SINCE = c_SINCE;
    }

    public String getC_CREDIT() {
        return this.C_CREDIT;
    }

    public void setC_CREDIT(String c_CREDIT) {
        this.C_CREDIT = c_CREDIT;
    }

    public double getC_CREDIT_LIM() {
        return this.C_CREDIT_LIM;
    }

    public void setC_CREDIT_LIM(double c_CREDIT_LIM) {
        this.C_CREDIT_LIM = c_CREDIT_LIM;
    }

    public double getC_DISCOUNT() {
        return this.C_DISCOUNT;
    }

    public void setC_DISCOUNT(double c_DISCOUNT) {
        this.C_DISCOUNT = c_DISCOUNT;
    }

    public double getC_BALANCE() {
        return this.C_BALANCE;
    }

    public void setC_BALANCE(double c_BALANCE) {
        this.C_BALANCE = c_BALANCE;
    }

    public double getC_YTD_PAYMENT() {
        return this.C_YTD_PAYMENT;
    }

    public void setC_YTD_PAYMENT(double c_YTD_PAYMENT) {
        this.C_YTD_PAYMENT = c_YTD_PAYMENT;
    }

    public int getC_PAYMENT_CNT() {
        return this.C_PAYMENT_CNT;
    }

    public void setC_PAYMENT_CNT(int c_PAYMENT_CNT) {
        this.C_PAYMENT_CNT = c_PAYMENT_CNT;
    }

    public int getC_DELIVERY_CNT() {
        return this.C_DELIVERY_CNT;
    }

    public void setC_DELIVERY_CNT(int c_DELIVERY_CNT) {
        this.C_DELIVERY_CNT = c_DELIVERY_CNT;
    }

    public String getC_DATA() {
        return this.C_DATA;
    }

    public void setC_DATA(String c_DATA) {
        this.C_DATA = c_DATA;
    }

    public Customer(int c_W_ID, int c_D_ID, int c_ID, String c_FIRST, String c_MIDDLE, String c_LAST, String c_STREET_1, String c_STREET_2, String c_CITY, String c_STATE, String c_ZIP, String c_PHONE, Timestamp c_SINCE, String c_CREDIT, double c_CREDIT_LIM, double c_DISCOUNT, double c_BALANCE, double c_YTD_PAYMENT, int c_PAYMENT_CNT, int c_DELIVERY_CNT, String c_DATA) {
        this.C_W_ID = c_W_ID;
        this.C_D_ID = c_D_ID;
        this.C_ID = c_ID;
        this.C_FIRST = c_FIRST;
        this.C_MIDDLE = c_MIDDLE;
        this.C_LAST = c_LAST;
        this.C_STREET_1 = c_STREET_1;
        this.C_STREET_2 = c_STREET_2;
        this.C_CITY = c_CITY;
        this.C_STATE = c_STATE;
        this.C_ZIP = c_ZIP;
        this.C_PHONE = c_PHONE;
        this.C_SINCE = c_SINCE;
        this.C_CREDIT = c_CREDIT;
        this.C_CREDIT_LIM = c_CREDIT_LIM;
        this.C_DISCOUNT = c_DISCOUNT;
        this.C_BALANCE = c_BALANCE;
        this.C_YTD_PAYMENT = c_YTD_PAYMENT;
        this.C_PAYMENT_CNT = c_PAYMENT_CNT;
        this.C_DELIVERY_CNT = c_DELIVERY_CNT;
        this.C_DATA = c_DATA;
    }
}