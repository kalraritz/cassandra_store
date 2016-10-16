package com.cassandra.transactions;

import com.cassandra.beans.Item;
import com.cassandra.utilities.Lucene;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import java.io.PrintWriter;
import java.util.*;

public class PopularItemTransaction {

    final String[] columns_next_order = {"no_d_next_o_id"};
    final String[] columns_stock = {"s_i_id", "s_quantity"};

    public void checkPopularItem(int w_id, int d_id, int num_last_orders, Session session, PrintWriter printWriter, Lucene lucene) {
        try {
            //Get the latest d_next_oid
            Statement getDNextOID = QueryBuilder.select(columns_next_order).from("next_order")
                    .where(QueryBuilder.eq("no_w_id", w_id))
                    .and(QueryBuilder.eq("no_d_id", d_id));
            ResultSet results = session.execute(getDNextOID);
            int d_next_oid = results.one().getInt("no_d_next_o_id");
            int start_index = d_next_oid - num_last_orders;

            //Get the last L orders
            Statement getLastLOrders = QueryBuilder.select().all().from("new_order_transaction")
                    .where(QueryBuilder.eq("o_w_id", w_id))
                    .and(QueryBuilder.eq("o_d_id", d_id))
                    .and(QueryBuilder.gte("o_id", start_index))
                    .and(QueryBuilder.lte("o_id", d_next_oid));
            results = session.execute(getLastLOrders);
            Set<Integer> itemids = new HashSet<Integer>();
            Map<Integer, List<Integer>> orderItemsMapping = new HashMap<Integer, List<Integer>>();

            int c_id = 0;
            printWriter.write("POPULAR ITEM TRANSACTION--------" + "\n");
            printWriter.write("(W_ID, D_ID, NUM_OF_LAST_ORDER_TO_BE_EXAMINED)(" + w_id + ", " + d_id + ", " + num_last_orders + ")\n");

            //Add itemids to a list to fetch the quantity for each item
            //Create a map of order-items.
            for (Row r : results.all()) {
                Set<Item> orders = r.getSet("o_items", Item.class);
                int order_id = r.getInt("o_id");
                Date oentryd = r.getTimestamp("o_entry_d");
                c_id = r.getInt("c_id");
                String customerStaticInfo = lucene.search(w_id + "" + d_id + "" + c_id, "customer-id", "customer-csv").get(0);
                String indexData[] = customerStaticInfo.split(",");
                String custname = "Customer name: " + indexData[3] + " " + indexData[4] + " " + indexData[5];
                printWriter.write("(O_ID, O_ENTRY_D, CUST_NAME)(" + order_id + ", " + oentryd + ", " + custname + "\n");
                Iterator<Item> order_items = orders.iterator();
                while (order_items.hasNext()) {
                    Item item = order_items.next();
                    itemids.add(item.getOlItemId());
                    List<Integer> items = null;
                    if (orderItemsMapping.get(order_id) == null) {
                        items = new ArrayList<Integer>();
                    } else {
                        items = orderItemsMapping.get(order_id);
                    }
                    items.add(item.getOlItemId());
                    orderItemsMapping.put(order_id, items);
                }
            }

            //Get quantity for each item and store in item-quantity map
            Statement itemPrices = QueryBuilder.select(columns_stock).from("stock_level_transaction")
                    .where(QueryBuilder.eq("s_w_id", w_id))
                    .and(QueryBuilder.in("s_i_id", itemids.toArray()));
            results = session.execute(itemPrices);
            Map<Integer, Double> orderItemQuantity = new HashMap<Integer, Double>();
            Map<Integer, String> orderItemName = new HashMap<Integer, String>();
            for (Row r : results.all()) {
                double item_quantity = r.getDouble("s_quantity");
                int item_id = r.getInt("s_i_id");
                orderItemQuantity.put(item_id, item_quantity);
                String[] itemStaticInfo = lucene.search(item_id + "", "item-id", "item-csv").get(0).split(",");
                String itemName = itemStaticInfo[1];
                orderItemName.put(item_id, itemName);
            }

            Map<Integer, List<Integer>> itemOrdersMap = new HashMap<Integer, List<Integer>>();
            for (Map.Entry<Integer, List<Integer>> entry : orderItemsMapping.entrySet()) {
                for (Integer itemid : entry.getValue()) {
                    List<Integer> items = null;
                    if (itemOrdersMap.get(itemid) == null) {
                        items = new ArrayList<Integer>();
                    } else {
                        items = itemOrdersMap.get(itemid);
                    }
                    items.add(entry.getKey());
                }
            }
            //Iterate over order-item map and get max item id for each order
            for (Map.Entry<Integer, List<Integer>> entry : orderItemsMapping.entrySet()) {
                int popularItem = getMaxQuantity(entry.getValue(), orderItemQuantity);
                printWriter.write("(ITEM_NAME, ITEM_QUANTITY, PERCENTAGE OF ORDERS IN S THAT CONTAINS THIS ITEM)(" + orderItemName.get(popularItem) + "," + orderItemQuantity.get(popularItem) + ", " + ((itemOrdersMap.get(popularItem).size() / orderItemsMapping.size()) * 100) + "%)\n");
            }
            printWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Returns max item id
    private int getMaxQuantity(List<Integer> value, Map<Integer, Double> orderItemQuantity) {
        Iterator<Integer> it = value.iterator();
        double max = Integer.MIN_VALUE;
        int max_item_id = 0;
        while (it.hasNext()) {
            int item_id = it.next();
            double item_quantity = orderItemQuantity.get(item_id);
            if (item_quantity > max) {
                max = item_quantity;
                max_item_id = item_id;
            }
        }
        return max_item_id;
    }
}
