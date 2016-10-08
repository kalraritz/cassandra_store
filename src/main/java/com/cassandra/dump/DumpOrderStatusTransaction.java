package com.cassandra.dump;

import com.cassandra.CassandraSession;
import com.cassandra.beans.Item;
import com.cassandra.models.NextOrder;
import com.cassandra.models.Order;
import com.cassandra.models.OrderStatus;
import com.cassandra.utilities.Lucene;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.opencsv.CSVReader;
import org.apache.log4j.Logger;

import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by manisha on 08/10/2016.
 */
public class DumpOrderStatusTransaction {

    private static Logger logger = Logger.getLogger(DumpOrderStatusTransaction.class);

    public void dump() {
        try {
            Session session = CassandraSession.getSession();
            MappingManager manager = new MappingManager(session);
            Mapper<OrderStatus> mapper = manager.mapper(OrderStatus.class);
            ClassLoader classLoader = getClass().getClassLoader();
            InputStreamReader orderFile = new InputStreamReader(classLoader.getResource("order.csv").openStream());
            CSVReader orderCsv = new CSVReader(orderFile);
            Iterator<String[]> iterator = orderCsv.iterator();
            while(iterator.hasNext()) {
                //Order csv row
                String[] orderRow = iterator.next();

                // Customer data for wid, did, cid
                List<String> customer = new Lucene().search(orderRow[0]+orderRow[1]+orderRow[3], "customer-id", "customer-csv");

                // Set of orderline items for wid, did, oid
                List<String> orderItems = new Lucene().search(orderRow[0]+orderRow[1]+orderRow[2], "order-id", "order-line-csv");
                Set<Item> orderItemsSet = new HashSet<>();
                for(String string: orderItems){
                    String[] orderLineRow = string.split(",");
                    // item for itemId (item id in orderLine)
                    List<String> items = new Lucene().search(orderLineRow[4], "item-id", "item-csv");
                    String itemName = items.get(0).split(",")[1];
                    Date deliveryDate = new Date();
                    if(orderLineRow[5] != null && !orderLineRow[5].equals("null")) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.SSS");
                        try {
                            deliveryDate = simpleDateFormat.parse(orderLineRow[5]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    Item item = new Item(Integer.parseInt(orderLineRow[4]), itemName, Integer.parseInt(orderLineRow[3]),
                    Integer.parseInt(orderLineRow[7]), Double.parseDouble(orderLineRow[8]), deliveryDate, orderLineRow[9],
                            Double.parseDouble(orderLineRow[6])
                    );
                    orderItemsSet.add(item);
                }
                String[] customerRow = customer.get(0).split(",");
                Date entryDate = new Date();
                if(orderRow[7] != null && !orderRow[7].equals("null")) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.SSS");
                    try {
                        entryDate = simpleDateFormat.parse(orderRow[7]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                Integer carrierId = null;
                if(orderRow[4] == "null" || orderRow[4] == null) {
                    carrierId = Integer.parseInt(orderRow[4]);
                }
                OrderStatus orderStatus = new OrderStatus(Integer.parseInt(orderRow[0]), Integer.parseInt(orderRow[1]),
                            Integer.parseInt(orderRow[3]), Integer.parseInt(orderRow[2]), Double.parseDouble(customerRow[16]),
                        entryDate, Integer.parseInt(orderRow[4]), orderItemsSet);
                mapper.save(orderStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CassandraSession.closeSession();
        }
    }
}
