package com.cassandra.beans;

/**
 * Created by manisha on 25/09/2016.
 */
public class OrderLineItemBean {
    OrderKey orderKey;
    Item item;

    public OrderLineItemBean(OrderKey orderKey, Item item) {
        this.orderKey = orderKey;
        this.item = item;
    }

    public OrderKey getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(OrderKey orderKey) {
        this.orderKey = orderKey;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
