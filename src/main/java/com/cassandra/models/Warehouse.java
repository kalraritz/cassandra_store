package com.cassandra.models;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

/**
 * Created by Sachin on 9/28/2016.
 */
@Table(keyspace = "thehood", name = "warehouse")
public class Warehouse {

    @Column( name = "W_ID" )
    private int id;

    @Column( name = "W_NAME" )
    private String name;

    @Column( name = "W_STREET_1" )
    private String street1;

    @Column( name = "W_STREET_2" )
    private String street2;

    @Column( name = "W_CITY" )
    private String city;

    @Column( name = "W_STATE" )
    private String state;

    @Column( name = "W_ZIP" )
    private String zip;

    @Column( name = "W_TAX" )
    private double tax;

    public Warehouse(int id, String name, String street1, String street2, String city, String state, String zip, double tax) {
        this.id = id;
        this.name = name;
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.tax = tax;
    }
}