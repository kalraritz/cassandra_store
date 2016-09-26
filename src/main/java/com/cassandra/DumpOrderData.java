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
            cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
            session = cluster.connect("orders");
            System.out.println(session.getCluster().getClusterName());

            MappingManager manager = new MappingManager(session);
            Mapper<Order> mapper = manager.mapper(Order.class);
            File orderFile = new File(classLoader.getResource("order-test.csv").getFile());
            CSVReader orderCsvReader = new CSVReader(new FileReader(orderFile));
            final List<String[]> inputOrderData = orderCsvReader.readAll();
            for(String[] strings: inputOrderData) {
                Date entryDate = new Date();
                int warehouseId = Integer.parseInt(strings[0]);
                int districtId = Integer.parseInt(strings[1]);
//                Integer carrierId;
//                if(strings[4].equals("null") || strings[4].equals(null))
//                    carrierId = null;
//                else
//                    carrierId = Integer.parseInt(strings[4]);
                Long orderId = Long.parseLong(strings[2]);
                Set<OrderKey> set = orderKeyItemsMap.keySet();
                Iterator it = set.iterator();
                while(it.hasNext()){
                    OrderKey key = (OrderKey) it.next();
                    System.out.println(key.toString());
                    System.out.println(orderKeyItemsMap.get((OrderKey) it.next()));
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
                        null, Long.parseLong(strings[3]), Integer.parseInt(strings[5]), Integer.parseInt(strings[6]),
                        orderKeyItemsMap.get(orderKey)
                        );
                mapper.save(order);

            }

//            for (Map.Entry<OrderKey, Set<Item>> orderKeySetEntry : orderKeyItemsMap.entrySet()) {
//                OrderKey orderKey = orderKeySetEntry.getKey();
//                Set<Item> itemsSet = orderKeySetEntry.getValue();
//                Order entity = new Order(orderKey.getWarehouseId(),
//                        orderKey.getDistrictId(),
//                        orderKey.getOrderId(),
//                        new Date(),
//                        null,
//                        new Long(0),
//                        0,
//                        0,
//                        itemsSet
//                );
//                mapper.save(entity);
//            }


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
            Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
            Session session = cluster.connect("orders");
            File warehouseFile = new File(classLoader.getResource("warehouse-test.csv").getFile());
            File districtFile = new File("district-test.csv");
            Map<Integer, Warehouse> warehouseMap = new HashMap<>();
            CSVReader warehouseCsvReader = new CSVReader(new FileReader(warehouseFile));
            CSVReader districtCsvReader = new CSVReader(new FileReader(districtFile));
            final List<String[]> warehouseInputData = warehouseCsvReader.readAll();
            for (String[] strings : warehouseInputData) {
                Warehouse warehouse = new Warehouse(Integer.parseInt(strings[0]), strings[1], strings[2], strings[3], strings[4],
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

    public Map<Integer, OldItem> getItems() {
        Map<Integer, OldItem> itemMap = new HashMap<>();
        try {
            File file = new File(classLoader.getResource("item-test.csv").getFile());
            CSVReader csvReader = new CSVReader(new FileReader(file));
            final List<String[]> inputData = csvReader.readAll();
            for (String[] strings : inputData) {
                OldItem oldItem = new OldItem(Integer.parseInt(strings[0]), strings[1], Double.parseDouble(strings[2]), Integer.parseInt(strings[3]), strings[4]);
                itemMap.put(Integer.parseInt(strings[0]), oldItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemMap;
    }

    public Map<OrderKey, Set<Item>> getOrderKeyItemsMap() {
        Map<Integer, OldItem> itemMap = this.getItems();

        Map<OrderKey, Set<Item>> orderLineItemMap = new HashMap<>();
        try {
            File file = new File(classLoader.getResource("order-line-test.csv").getFile());
            CSVReader csvReader = new CSVReader(new FileReader(file));
            final List<String[]> inputData = csvReader.readAll();


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

//            Map<OrderKey, List<OrderLineItemBean>> collect = orderLineItemsList.stream().collect(Collectors.groupingBy(o -> o.getOrderKey()));

//            List<NewOrderTransactionBean> newOrderTransactionBeanList = orderKeyItemsMap.entrySet().stream()
//                    .map(e -> new NewOrderTransactionBean(e.getKey(), e.getValue()))
//                    .collect(Collectors.toList());
//
//            return newOrderTransactionBeanList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }



}
