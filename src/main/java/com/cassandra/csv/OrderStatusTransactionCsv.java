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


    public void prepareCsv() {
        try {
            PrintWriter pw = new PrintWriter(new File("/Users/manisha/NUS/DD/project/csvFiles/orderStatusTransaction.csv"));
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = new FileInputStream("/Users/manisha/Downloads/D8-data/order.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            InputStreamReader orderFile = new InputStreamReader(classLoader.getResource("order-test.csv").openStream());

            CSVReader orderCsv = new CSVReader(inputStreamReader);
            Iterator<String[]> iterator = orderCsv.iterator();
            while(iterator.hasNext()) {
                List<String> orderStatusList = new ArrayList<>();
                String[] orderRow = iterator.next();
                orderStatusList.add(orderRow[0]); // w_id
                orderStatusList.add(orderRow[1]); //d_id
                orderStatusList.add(orderRow[3]); //c_id
                orderStatusList.add(orderRow[2]); //o_id
                List<String> customer = new Lucene().search(orderRow[0]+orderRow[1]+orderRow[3], "customer-id", "customer-csv");
                String[] customerRow = customer.get(0).split(",");
                orderStatusList.add(customerRow[16]); //c_balance
                orderStatusList.add(orderRow[7]); // entryDate
                if(orderRow[4].equals("null"))
                    orderRow[4] = "";
                orderStatusList.add(orderRow[4]); //carrier_id
                List<String> orderItems = new Lucene().search(orderRow[0]+orderRow[1]+orderRow[2], "order-id", "order-line-csv");
                List<String> orderItemList = new ArrayList<>();
                for(String string: orderItems){
                    String[] orderLineRow = string.split(",");
                    String str = "{i_id: "+orderLineRow[4]+","+ "ol_number: "+orderLineRow[3]+","+"supply_w_id: "+orderLineRow[7]+","+
                "i_amount: "+orderLineRow[6]+","+"i_quantity: "+ orderLineRow[8]+","+ "i_delivery_d: "+"\'"+orderLineRow[5]+"\'"+","+"i_dist_info: "+"\'"+orderLineRow[9]+"\'"+"}";

                orderItemList.add(str);
                }
                String itemSet = "{"+ StringUtils.join(orderItemList, ",") +"}";
                orderStatusList.add(itemSet);
                String str = StringUtils.join(orderStatusList, ",");
                pw.write(str+ "\n");
                pw.flush();
            }
            pw.close();
            System.out.println("done preparing order Status transaction csv!!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in  preparing CustomerData csv");

        }
    }

}
