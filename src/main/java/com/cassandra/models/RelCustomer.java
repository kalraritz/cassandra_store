package com.cassandra.models;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;

/**
 * Created by manisha on 04/10/2016.
 */

@Table(keyspace = "orders", name = "rel_customer")
public class RelCustomer {

    @Column( name = "C_ID" )
    private int id;

    @Column( name = "C_W_ID" )
    private int warehouseId;

    @Column( name = "C_D_ID" )
    private int districtId;

    @Column( name = "C_FIRST" )
    private String firstName;

    @Column( name = "C_MIDDLE" )
    private String middleName;

    @Column( name = "C_LAST" )
    private String lastName;

    @Column( name = "C_STREET1" )
    private String street1;

    @Column( name = "C_STREET2" )
    private String street2;

    @Column( name = "C_CITY" )
    private String city;

    @Column( name = "C_STATE" )
    private String state;

    @Column( name = "C_ZIP" )
    private String zip;

    @Column( name = "C_PHONE" )
    private String phone;

    @Column( name = "C_SINCE" )
    private Date since;

    @Column( name = "C_CREDIT" )
    private String credit;

    @Column( name = "C_CREDIT_LIM" )
    private double creditLim;

    @Column( name = "C_DISCOUNT" )
    private double discount;

    @Column( name = "C_BALANCE" )
    private double balance;

    @Column( name = "C_YTD_PAYMENT" )
    private float ytdPayment;

    @Column( name = "C_PAYMENT_CNT" )
    private int paymentCnt;

    @Column( name = "C_DELIVERY_CNT" )
    private int deliveryCnt;

    @Column( name = "C_DATA" )
    private String data;

    public RelCustomer(int id, int warehouseId, int districtId, String firstName, String middleName, String lastName, String street1, String street2, String city, String state, String zip, String phone, Date since, String credit, double creditLim, double discount, double balance, float ytdPayment, int paymentCnt, int deliveryCnt, String data) {
        this.id = id;
        this.warehouseId = warehouseId;
        this.districtId = districtId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.since = since;
        this.credit = credit;
        this.creditLim = creditLim;
        this.discount = discount;
        this.balance = balance;
        this.ytdPayment = ytdPayment;
        this.paymentCnt = paymentCnt;
        this.deliveryCnt = deliveryCnt;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public double getCreditLim() {
        return creditLim;
    }

    public void setCreditLim(double creditLim) {
        this.creditLim = creditLim;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public float getYtdPayment() {
        return ytdPayment;
    }

    public void setYtdPayment(float ytdPayment) {
        this.ytdPayment = ytdPayment;
    }

    public int getPaymentCnt() {
        return paymentCnt;
    }

    public void setPaymentCnt(int paymentCnt) {
        this.paymentCnt = paymentCnt;
    }

    public int getDeliveryCnt() {
        return deliveryCnt;
    }

    public void setDeliveryCnt(int deliveryCnt) {
        this.deliveryCnt = deliveryCnt;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
