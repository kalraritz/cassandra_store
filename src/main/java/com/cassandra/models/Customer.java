package com.cassandra.models;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;

/**
 * Created by Sachin on 9/28/2016.
 */
@Table(keyspace = "orders", name = "Customer")
public class Customer {

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
    private String contact;

    @Column( name = "C_SINCE" )
    private Date since;

    @Column( name = "C_CREDIT" )
    private double credit;

    @Column( name = "C_CREDIT_LIM" )
    private double creditLim;

    @Column( name = "C_DISCOUNT" )
    private double discount;

    @Column( name = "C_DATA" )
    private String data;

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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}