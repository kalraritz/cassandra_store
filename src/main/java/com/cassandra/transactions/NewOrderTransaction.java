package com.cassandra.transactions;

import com.cassandra.beans.Item;
import com.cassandra.utilities.Lucene;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.RunnableFuture;

/**
 * Created by ritesh on 09/10/16.
 */
public class NewOrderTransaction extends Thread {




    public NewOrderTransaction(ArrayList<NewOrderTransaction> listOfObjects,Session session,Lucene index){
        if(this.listOfObjects == null)
        {
            this.listOfObjects =  new ArrayList<>(listOfObjects);
        }
        if(NewOrderTransaction.session == null || NewOrderTransaction.index == null)
        {
            NewOrderTransaction.session = session;
            NewOrderTransaction.index = index;
        }
    }

    private int w_id;
    private int d_id;
    private int c_id;
    private ArrayList<String> itemlineinfo;
    private static Session session;
    private static Lucene index;
    private ArrayList<NewOrderTransaction> listOfObjects;
    static List<String> columns = Arrays.asList("o_w_id", "o_d_id", "o_id","o_c_id","o_entry_d","o_carrier_id","o_ol_cnt","o_all_local","o_items");

    public NewOrderTransaction(int w_id, int d_id, int c_id, ArrayList<String> itemlineinfo) {
        this.w_id = w_id;
        this.d_id = d_id;
        this.c_id = c_id;
        this.itemlineinfo = itemlineinfo;

    }


    @Override
    public void run() {
        ArrayList<NewOrderTransaction> objs =  new ArrayList<>(this.listOfObjects) ;
        System.out.println(objs.size());
        for (NewOrderTransaction ob :objs)
        {
            final String[] columns_next_order = {"no_d_next_o_id"};
            Statement getDNextOID = QueryBuilder.select(columns_next_order).from("next_order")
                    .where(QueryBuilder.eq("no_w_id", ob.w_id)).and(QueryBuilder.eq("no_d_id", ob.d_id));
            ResultSet results = session.execute(getDNextOID);
            int d_next_oid = results.one().getInt("no_d_next_o_id");
            List<Object> values =  new ArrayList<Object>();
        }
    }

    public void newOrderTransaction(int w_id, int d_id, int c_id, ArrayList<String> itemlineinfo, Session session,Lucene index) {
        try {
            // put the order in order status trasaction
            // put the order status transaction
            // update customer data
            // update next order
            // update stock level
            final String[] columns_next_order = {"no_d_next_o_id"};
            Statement getDNextOID = QueryBuilder.select(columns_next_order).from("next_order")
                    .where(QueryBuilder.eq("no_w_id", w_id)).and(QueryBuilder.eq("no_d_id", d_id));
            ResultSet results = session.execute(getDNextOID);
            int d_next_oid = results.one().getInt("no_d_next_o_id");
            List<Object> values =  new ArrayList<Object>();

            values.add(w_id);
            values.add(d_id);
            values.add(d_next_oid + 1);
            values.add(c_id);
            values.add(new Date());
            values.add(null);
            values.add(itemlineinfo.size());

            double all_local = 1;
            int itemnum = 0;
            Set<Item> items = new HashSet<>();
            int ol_i_id = 0;
            double total_amount = 0.0;

            for (String item : itemlineinfo) {

                String[] itemline = item.split(",");

                //Order line info update
                String[] itemStaticInfo = index.search(itemline[0] + "", "item-id", "item-csv").get(0).split(",");
                ol_i_id = Integer.parseInt(itemline[0]);
                int ol_supply_w_id = Integer.parseInt(itemline[1]);
                double ol_quantity = Double.parseDouble(itemline[2]);
                double itemPrice = Double.parseDouble(itemStaticInfo[3]);
                Item eachitem = new Item(ol_i_id, ++itemnum, ol_supply_w_id, ol_quantity, null, "", itemPrice * ol_quantity);
                items.add(eachitem);
                total_amount += itemPrice * ol_quantity;
                if (ol_supply_w_id != w_id) {
                    all_local = 0;
                }


                //Stock info update
                String[] stockStaticInfo = index.search(w_id + "" + ol_i_id + "", "stock-id", "stock-csv").get(0).split(",");
                double stockQuantity = Double.parseDouble(stockStaticInfo[2]);
                double adjustedQuantiy = stockQuantity - ol_quantity;
                if (adjustedQuantiy < 10) {
                    adjustedQuantiy = adjustedQuantiy + 100;
                }


                Statement stockInfo = QueryBuilder.select().all().from("stock_level_transaction")
                        .where(QueryBuilder.eq("s_w_id", w_id))
                        .and(QueryBuilder.eq("s_i_id", ol_i_id));

                Row stockInfoResults = session.execute(stockInfo).one();

                double s_ytd = stockInfoResults.getDouble("s_ytd");
                int s_order_cnt = stockInfoResults.getInt("s_order_cnt");
                int s_remote_cnt = stockInfoResults.getInt("s_remote_cnt");
                Statement stockUpdate = QueryBuilder.update("stock_level_transaction").with(QueryBuilder.set("s_quantity", adjustedQuantiy))
                        .and(QueryBuilder.set("s_ytd", s_ytd + ol_quantity))
                        .and(QueryBuilder.set("s_order_cnt", s_order_cnt + 1))
                        .and(QueryBuilder.set("s_remote_cnt", s_remote_cnt + 1))
                        .where(QueryBuilder.eq("s_w_id", w_id))
                        .and(QueryBuilder.eq("s_i_id", ol_i_id));
                session.execute(stockUpdate);
            }

            values.add(all_local);
            values.add(items);
            Statement insertNewOrder = QueryBuilder.insertInto("new_order_transaction").values(columns, values);
            session.execute(insertNewOrder);

            String[] districtStaticInfo = index.search(w_id + "" + d_id + "", "district-id", "district-csv").get(0).split(",");
            String[] warehouseStaticInfo = index.search(w_id + "", "warehouse-id", "warehouse-csv").get(0).split(",");
            String[] customerStaticInfo = index.search(w_id + "" + d_id + "" + c_id + "", "customer-id", "customer-csv").get(0).split(",");

            total_amount = total_amount * (1 + Double.parseDouble(districtStaticInfo[8]) + Double.parseDouble(warehouseStaticInfo[8]))
                    * (1 - Double.parseDouble(customerStaticInfo[15]));
            System.out.println("Total amount : " + total_amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}