package com.cassandra.beans;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by manisha on 24/09/2016.
 */
public class OrderKey {

    private int warehouseId;
    private int districtId;
    private Long orderId;

    public OrderKey(int warehouseId, int districtId, Long orderId) {
        this.warehouseId = warehouseId;
        this.districtId = districtId;
        this.orderId = orderId;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderKey{" +
                "warehouseId=" + warehouseId +
                ", districtId=" + districtId +
                ", orderId=" + orderId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        OrderKey orderKey = (OrderKey) o;

        return new EqualsBuilder()
                .append(warehouseId, orderKey.warehouseId)
                .append(districtId, orderKey.districtId)
                .append(orderId, orderKey.orderId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(warehouseId)
                .append(districtId)
                .append(orderId)
                .toHashCode();
    }
}
