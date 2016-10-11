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

/**
 * Created by ritesh on 09/10/16.
 */
public class NewOrderTransaction {


    public void newOrderTransaction(int w_id, int d_id, int c_id, ArrayList<String> itemlineinfo, Session session)
    {
        try
        {
            // put the order in order status trasaction
            // put the order status transaction
            // update customer data
            // update next order
            // update stock level
            final String[] columns_next_order = {"d_next_o_id"};
            Statement getDNextOID = QueryBuilder.select(columns_next_order).from("next_order")
                    .where(QueryBuilder.eq("o_w_id",w_id)).and(QueryBuilder.eq("o_d_id",d_id));
            ResultSet results= session.execute(getDNextOID);
            int d_next_oid = results.one().getInt("d_next_o_id");

            LinkedList<String> columns = new LinkedList<String>();
            columns.add("o_w_id");
            columns.add("o_d_id");
            columns.add("o_id");
            columns.add("o_c_id");
            columns.add("o_entry_d");
            columns.add("o_carrier_id");
            columns.add("o_ol_cnt");
            columns.add("o_all_local");
            columns.add("o_items");
            LinkedList<Object> values = new LinkedList<Object>();
            values.add(w_id);
            values.add(d_id);
            values.add(d_next_oid + 1);
            values.add(c_id);
            values.add(new Date());
            values.add(null);
            values.add(itemlineinfo.size());
            HashSet<Item> items = new HashSet<Item>();
            int itemnum = 0;
            /*for(String item: itemlineinfo) {
                String[] itemline = item.split(",");
                String[] itemRow = litem.get(0).split(",");
                Item eachitem = new Item(Integer.parseInt(itemline[0]), itemRow[1], ++itemnum, Integer.parseInt(itemline[1]), Double.parseDouble(itemline[2]), null, );
                for(String itm:its)
                    eachitem(Integer.parseInt(itm));
                items.add(eachitem);
            }*/
            // OL I ID,OL SUPPLY W ID,OL QUANTITY
            double all_local = 1;

            for(Item it: items) {
                if(it.get(1) != w_id) {
                    all_local = 0;
                    break;
                }
            }
            values.add(all_local);
            HashSet<Item> items = new HashSet<Item>();
            values.add();

            Statement insertNewOrder = QueryBuilder.insertInto("new_order_transaction").values()

            Statement orderStatus = QueryBuilder.select().all().from("order_status_transaction")
                    .where(QueryBuilder.eq("o_w_id",w_id))
                    .and(QueryBuilder.eq("o_d_id",d_id))
                    .and(QueryBuilder.eq("o_c_id",c_id))
                    .limit(1);
            ResultSet results= session.execute(orderStatus);

            for(Row r:results.all()){
                System.out.println("Customer name : "+r.getString("c_first")
                        +r.getString("c_middle")+r.getString("c_last"));
                System.out.println(r.getDouble("c_balance"));
                Set<Item> orders = r.getSet("ol_i_id",Item.class);
                Iterator<Item> order_items = orders.iterator();
                while(order_items.hasNext())
                {
                    Item item = order_items.next();
                    System.out.println("Item number: "+item.getOlItemId());
                    System.out.println("Warehouse number: "+item.getOlSuppWarehouseId());
                    System.out.println("Quantity number: "+item.getOlQuantity());
                    System.out.println("Total price: "+item.getOlAmount());
                    System.out.println("Date and time of delivery: "+item.getOlDeliveryDate());
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
=======
//package com.cassandra.transactions;
//
//import com.cassandra.beans.Item;
//import com.cassandra.utilities.Lucene;
//import com.datastax.driver.core.ResultSet;
//import com.datastax.driver.core.Row;
//import com.datastax.driver.core.Session;
//import com.datastax.driver.core.Statement;
//import com.datastax.driver.core.querybuilder.QueryBuilder;
//
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * Created by ritesh on 09/10/16.
// */
//public class NewOrderTransaction {
//
//
//    public void newOrderTransaction(int w_id, int d_id, int c_id, ArrayList<String> itemlineinfo, Session session)
//    {
//        try
//        {
//            // put the order in order status trasaction
//            // put the order status transaction
//            // update customer data
//            // update next order
//            // update stock level
//            final String[] columns_next_order = {"d_next_o_id"};
//            Statement getDNextOID = QueryBuilder.select(columns_next_order).from("next_order")
//                    .where(QueryBuilder.eq("o_w_id",w_id)).and(QueryBuilder.eq("o_d_id",d_id));
//            ResultSet results= session.execute(getDNextOID);
//            int d_next_oid = results.one().getInt("d_next_o_id");
//
//            LinkedList<String> columns = new LinkedList<String>();
//            columns.add("o_w_id");
//            columns.add("o_d_id");
//            columns.add("o_id");
//            columns.add("o_c_id");
//            columns.add("o_entry_d");
//            columns.add("o_carrier_id");
//            columns.add("o_ol_cnt");
//            columns.add("o_all_local");
//            columns.add("o_items");
//            LinkedList<Object> values = new LinkedList<Object>();
//            values.add(w_id);
//            values.add(d_id);
//            values.add(d_next_oid + 1);
//            values.add(c_id);
//            values.add(new Date());
//            values.add(null);
//            values.add(itemlineinfo.size());
//            HashSet<Item> items = new HashSet<Item>();
//            int itemnum = 0;
//            /*for(String item: itemlineinfo) {
//                String[] itemline = item.split(",");
//                String[] itemRow = litem.get(0).split(",");
//                Item eachitem = new Item(Integer.parseInt(itemline[0]), itemRow[1], ++itemnum, Integer.parseInt(itemline[1]), Double.parseDouble(itemline[2]), null, );
//                for(String itm:its)
//                    eachitem(Integer.parseInt(itm));
//                items.add(eachitem);
//            }*/
//            // OL I ID,OL SUPPLY W ID,OL QUANTITY
//            double all_local = 1;
//
//            for(ArrayList<Integer> it: items) {
//                if(it.get(1) != w_id) {
//                    all_local = 0;
//                    break;
//                }
//            }
//            values.add(all_local);
//            HashSet<Item> items = new HashSet<Item>();
//            values.add();
//
//            Statement insertNewOrder = QueryBuilder.insertInto("new_order_transaction").values()
//
//            Statement orderStatus = QueryBuilder.select().all().from("order_status_transaction")
//                    .where(QueryBuilder.eq("o_w_id",w_id))
//                    .and(QueryBuilder.eq("o_d_id",d_id))
//                    .and(QueryBuilder.eq("o_c_id",c_id))
//                    .limit(1);
//            ResultSet results= session.execute(orderStatus);
//
//            for(Row r:results.all()){
//                System.out.println("Customer name : "+r.getString("c_first")
//                        +r.getString("c_middle")+r.getString("c_last"));
//                System.out.println(r.getDouble("c_balance"));
//                Set<Item> orders = r.getSet("ol_i_id",Item.class);
//                Iterator<Item> order_items = orders.iterator();
//                while(order_items.hasNext())
//                {
//                    Item item = order_items.next();
//                    System.out.println("Item number: "+item.getOlItemId());
//                    System.out.println("Warehouse number: "+item.getOlSuppWarehouseId());
//                    System.out.println("Quantity number: "+item.getOlQuantity());
//                    System.out.println("Total price: "+item.getOlAmount());
//                    System.out.println("Date and time of delivery: "+item.getOlDeliveryDate());
//                }
//            }
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//}
