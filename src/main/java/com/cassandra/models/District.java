package com.cassandra.models;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;

/**
 * Created by Sachin on 9/28/2016.
 */

@Table(keyspace = "thehood", name = "District")
public class District {

    @Column( name = "D_ID" )
    private int id;

    @Column( name = "D_W_ID" )
    private int warehouseId;

    @Column( name = "D_NAME" )
    private String name;

    @Column( name = "D_STREET_1" )
    private String street1;

    @Column( name = "D_STREET_2" )
    private String street2;

    @Column( name = "D_CITY" )
    private String city;

    @Column( name = "D_STATE" )
    private String state;

    @Column( name = "D_ZIP" )
    private String zip;

    @Column( name = "D_TAX" )
    private double tax;

    public District(int id, int warehouseId, String name, String street1, String street2, String city, String state, String zip, double tax) {
        this.id = id;
        this.warehouseId = warehouseId;
        this.name = name;
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.tax = tax;
    }
}