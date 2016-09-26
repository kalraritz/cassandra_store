package com.cassandra;

import com.cassandra.beans.*;
import com.cassandra.models.Order;
import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.opencsv.CSVReader;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by manisha on 24/09/2016.
 */
public class DumpOrderData {

    private final ClassLoader classLoader;
    private Integer warehouseId;
    private Integer districtId;
    private Long orderId;
    private Long customerId;
    private Integer carrierId;
    private Integer orderLineCount;
    private Integer allLocal;
    private Timestamp entryDate;

    public DumpOrderData() {
        this.classLoader = getClass().getClassLoader();
    }

    public void dumpNewOrderTransactionData(Map<OrderKey, Set<Item>> itemsMap) {
        try {
            Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
            Session session = cluster.connect("orders");
            System.out.println(session.getCluster().getClusterName());


            File file = new File(classLoader.getResource("order-test.csv").getFile());
            CSVReader csvReader = new CSVReader(new FileReader(file));
            final List<String[]> inputData = csvReader.readAll();
            for (String[] strings : inputData) {
                warehouseId = Integer.parseInt(strings[0]);
                districtId = Integer.parseInt(strings[1]);
                orderId = Long.parseLong(strings[2]);
                customerId = Long.parseLong(strings[3]);
                if (strings[4].equals("null"))
                    carrierId = 324;
                else
                    carrierId = Integer.parseInt(strings[4]);
                orderLineCount = Integer.parseInt(strings[5]);
                allLocal = Integer.parseInt(strings[6]);
                entryDate = Timestamp.valueOf(strings[7]);
                OrderKey orderKey = new OrderKey(warehouseId, districtId, orderId);
                Set<Item> sample = new HashSet<>();
                String str = "{{OL_I_ID: 1, OL_I_NAME: 'ggfd', OL_NUMBER:1, OL_S_W_ID: 4, OL_QUANTITY:4, OL_DELIVERY_D: '2016-08-15 16:00:40.433', OL_DIST_INFO: 'erew', OL_AMOUNT: 4.4}}";
//                PreparedStatement statement = session.prepare(
//                        "INSERT INTO New_Order_Transaction" + "(O_W_ID, O_D_ID, O_ID, O_ENTRY_D, O_CARRIER_ID, O_C_ID, O_OL_CNT, O_ALL_LOCAL," +
//                                "O_ITEMS)"
//                                + "VALUES (?,?,?,?,?,?,?,?,?);");
//                BoundStatement boundStatement = new BoundStatement(statement);
//                session.execute(boundStatement.bind(warehouseId, districtId, orderId, entryDate, carrierId, customerId, orderLineCount,
//                        allLocal, sample));
                String itemSet = null;
                Set<Item> items = itemsMap.get(orderKey);
                if (items != null) {
                    Iterator<Item> it = items.iterator();
                    String frozenItem = "";
                    while (it.hasNext()) {
                        Item item = it.next();
                        frozenItem = frozenItem + "(" + item.getOlItemId() + ", '" + item.getOlItemName() + "', " + item.getOlNumber() + ", " + item.getOlSuppWarehouseId() + ", " + item.getOlQuantity()
                                + ", '" + item.getOlDeliveryDate() + "', '" + item.getOlDistInfo() + "', " + item.getOlAmount() + ")" + ",";
                    }
                    frozenItem = frozenItem.substring(0, frozenItem.length() - 1);

                    itemSet = "{" + frozenItem + "}";
                }
                String query = "INSERT INTO New_Order_Transaction (O_W_ID, O_D_ID, O_ID, O_ENTRY_D, O_CARRIER_ID, O_C_ID, O_OL_CNT, O_ALL_LOCAL, O_ITEMS) " +
                        "VALUES(" + warehouseId + ", " + districtId + ", " + orderId + ", '" + strings[7] + "', " + carrierId + ", " + customerId + ", " + orderLineCount + ", " + allLocal + "," + itemSet + ");";
                System.out.println(query);
                session.execute(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public Map<OrderKey, Set<Item>> getOrderLineItems(Map<Integer, OldItem> itemMap) {

        Map<OrderKey, Set<Item>> orderLineItemMap = new HashMap<>();
        try {
            File file = new File(classLoader.getResource("order-line-test.csv").getFile());
            CSVReader csvReader = new CSVReader(new FileReader(file));
            final List<String[]> inputData = csvReader.readAll();

            inputData.stream()
                    .collect(Collectors.toMap(row -> {
                        Integer whId = Integer.parseInt(row[0]);
                        Integer dId = Integer.parseInt(row[1]);
                        Long ordId = Long.parseLong(row[2]);
                        return new OrderKey(whId, dId, ordId);
                    }, row -> {
                        int olItemId = ;
                        String olItemName;
                        int olNumber;
                        int olSuppWarehouseId;
                        int olQuantity;
                        Timestamp olDeliveryDate;
                        String olDistInfo;
                        double olAmount;

                        return Item()
                    }));

            for (String[] strings : inputData) {
                Set<Item> items = new HashSet<>();
                OrderKey orderKey = new OrderKey(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Long.parseLong(strings[2]));
                Item item = new Item(Integer.parseInt(strings[4]), itemMap.get(Integer.parseInt(strings[4])).getName(), Integer.parseInt(strings[3]),
                        Integer.parseInt(strings[7]), Integer.parseInt(strings[8]), Timestamp.valueOf(strings[5]), strings[9], Double.parseDouble(strings[6]));
                if (orderLineItemMap.size() != 0) {
                    Set<Item> existingItems = orderLineItemMap.get(orderKey);
                    if (existingItems != null) {
                        existingItems.add(item);
                        orderLineItemMap.put(orderKey, existingItems);
                    } else {
                        items.add(item);
                        orderLineItemMap.put(orderKey, items);
                    }

                } else {
                    items.add(item);
                    orderLineItemMap.put(orderKey, items);
                }
            }
            Set<OrderKey> keys = orderLineItemMap.keySet();
            Iterator<OrderKey> it = keys.iterator();
            while (it.hasNext()) {
                OrderKey orderKey = it.next();
                System.out.println(orderKey.getDistrictId() + " " + orderKey.getWarehouseId() + " " + orderKey.getOrderId() + "******" + orderLineItemMap.get(orderKey));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderLineItemMap;
    }

    public void test() {
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        Session session = cluster.connect("orders");
        MappingManager manager = new MappingManager(session);
        Mapper<Order> mapper = manager.mapper(Order.class);
        Set<Item> set = new HashSet<>();
//        Order order = new Order(1,1,new Long(3), "2016-08-15 16:00:40.433",1,new Long(1),1,11, null);
//        mapper.save(order);
    }


}
