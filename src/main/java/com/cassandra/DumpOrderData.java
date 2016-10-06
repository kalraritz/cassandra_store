package com.cassandra;

import com.cassandra.beans.*;
import com.cassandra.models.Order;
import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.opencsv.CSVReader;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by manisha on 24/09/2016.
 */
public class DumpOrderData {

    private final ClassLoader classLoader;

    public DumpOrderData() {
        this.classLoader = getClass().getClassLoader();
    }


    public void dumpNewOrderTransactionData() {
        Map<OrderKey, Set<Item>> orderKeyItemsMap = getOrderKeyItemsMap();
        Cluster cluster = null;
        Session session = null;
        try {
            cluster = Cluster.builder().addContactPoint("192.168.48.247").build();
            session = cluster.connect("thehood");
            MappingManager manager = new MappingManager(session);
            Mapper<Order> mapper = manager.mapper(Order.class);
            InputStreamReader orderFile = new InputStreamReader(classLoader.getResource("order.csv").openStream());
            CSVReader orderCsv = new CSVReader(orderFile);
            final List<String[]> inputOrderData = orderCsv.readAll();
            for(String[] strings: inputOrderData) {
                Date entryDate = new Date();
                int warehouseId = Integer.parseInt(strings[0]);
                int districtId = Integer.parseInt(strings[1]);
                Integer carrierId = null;
                if(strings[4] == "null" || strings[4] == null)
                    carrierId =  Integer.parseInt(strings[4]);
                Long orderId = Long.parseLong(strings[2]);
                Set<OrderKey> set = orderKeyItemsMap.keySet();
                Iterator it = set.iterator();
                while(it.hasNext()){
                    OrderKey key = (OrderKey) it.next();
                    System.out.println(key.toString());
                }
                System.out.println(warehouseId+""+districtId+""+orderId);
                OrderKey orderKey =  new OrderKey(warehouseId, districtId, orderId);
                if(strings[7] != null && !strings[7].equals("null")) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.SSS");
                    try {
                        entryDate = simpleDateFormat.parse(strings[7]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                Order order = new Order(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Long.parseLong(strings[2]), entryDate,
                        carrierId, Long.parseLong(strings[3]), Integer.parseInt(strings[5]), Integer.parseInt(strings[6]),
                        orderKeyItemsMap.get(orderKey)
                );
                mapper.save(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (session != null && !session.isClosed())
                session.close();

            if (cluster != null && !cluster.isClosed())
                cluster.close();
        }


    }


    public void processBatchOrders (List<String[]> batchOrders) {
        Cluster cluster = null;
        Session session = null;
        try {
            cluster = Cluster.builder().addContactPoint("192.168.48.247").build();
            session = cluster.connect("thehood");
            MappingManager manager = new MappingManager(session);
            Mapper<Order> mapper = manager.mapper(Order.class);
            Map<OrderKey, Set<Item>> orderLineItemBean = getOrderKeyItemsMap();
            List<Order> orders = new ArrayList<>();
            for (String[] string : batchOrders) {
                Date entryDate = new Date();
               OrderKey orderKey = new OrderKey(Integer.parseInt(string[0]), Integer.parseInt(string[1]), Long.parseLong(string[2]));
               Set<Item> itemSet = orderLineItemBean.get(orderKey);
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
               Order order = new Order(orderKey.getWarehouseId(), orderKey.getDistrictId(), orderKey.getOrderId(),
                       entryDate, carrierId, Long.parseLong(string[3]), Integer.parseInt(string[5]), Integer.parseInt(string[6]),
                       itemSet);
               orders.add(order);
            }

            BatchStatement batchStatement = new BatchStatement();
            for(Order order: orders)
                batchStatement.add(mapper.saveQuery(order));
            session.execute(batchStatement);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cluster.close();
            session.close();
        }
    }
    public void dumNewOrderTransactionInBatches() {
        Map<OrderKey, Set<Item>> orderKeyItemsMap = getOrderKeyItemsMap();
        Cluster cluster = null;
        Session session = null;
        try {
            InputStreamReader orderFile = new InputStreamReader(classLoader.getResource("order.csv").openStream());
            CSVReader orderCsv = new CSVReader(orderFile);
            Iterator<String[]> iterator = orderCsv.iterator();
            List<String[]> batchOrders = new ArrayList<>();
            while(iterator.hasNext()) {
                batchOrders.add(iterator.next());
                if(batchOrders.size() == 1000) {
                    processBatchOrders(batchOrders);
                    batchOrders = new ArrayList<>();
                }
            }
            if(batchOrders.size()!=0) {
                processBatchOrders(batchOrders);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (session != null && !session.isClosed())
                session.close();

            if (cluster != null && !cluster.isClosed())
                cluster.close();
        }


    }

    public void dumpNextOrderData() {
        try {
            Cluster cluster = Cluster.builder().addContactPoint("192.168.48.247").build();
            Session session = cluster.connect("thehood");
            File warehouseFile = new File(classLoader.getResource("warehouse-test.csv").getFile());
            File districtFile = new File("district-test.csv");
            Map<Integer, WarehouseBean> warehouseMap = new HashMap<>();
            CSVReader warehouseCsvReader = new CSVReader(new FileReader(warehouseFile));
            CSVReader districtCsvReader = new CSVReader(new FileReader(districtFile));
            final List<String[]> warehouseInputData = warehouseCsvReader.readAll();
            for (String[] strings : warehouseInputData) {
                WarehouseBean warehouse = new WarehouseBean(Integer.parseInt(strings[0]), strings[1], strings[2], strings[3], strings[4],
                        strings[5], strings[6], Double.parseDouble(strings[7]), Double.parseDouble(strings[8]));
                warehouseMap.put(warehouse.getId(), warehouse);
            }
            final List<String[]> districtInputData = districtCsvReader.readAll();
            for (String[] strings : districtInputData) {
                String query = "INSERT INTO Next_Order (NO_W_ID, NO_D_ID, NO_D_NEXT_OID, NO_W_YTD, NO_D_YTD) VALUES(" + Integer.parseInt(strings[0]) + ", " + Integer.parseInt(strings[1]) + ", " + Integer.parseInt(strings[10]) +
                        ", " + warehouseMap.get(Integer.parseInt(strings[0])).getYtd() + ", " + Double.parseDouble(strings[9]) + ");";
                System.out.println(query);
                session.execute(query);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public Map<Integer, ItemBean> getItems() {
        Map<Integer, ItemBean> itemMap = new HashMap<>();
        try {
            InputStreamReader itemsFile = new InputStreamReader(classLoader.getResource("item.csv").openStream());
            CSVReader itemCsv = new CSVReader(itemsFile);
            final List<String[]> inputData = itemCsv.readAll();
            for (String[] strings : inputData) {
                ItemBean oldItem = new ItemBean(Integer.parseInt(strings[0]), strings[1], Double.parseDouble(strings[2]), Integer.parseInt(strings[3]), strings[4]);
                itemMap.put(Integer.parseInt(strings[0]), oldItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemMap;
    }

    public Map<OrderKey, Set<Item>> getOrderKeyItemsMap() {
        Map<Integer, ItemBean> itemMap = this.getItems();

        Map<OrderKey, Set<Item>> orderLineItemMap = new HashMap<>();
        try {
            InputStreamReader orderLineFile = new InputStreamReader(classLoader.getResource("order-line.csv").openStream());
            CSVReader orderLineCsv = new CSVReader(orderLineFile);
            final List<String[]> inputData = orderLineCsv.readAll();
            List<OrderLineItemBean> orderLineItemsList = inputData.stream().map(orderLineItemRow -> {
                Integer whId = Integer.parseInt(orderLineItemRow[0]);
                Integer dId = Integer.parseInt(orderLineItemRow[1]);
                Long ordId = Long.parseLong(orderLineItemRow[2]);
                OrderKey orderKey = new OrderKey(whId, dId, ordId);

                int olItemId = Integer.parseInt(orderLineItemRow[4]);
                String olItemName = itemMap.get(olItemId).getName();
                int olNumber = Integer.parseInt(orderLineItemRow[3]);
                int olSuppWarehouseId = Integer.parseInt(orderLineItemRow[7]);
                int olQuantity = Integer.parseInt(orderLineItemRow[8]);
                System.out.println(orderLineItemRow[5]);
                Date olDeliveryDate = new Date();
                if(orderLineItemRow[5] != null && !orderLineItemRow[5].equals("null")) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.SSS");
                    try {
                        olDeliveryDate = simpleDateFormat.parse(orderLineItemRow[5]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(olDeliveryDate);
                String olDistInfo = orderLineItemRow[9];
                double olAmount = Double.parseDouble(orderLineItemRow[6]);
                Item i = new Item(olItemId, olItemName, olNumber, olSuppWarehouseId, olQuantity, olDeliveryDate, olDistInfo, olAmount);

                return new OrderLineItemBean(orderKey, i);
            }).collect(Collectors.toList());

            Map<OrderKey, Set<Item>> orderKeyItemsMap = orderLineItemsList.stream().collect(Collectors.groupingBy(o -> o.getOrderKey(),
                    Collectors.mapping(o -> o.getItem(), Collectors.toSet())));

            return orderKeyItemsMap;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }



}
