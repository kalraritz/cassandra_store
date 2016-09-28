package com.cassandra;

import com.cassandra.models.Item;
import com.cassandra.beans.OrderKey;
import com.cassandra.models.ItemOrders;
import com.datastax.driver.core.*;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;


public class Test {


    private final ClassLoader classLoader;

    public Test() {
        this.classLoader = getClass().getClassLoader();
    }
    public void dumpNewOrderTransactionDataw(Map<OrderKey, Set<ItemOrders>> itemsMap) {

        Integer warehouseId;
        Integer districtId;
        Long orderId;
        Long customerId;
        Integer carrierId;
        Integer orderLineCount;
        Integer allLocal;
        Date entryDate;


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
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.SSS");
                entryDate = simpleDateFormat.parse(strings[7]);
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
                Set<ItemOrders> items = itemsMap.get(orderKey);
                if (items != null) {
                    Iterator<ItemOrders> it = items.iterator();
                    String frozenItem = "";
                    while (it.hasNext()) {
                        ItemOrders item = it.next();
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

    public void test() {
        //
//            for (String[] strings : inputData) {
//                Set<Item> items = new HashSet<>();
//                OrderKey orderKey = new OrderKey(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Long.parseLong(strings[2]));
//                Item item = new Item(Integer.parseInt(strings[4]), itemMap.get(Integer.parseInt(strings[4])).getName(), Integer.parseInt(strings[3]),
//                        Integer.parseInt(strings[7]), Integer.parseInt(strings[8]), Timestamp.valueOf(strings[5]), strings[9], Double.parseDouble(strings[6]));
//                if (orderLineItemMap.size() != 0) {
//                    Set<Item> existingItems = orderLineItemMap.get(orderKey);
//                    if (existingItems != null) {
//                        existingItems.add(item);
//                        orderLineItemMap.put(orderKey, existingItems);
//                    } else {
//                        items.add(item);
//                        orderLineItemMap.put(orderKey, items);
//                    }
//
//                } else {
//                    items.add(item);
//                    orderLineItemMap.put(orderKey, items);
//                }
//            }
//            Set<OrderKey> keys = orderLineItemMap.keySet();
//            Iterator<OrderKey> it = keys.iterator();
//            while (it.hasNext()) {
//                OrderKey orderKey = it.next();
//                System.out.println(orderKey.getDistrictId() + " " + orderKey.getWarehouseId() + " " + orderKey.getOrderId() + "******" + orderLineItemMap.get(orderKey));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return orderLineItemMap;
    }

    public static void main(String[] args) {

    }
}
