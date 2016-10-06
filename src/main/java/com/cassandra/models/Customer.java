package com.cassandra.models;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;

/**
 * Created by Sachin on 9/28/2016.
 */
@Table(keyspace = "thehood", name = "Customer")
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

    @Column( name = "C_STREET_1" )
    private String street1;

    @Column( name = "C_STREET_2" )
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

    public Customer(int id, int warehouseId, int districtId, String firstName, String middleName, String lastName, String street1, String street2, String city, String state, String zip, String contact, Date since, double credit, double creditLim, double discount, String data) {
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
        this.contact = contact;
        this.since = since;
        this.credit = credit;
        this.creditLim = creditLim;
        this.discount = discount;
        this.data = data;
    }
}