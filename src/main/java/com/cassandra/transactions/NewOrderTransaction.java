package com.cassandra.transactions;

import com.cassandra.TransactionDriver;
import com.cassandra.beans.Item;
import com.cassandra.utilities.Lucene;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.RunnableFuture;

/**
 * Created by ritesh on 09/10/16.
 */
public class NewOrderTransaction {

    static List<String> columns = Arrays.asList("o_w_id", "o_d_id", "o_id","o_c_id","o_entry_d","o_carrier_id","o_ol_cnt","o_all_local","o_items");

    static final String[] columns_next_order = {"no_d_next_o_id"};

    public void newOrderTransaction(int w_id, int d_id, int c_id, ArrayList<String> itemlineinfo, Session session,Lucene index, PrintWriter printWriter) {
        try {
            // put the order in order status trasaction
            // put the order status transaction
            // update customer data
            // update next order
            // update stock level

            String getDNextOID = "select no_d_next_o_id from next_order where no_w_id ="+1+" and no_d_id = 1";
            ResultSet results = session.execute(getDNextOID);
            int d_next_oid = results.one().getInt("no_d_next_o_id");

            String dNextOIDUpdate = "update next_order set no_d_next_o_id = "+(d_next_oid+1)+" where no_w_id ="+1+" and no_d_id = 1";
            session.execute(dNextOIDUpdate);

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

            String update = "BEGIN BATCH ";

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

                update = update + " UPDATE stock_level_transaction set s_quantity="+adjustedQuantiy+", s_ytd="+(s_ytd+ol_quantity)
                        +", s_order_cnt="+(s_order_cnt+1)+", s_remote_cnt="+(s_remote_cnt+1)+" where s_w_id="+w_id+" and s_i_id="+ol_i_id+ ";";}
            values.add(all_local);
            values.add(items);

            update +="APPLY BATCH;";
            Statement insertNewOrder = QueryBuilder.insertInto("new_order_transaction").values(columns, values);
            session.execute(insertNewOrder);
            session.execute(update);
            String[] districtStaticInfo = index.search(w_id + "" + d_id + "", "district-id", "district-csv").get(0).split(",");
            String[] warehouseStaticInfo = index.search(w_id + "", "warehouse-id", "warehouse-csv").get(0).split(",");
            String[] customerStaticInfo = index.search(w_id + "" + d_id + "" + c_id + "", "customer-id", "customer-csv").get(0).split(",");

            total_amount = total_amount * (1 + Double.parseDouble(districtStaticInfo[8]) + Double.parseDouble(warehouseStaticInfo[8]))
                    * (1 - Double.parseDouble(customerStaticInfo[15]));
            printWriter.write("New Order Transaction--------"+"\n");
            printWriter.write("Total amount : " + total_amount+"\n\n");
            printWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}