package com.cassandra.beans;

import com.cassandra.models.Item;
import com.cassandra.models.ItemOrders;

/**
 * Created by manisha on 25/09/2016.
 */
public class OrderLineItemBean {
    OrderKey orderKey;
    ItemOrders item;

    public OrderLineItemBean(OrderKey orderKey, ItemOrders item) {
        this.orderKey = orderKey;
        this.item = item;
    }

    public OrderKey getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(OrderKey orderKey) {
        this.orderKey = orderKey;
    }

    public ItemOrders getItem() {
        return item;
    }

    public void setItem(ItemOrders item) {
        this.item = item;
    }
}
