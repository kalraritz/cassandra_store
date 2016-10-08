package com.cassandra.dump;

import com.cassandra.CassandraSession;
import com.cassandra.beans.*;
import com.cassandra.models.Order;
import com.cassandra.utilities.Lucene;
import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.opencsv.CSVReader;
import org.apache.log4j.Logger;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by manisha on 24/09/2016.
 */
public class DumpNewOrderTransaction {
    private static Logger logger = Logger.getLogger(DumpNewOrderTransaction.class);
    private final ClassLoader classLoader;

    public DumpNewOrderTransaction() {
        this.classLoader = getClass().getClassLoader();
    }

    public void dump() {

        try {
            Session session = CassandraSession.getSession();
            MappingManager manager = new MappingManager(session);
            Mapper<Order> mapper = manager.mapper(Order.class);
            InputStreamReader orderFile = new InputStreamReader(classLoader.getResource("order.csv").openStream());
            CSVReader orderCsv = new CSVReader(orderFile);
            List<Order> orders = new ArrayList<>();
            Iterator<String[]> iterator = orderCsv.iterator();
            int count = 0;
            while(iterator.hasNext()) {
                count++;
                if(count % 10000 ==0)
                    logger.info("inserting............"+count);
                String[] string = iterator.next();
                Date entryDate = new Date();
                List<String> orderLineItems = new Lucene().search(string[0] + string[1] + string[2], "order-id", "order-line-csv");
                Set<Item> orderItemSet = new HashSet<>();
                for(String row: orderLineItems) {
                    String[] orderLineRow = row.split(",");
                    List<String> items = new Lucene().search(orderLineRow[4], "item-id", "item-csv");
                    String[] itemRow = items.get(0).split(",");
                    Date deliveryDate = new Date();
                    if(orderLineRow[5] != null && !orderLineRow[5].equals("null")) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.SSS");
                        try {
                            deliveryDate = simpleDateFormat.parse(orderLineRow[5]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    Item item = new Item(Integer.parseInt(orderLineRow[4]), itemRow[1], Integer.parseInt(orderLineRow[3]), Integer.parseInt(orderLineRow[7]),
                            Double.parseDouble(orderLineRow[8]), deliveryDate, orderLineRow[9], Double.parseDouble(orderLineRow[6]) );
                    orderItemSet.add(item);

                }
                if(string[7] != null && !string[7].equals("null")) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.SSS");
                    try {
                        entryDate = simpleDateFormat.parse(string[7]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                Integer carrierId = null;
                if(string[4] == "null" || string[4] == null) {
                    carrierId = Integer.parseInt(string[4]);
                }
                Order order = new Order(Integer.parseInt(string[0]), Integer.parseInt(string[1]), Integer.parseInt(string[2]),
                        entryDate, carrierId, Integer.parseInt(string[3]), Integer.parseInt(string[5]), Integer.parseInt(string[6]),
                        orderItemSet);
                mapper.save(order);

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CassandraSession.closeSession();
        }
    }

}
