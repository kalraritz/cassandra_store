package com.cassandra.csv;

import com.cassandra.utilities.Lucene;
import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created by manisha on 09/10/2016.
 */
public class NewOrderTransactionCsv {

    private static Logger logger = Logger.getLogger(NewOrderTransactionCsv.class);

    public void prepareCsv(Lucene lucene, Properties properties) {
        String csv_dump_path = properties.getProperty("csv_dump_path");
        String csv_files_path = properties.getProperty("csv_files_path");
        PrintWriter pw = null;
        logger.info("Preparing csv for NewOrderTransaction...");
        try{
            pw = new PrintWriter(new File(csv_dump_path+"new_order_transaction_csv"));
            InputStream inputStream = new FileInputStream(csv_files_path+"order.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            CSVReader orderCsv = new CSVReader(inputStreamReader);
            Iterator<String[]> iterator =  orderCsv.iterator();
            while(iterator.hasNext())
            {
                List<String> newOrderRowList = new ArrayList<>();
                String[] orderRow = iterator.next();
                if(orderRow[0].equals("2")){
                    break;
                }
                newOrderRowList.add(orderRow[0]);
                newOrderRowList.add(orderRow[1]);
                newOrderRowList.add(orderRow[2]);
                newOrderRowList.add(orderRow[3]);
                newOrderRowList.add(orderRow[7]);
                if(!orderRow[5].equals("null") )
                    newOrderRowList.add(orderRow[5]);
                else
                    newOrderRowList.add(String.valueOf(-1));
                newOrderRowList.add(orderRow[5]);
                newOrderRowList.add(orderRow[6]);
                List<String> orderLineItems = lucene.search(orderRow[0] + orderRow[1] + orderRow[2], "order-id", "order-line-csv");
                String set = "{";
                List<String> orderItemList = new ArrayList<>();
                for(String string: orderLineItems){
                    String[] orderLineRow = string.split(",");
                    String str = "{i_id: "+orderLineRow[4]+","+ "ol_number: "+orderLineRow[3]+","+"supply_w_id: "+orderLineRow[7]+","+
                            "i_amount: "+orderLineRow[6]+","+"i_quantity: "+ orderLineRow[8]+","+ "i_delivery_d: "+"\'"+"\'"+","+"i_dist_info: "+"\'"+orderLineRow[9]+"\'"+"}";
                    orderItemList.add(str);
                }
                String itemSet = "\"{"+ StringUtils.join(orderItemList, ",") +"}\"";
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
