package com.cassandra.transactions;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.cassandra.beans.Item;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.CodecRegistry;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class DeliveryTransaction {
    public void readDeliveryTransaction(int w_id,int carrier_id,Session session)
    {
        try
        {
            Statement getOrders = QueryBuilder.select().all().from("new_order_transaction")
                    .where(QueryBuilder.eq("o_w_id",w_id))
                    .and(QueryBuilder.eq("o_carrier_id",null))
                    .limit(1);
            ResultSet results= session.execute(getOrders);
            for(Row r:results.all()){
                int order_id = r.getInt("o_id");
                int d_id = r.getInt("d_id");
                int c_id = r.getInt("c_id");
                Set<Item> orders = r.getSet("ol_i_id",Item.class);
                Iterator<Item> order_items = orders.iterator();
                Set<Item> updated_orders = new HashSet<Item>();
                Item item = null;
                int ol_amt_sum = 0;
                while(order_items.hasNext())
                {
                    ol_amt_sum += item.getOlAmount();
                    item = order_items.next();
                    item.setOlDeliveryDate(new Timestamp(new Date().getTime()));
                    updated_orders.add(item);
                }

                QueryBuilder.update("new_order_transaction").with(QueryBuilder.set("o_carrier_id",carrier_id ))
                        .and(QueryBuilder.set("ol_i_id", updated_orders))
                        .where(QueryBuilder.eq("o_id", order_id));
                Statement CustomerOrder = QueryBuilder.select().all().from("customer_data_transaction")
                        .where(QueryBuilder.eq("o_w_id",w_id))
                        .and(QueryBuilder.eq("d_id",d_id))
                        .and(QueryBuilder.eq("c_id",c_id));
                results= session.execute(CustomerOrder);
                double balance = results.one().getDouble("c_balance");
                int delivery_cnt = results.one().getInt("c_delivery");
                QueryBuilder.update("customer_data_transaction").with(QueryBuilder.set("c_balance",balance + ol_amt_sum ))
                        .and(QueryBuilder.set("delivery_cnt", delivery_cnt + 1))
                        .where(QueryBuilder.eq("o_id", order_id));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}