package com.cassandra.beans;

import java.util.Set;

/**
 * Created by manikuramboyina on 25/9/16.
 */
public class NewOrderTransactionBean {

    private OrderKey orderKey;

    Set<Item> items;

    public NewOrderTransactionBean(OrderKey orderKey) {
        this.orderKey = orderKey;
    }

    public OrderKey getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(OrderKey orderKey) {
        this.orderKey = orderKey;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
