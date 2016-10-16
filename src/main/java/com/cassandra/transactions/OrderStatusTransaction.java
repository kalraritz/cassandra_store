package com.cassandra.transactions;

import com.cassandra.beans.Item;
import com.cassandra.utilities.Lucene;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;

public class OrderStatusTransaction {

    static String customercolums[] = {"c_balance"};
    static String ordercolums[] = {"o_items"};

    public void readOrderStatus(int w_id, int d_id, int c_id, Session session, Lucene index, PrintWriter printWriter) {
        try {
            Statement orderStatus = QueryBuilder.select(ordercolums).from("new_order_transaction")
                    .where(QueryBuilder.eq("o_w_id", w_id))
                    .and(QueryBuilder.eq("o_d_id", d_id))
                    .and(QueryBuilder.eq("o_c_id", c_id));
            ResultSet results = session.execute(orderStatus);

            String[] customerData = index.search(w_id + "" + d_id + "" + c_id, "customer-id", "customer-csv").get(0).split(",");
            Statement CustomerData = QueryBuilder.select(customercolums).from("customer_data")
                    .where(QueryBuilder.eq("c_w_id", w_id))
                    .and(QueryBuilder.eq("c_d_id", d_id))
                    .and(QueryBuilder.eq("c_id", c_id));
            Row customerRow = session.execute(CustomerData).one();
            Iterator<Row> it = results.iterator();
            Row lastOrder = null;

            while (it.hasNext()) {
                lastOrder = it.next();
                if (!it.hasNext()) {
                    break;
                }
            }
            printWriter.write("ORDER STATUS TRANSACTION--------" + "\n");
            printWriter.write("Customer name : " + customerData[3]
                    + " " + customerData[4] + " " + customerData[5] + "\n");
            printWriter.write("Customer balance : " + customerRow.getDouble("c_balance") + "\n");
            Set<Item> orders = lastOrder.getSet("o_items", Item.class);
            Iterator<Item> order_items = orders.iterator();
            while (order_items.hasNext()) {
                Item item = order_items.next();
                printWriter.write("Item number: " + item.getOlItemId() + " | " + "Warehouse number: " + item.getOlSuppWarehouseId()
                        + " | " + "Quantity number: " + item.getOlQuantity() + " | " + "Total price: " + item.getOlAmount()
                        + " | " + "Date and time of delivery: " + item.getOlDeliveryDate() + "\n");
            }
            printWriter.write("\n");
            printWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}