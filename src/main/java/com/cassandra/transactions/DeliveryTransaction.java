package com.cassandra.transactions;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.*;

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
    public void readDeliveryTransaction(int w_id, int carrier_id, Session session, PrintWriter printWriter)
    {
        w_id = 1;
        try
        {
            List<ResultSet> resultSetList = new ArrayList<>();

            //Get oldest order for 10 districts
            for(int i=1; i<=10; i++){
                Statement getOrders = QueryBuilder.select().all().from("new_order_transaction")
                        .where(QueryBuilder.eq("o_w_id",w_id))
                        .and(QueryBuilder.eq("o_carrier_id",-1))
                        .and(QueryBuilder.eq("o_d_id", i))
                        .limit(1);
                ResultSet results= session.execute(getOrders);
                resultSetList.add(results);
            }

            for(ResultSet resultSet: resultSetList){
                for(Row r:resultSet.all()){
                    int order_id = r.getInt("o_id");
                    int d_id = r.getInt("o_d_id");
                    int c_id = r.getInt("o_c_id");
                    Set<Item> orders = r.getSet("o_items",Item.class);
                    Iterator<Item> order_items = orders.iterator();
                    Set<Item> updated_orders = new HashSet<Item>();
                    Item item = null;
                    int ol_amt_sum = 0;
                    //Create a new items set with updated delivery date
                    while(order_items.hasNext())
                    {
                        item = order_items.next();
                        ol_amt_sum += item.getOlAmount();
                        item.setOlDeliveryDate(new Timestamp(new Date().getTime()));
                        updated_orders.add(item);
                    }
                    ol_amt_sum = 100;
                    //Update new order with carrier id and items set
                    Statement NewOrderDeliveryUpdate = QueryBuilder.update("new_order_transaction").with(QueryBuilder.set("o_carrier_id",carrier_id ))
                            .and(QueryBuilder.set("o_items", updated_orders))
                            .where(QueryBuilder.eq("o_id", order_id))
                            .and(QueryBuilder.eq("o_w_id",w_id))
                            .and(QueryBuilder.eq("o_d_id",d_id));
                    session.execute(NewOrderDeliveryUpdate);


                    Statement CustomerOrder = QueryBuilder.select().all().from("customer_data")
                            .where(QueryBuilder.eq("c_w_id",w_id))
                            .and(QueryBuilder.eq("c_d_id",d_id))
                            .and(QueryBuilder.eq("c_id",c_id));
                    Row customerResult= session.execute(CustomerOrder).one();
                    double balance = customerResult.getDouble("c_balance") + ol_amt_sum;
                    int delivery_cnt = customerResult.getInt("c_delivery_cnt") + 1;

                    Statement CustomerDataUpdate = QueryBuilder.update("customer_data").with(QueryBuilder.set("c_balance",balance ))
                            .and(QueryBuilder.set("c_delivery_cnt", delivery_cnt))
                            .where(QueryBuilder.eq("c_w_id", w_id))
                            .and(QueryBuilder.eq("c_d_id", d_id))
                            .and(QueryBuilder.eq("c_id", c_id));
                    session.execute(CustomerDataUpdate);
                }
            }



        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}