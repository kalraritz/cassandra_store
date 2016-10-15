package com.cassandra.transactions;

import java.awt.*;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.cassandra.TransactionDriver;
import com.cassandra.beans.Item;
import com.cassandra.utilities.Lucene;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class StockLevelTransaction {

    final String[] columns_next_order = {"no_d_next_o_id"};
    final String[] columns_stock = {"s_i_id","s_quantity"};
    final String[] next_order_columns = {"o_items"};

    public void checkStockThreshold(int w_id,int d_id,double threshold,int num_last_orders,Session session,Lucene index)
    {
     //   System.out.println("Processing started....");
        try
        {
            //Get the latest d_next_oid
            Statement getDNextOID = QueryBuilder.select(columns_next_order).from("next_order")
                    .where(QueryBuilder.eq("no_w_id",w_id))
                    .and(QueryBuilder.eq("no_d_id",d_id));
            ResultSet results= session.execute(getDNextOID);
            int d_next_oid = results.one().getInt("no_d_next_o_id");
            int start_index = d_next_oid - num_last_orders;

            //Get the last L orders
            Statement getLastLOrders = QueryBuilder.select(next_order_columns).from("new_order_transaction")
                    .where(QueryBuilder.eq("o_w_id",w_id))
                    .and(QueryBuilder.eq("o_d_id",d_id))
                    .and(QueryBuilder.gte("o_id",start_index))
                    .and(QueryBuilder.lte("o_id",d_next_oid));
            results= session.execute(getLastLOrders);

            Set<Integer> itemids = new HashSet<Integer>();
            Map<Integer,String> itemNames = new HashMap<>();

            //Add itemids to a list to fetch the quantity for each item
            for(Row r:results.all()){
                Set<Item> orders = r.getSet("o_items",Item.class);
                Iterator<Item> order_items = orders.iterator();
                while(order_items.hasNext())
                {
                    Item item = order_items.next();
                    itemids.add(item.getOlItemId());
                    String[] itemStaticInfo = index.search(item.getOlItemId() + "", "item-id", "item-csv").get(0).split(",");
                    itemNames.put(item.getOlItemId(),itemStaticInfo[1]);
                }
            }
            Statement itemQuantity = QueryBuilder.select(columns_stock).from("stock_level_transaction")
                    .where(QueryBuilder.eq("s_w_id",w_id))
                    .and(QueryBuilder.in("s_i_id",itemids.toArray()));
            results = session.execute(itemQuantity);

            PrintWriter pw = TransactionDriver.pw;
            pw.write("Stock Level Transaction--------"+"\n");
            for(Row r:results.all()){
                double item_quantity = r.getDouble("s_quantity");
                if(item_quantity < threshold)
                {
                    int item_id = r.getInt("s_i_id");
                    pw.write("Item id : "+ item_id+"\n");
                    pw.write("Item name : "+ itemNames.get(item_id)+"\n");
                    pw.write("Item quantity "+item_quantity+"\n");
                }
            }
            pw.flush();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}