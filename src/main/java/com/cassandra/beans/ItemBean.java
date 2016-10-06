package com.cassandra.beans;

/**
 * Created by manisha on 24/09/2016.
 */
public class ItemBean {

    private int id;
    private String name;
    private double price;
    private int imgId;
    private String data;

    public ItemBean(int id, String name, double price, int imgId, String data) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgId = imgId;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
