package com.cassandra.models;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

/**
 * Created by manisha on 04/10/2016.
 */
@Table(keyspace = "orders", name = "rel_district")
public class RelDistrict {

    @Column( name = "D_ID" )
    private int id;

    @Column( name = "D_W_ID" )
    private int warehouseId;

    @Column( name = "D_NAME" )
    private String name;

    @Column( name = "D_STREET1" )
    private String street1;

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

    @Column( name = "D_YTD" )
    private double ytd;

    @Column( name = "D_NEXT_O_ID" )
    private int nextOrderId;

    public RelDistrict(int id, int warehouseId, String name, String street1, String street2, String city, String state, String zip, double tax, double ytd, int nextOrderId) {
        this.id = id;
        this.warehouseId = warehouseId;
        this.name = name;
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.tax = tax;
        this.ytd = ytd;
        this.nextOrderId = nextOrderId;
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

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getYtd() {
        return ytd;
    }

    public void setYtd(double ytd) {
        this.ytd = ytd;
    }

    public int getNextOrderId() {
        return nextOrderId;
    }

    public void setNextOrderId(int nextOrderId) {
        this.nextOrderId = nextOrderId;
    }
}
