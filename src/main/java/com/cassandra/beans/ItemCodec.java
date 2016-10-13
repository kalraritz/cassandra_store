package com.cassandra.beans;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.Date;

import com.cassandra.beans.Item;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import com.datastax.driver.core.exceptions.InvalidTypeException;

public class ItemCodec extends TypeCodec<Item> {

    private final TypeCodec<UDTValue> innerCodec;

    private final UserType userType;

    public ItemCodec(TypeCodec<UDTValue> innerCodec, Class<Item> javaType) {
        super(innerCodec.getCqlType(), javaType);
        this.innerCodec = innerCodec;
        this.userType = (UserType)innerCodec.getCqlType();
    }

    @Override
    public ByteBuffer serialize(Item value, ProtocolVersion protocolVersion) throws InvalidTypeException {
        return innerCodec.serialize(toUDTValue(value), protocolVersion);
    }

    @Override
    public Item deserialize(ByteBuffer bytes, ProtocolVersion protocolVersion) throws InvalidTypeException {
        return toAddress(innerCodec.deserialize(bytes, protocolVersion));
    }

    @Override
    public Item parse(String value) throws InvalidTypeException {
        return value == null || value.isEmpty() || value.equals("NULL") ? null : toAddress(innerCodec.parse(value));
    }

    @Override
    public String format(Item value) throws InvalidTypeException {
        return value == null ? null : innerCodec.format(toUDTValue(value));
    }

    protected Item toAddress(UDTValue value) {
        return value == null ? null : new Item(
                value.getInt("i_id"),
                value.getInt("ol_number"),
                value.getInt("supply_w_id"),
                value.getDouble("i_quantity"),
                value.getTimestamp("i_delivery_d"),
                value.getString("i_dist_info"),
                value.getDouble("i_amount")
        );
    }

    protected UDTValue toUDTValue(Item value) {
        return value == null ? null : userType.newValue()
                .setInt("i_id", value.getOlItemId())
                .setInt("ol_number", value.getOlNumber())
                .setInt("supply_w_id", value.getOlSuppWarehouseId())
                .setDouble("i_quantity", value.getOlQuantity())
                .setDate("i_delivery_d", null)
                .setString("i_dist_info", value.getOlDistInfo())
                .setDouble("i_amount", value.getOlAmount())
                ;
    }
}