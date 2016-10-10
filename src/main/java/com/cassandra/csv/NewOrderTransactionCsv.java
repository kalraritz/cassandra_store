package com.cassandra.csv;

import com.cassandra.utilities.Lucene;
import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by manisha on 09/10/2016.
 */
public class NewOrderTransactionCsv {

    private static Logger logger = Logger.getLogger(NewOrderTransactionCsv.class);

    public void prepareCsv() {
        PrintWriter pw = null;
        logger.info("Preparing csv for NewOrderTransaction...");
        try{
           pw = new PrintWriter(new File("/Users/manisha/NUS/DD/project/csvFiles/newOrderTransaction.csv"));
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = new FileInputStream("/Users/manisha/Downloads/D8-data/order.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            CSVReader orderCsv = new CSVReader(inputStreamReader);
            Iterator<String[]> iterator =  orderCsv.iterator();
            while(iterator.hasNext()){
                List<String> newOrderRowList = new ArrayList<>();
                String[] orderRow = iterator.next();
                newOrderRowList.add(orderRow[0]);
                newOrderRowList.add(orderRow[1]);
                newOrderRowList.add(orderRow[2]);
                newOrderRowList.add(orderRow[3]);
                newOrderRowList.add(orderRow[7]);
                newOrderRowList.add(orderRow[4]);
                newOrderRowList.add(orderRow[5]);
                newOrderRowList.add(orderRow[6]);
                List<String> orderLineItems = new Lucene().search(orderRow[0] + orderRow[1] + orderRow[2], "order-id", "order-line-csv");
                String set = "{";
                List<String> orderItemList = new ArrayList<>();
                for(String string: orderLineItems){
                    String[] orderLineRow = string.split(",");
                    String str = "{i_id: "+orderLineRow[4]+","+ "ol_number: "+orderLineRow[3]+","+"supply_w_id: "+orderLineRow[7]+","+
                            "i_amount: "+orderLineRow[6]+","+"i_quantity: "+ orderLineRow[8]+","+ "i_delivery_d: "+"\'"+orderLineRow[5]+"\'"+","+"i_dist_info: "+"\'"+orderLineRow[9]+"\'"+"}";
                    orderItemList.add(str);
                }
                String itemSet = "{"+ StringUtils.join(orderItemList, ",") +"}";
                newOrderRowList.add(itemSet);
                String newOrder = StringUtils.join(newOrderRowList, ",");
                pw.write(newOrder+"\n");
                pw.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in  preparing NewOrderTransaction csv!!");
        } finally {
            pw.close();
            logger.info("Done preparing NewOrderTransaction csv!!");
        }
    }
}
