package com.cassandra.csv;

import com.cassandra.beans.Item;
import com.cassandra.utilities.Lucene;
import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * Created by manisha on 09/10/2016.
 */
public class OrderStatusTransactionCsv {

    private static Logger logger = Logger.getLogger(OrderStatusTransactionCsv.class);

    public void prepareCsv() {
        PrintWriter pw = null;
        logger.info("Preparing csv for OrderStatusTransaction....");
        try {
            pw = new PrintWriter(new File("/Users/manisha/NUS/DD/project/csvFiles/orderStatusTransaction.csv"));
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = new FileInputStream("/Users/manisha/Downloads/D8-data/order.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            InputStreamReader orderFile = new InputStreamReader(classLoader.getResource("order-test.csv").openStream());

            CSVReader orderCsv = new CSVReader(inputStreamReader);
            Iterator<String[]> iterator = orderCsv.iterator();
            while (iterator.hasNext()) {
                List<String> orderStatusRowList = new ArrayList<>();
                String[] orderRow = iterator.next();
                orderStatusRowList.add(orderRow[0]); // w_id
                orderStatusRowList.add(orderRow[1]); //d_id
                orderStatusRowList.add(orderRow[3]); //c_id
                orderStatusRowList.add(orderRow[2]); //o_id
                List<String> customer = new Lucene().search(orderRow[0] + orderRow[1] + orderRow[3], "customer-id", "customer-csv");
                String[] customerRow = customer.get(0).split(",");
                orderStatusRowList.add(customerRow[16]); //c_balance
                String ddDate = orderRow[7];
                if (!ddDate.equals("null")) {
                    ddDate = "'" + ddDate + "'";
                }
                orderStatusRowList.add(ddDate); // entryDate
                if (orderRow[4].equals("null"))
                    orderRow[4] = "";
                orderStatusRowList.add(orderRow[4]); //carrier_id
                List<String> orderItems = new Lucene().search(orderRow[0] + orderRow[1] + orderRow[2], "order-id", "order-line-csv");
                List<String> orderItemList = new ArrayList<>();
                for (String string : orderItems) {
                    String[] orderLineRow = string.split(",");
                    String deliveryDate = orderLineRow[5];
                    if (!deliveryDate.equals("null")) {
                        deliveryDate = "\'" + deliveryDate + "\'";
                    }
                    String str = "{i_id: " + orderLineRow[4] + "," + "ol_number: " + orderLineRow[3] + "," + "supply_w_id: " + orderLineRow[7] + "," +
                            "i_amount: " + orderLineRow[6] + "," + "i_quantity: " + orderLineRow[8] + "," + "i_delivery_d: " + deliveryDate + "," + "i_dist_info: " + "\'" + orderLineRow[9] + "\'" + "}";
                    orderItemList.add(str);
                }
                String itemSet = "{" + StringUtils.join(orderItemList, ",") + "}";
                orderStatusRowList.add(itemSet);
                String str = StringUtils.join(orderStatusRowList, ",");
                String insertOrder = "INSERT INTO thehood.New_Order_Transaction (o_w_id,o_d_id,o_id,o_c_id,o_entry_d,o_carrier_id,o_ol_cnt,o_all_local,o_items) VALUES (" + str + ")";
                pw.write(insertOrder + ";" + "\n");
                pw.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in  preparing orderStatusTransaction csv");

        } finally {
            pw.close();
            logger.info("done preparing orderStatusTransaction csv!!");
        }
    }

}
