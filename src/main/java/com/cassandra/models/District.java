package com.cassandra.models;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

/**
 * Created by Sachin on 9/28/2016.
 */

@Table(keyspace = "orders", name = "District")
public class District {

    @Column( name = "D_ID" )
    private int id;

    @Column( name = "D_W_ID" )
    private int warehouseId;

    @Column( name = "D_NAME" )
    private String name;

    @Column( name = "D_STREET" )
    private String street;

    @Column( name = "D_STREET2" )
    private String street2;

    @Column( name = "D_CITY" )
    private String city;

    @Column( name = "D_STATE" )
    private String state;

    @Column( name = "D_ZIP" )
    private String zip;

    @Column( name = "D_TAX" )
    private double tax;

    public District(int id, int warehouseId, String name, String street, String street2, String city, String state, String zip, double tax) {
        this.id = id;
        this.warehouseId = warehouseId;
        this.name = name;
        this.street = street;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.tax = tax;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }
}