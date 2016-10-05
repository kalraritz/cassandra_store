//package com.cassandra;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import com.cassandra.models.*;
//import com.datastax.driver.core.Session;
//import com.datastax.driver.mapping.Mapper;
//import com.datastax.driver.mapping.MappingManager;
//
//public class CSVToTable {
//
//    public static final String dir = "C:\\DD\\D8-data\\";
//    public static final String cvsSplitBy = ",";
//    static Map<Integer,RelWarehouse> warehouseMap = new HashMap<Integer,RelWarehouse>();
//    static Map<String,RelDistrict> districtMap = new HashMap<String,RelDistrict>();
//    static Map<String,RelCustomer> customerMap = new HashMap<String,RelCustomer>();
//    static Map<String,RelStock> stockMap = new HashMap<String,RelStock>();
//    static Map<Integer,RelItem> itemMap = new HashMap<Integer,RelItem>();
//
//    private static Session session;
//
//
//    public static void main(String args[])
//    {
//        readStaticData();
//        CassandraInit c = new CassandraInit();
//        session =  c.cassandraInit();
//        dumpWarehouseData();
//    }
//
//    public static void readStaticData()
//    {
//        //Warehouse
//        String csvFile = dir + "warehouse.csv";
//        ArrayList<String> lines = readCSV(csvFile);
//        parseWarehouse(lines,warehouseMap);
//
//        //District
//        csvFile = dir + "district.csv";
//        lines = readCSV(csvFile);
//        parseDistrict(lines,districtMap);
//
//
//        //Customer
//        csvFile = dir + "customer.csv";
//        lines = readCSV(csvFile);
//        parseCustomer(lines,customerMap);
//
//        //Stock
//        csvFile = dir + "stock.csv";
//        lines = readCSV(csvFile);
//        parseStock(lines,stockMap);
//
//        //Item
//        csvFile = dir + "item.csv";
//        Map<String,RelItem> item = new HashMap<String,RelItem>();
//        lines = readCSV(csvFile);
//        parseItem(lines,item);
//
//    }
//
//
//    public static void parseItem(ArrayList<String> lines,Map<String,RelItem> items)
//    {
//        for(String line : lines)
//        {
//            String[] data = line.split(cvsSplitBy);
//            String iid = data[0];
//            //items.put(iid, new Item(data[0], data[1], data[2], data[3], data[4]));
//        }
//    }
//
//
//    public static void parseStock(ArrayList<String> lines,Map<String,RelStock> stock)
//    {
//        for(String line : lines)
//        {
//            String[] data = line.split(cvsSplitBy);
//            String wid_iid = data[0] + data[1];
//            stock.put(wid_iid, new RelStock(Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2])
//                    ,Double.parseDouble(data[3]), Integer.parseInt(data[4]),Integer.parseInt(data[5]),data[6],data[7],
//                    data[8],data[9], data[10],data[11], data[12], data[13], data[14], data[15], data[16]));
//        }
//    }
//
//    public static void parseCustomer(ArrayList<String> lines,Map<String,RelCustomer> customer)
//    {
//        for(String line : lines)
//        {
//            String[] data = line.split(cvsSplitBy);
//            String wid_did_cid = data[0] + data[1] + data[2];
//            customer.put(wid_did_cid, new RelCustomer(Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2]),
//                    data[3], data[4],data[5],data[6],data[7],data[8],data[9], data[10],data[11]
//                    , new Timestamp(Long.parseLong(data[12])), data[13],Double.parseDouble(data[14]),Double.parseDouble(data[15])
//                    , Double.parseDouble(data[16]),Float.parseFloat(data[17]),Integer.parseInt(data[18])
//                    ,Integer.parseInt(data[19]), data[20]));
//        }
//    }
//
//    public static void parseDistrict(ArrayList<String> lines,Map<String,RelDistrict> district)
//    {
//        for(String line : lines)
//        {
//            String[] data = line.split(cvsSplitBy);
//            String wid_did = data[0] + data[1];
//            district.put(wid_did, new RelDistrict(Integer.parseInt(data[0]),Integer.parseInt(data[1]),data[2],data[3],
//                    data[4],data[5],data[6],data[7],Double.parseDouble(data[8]),Double.parseDouble(data[9]),Integer.parseInt(data[10])));
//        }
//    }
//
//
//    public static void parseWarehouse(ArrayList<String> lines,Map<Integer,RelWarehouse> warehouse)
//    {
//        for(String line : lines)
//        {
//            String[] data = line.split(cvsSplitBy);
//            int wid = Integer.parseInt(data[0]);
//            warehouse.put(wid, new RelWarehouse(wid,data[1],data[2],data[3],
//                    data[4],data[5],data[6],Double.parseDouble(data[7]),Double.parseDouble(data[8])));
//        }
//    }
//
//    public static ArrayList<String> readCSV(String csvFile)
//    {
//        String line = "";
//        ArrayList<String> lines = new ArrayList<String>();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
//            while ((line = br.readLine()) != null) {
//                lines.add(line);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return lines;
//    }
//
//
//    public static void dumpWarehouseData()
//    {
//        for (Entry<Integer, RelWarehouse> entry : warehouseMap.entrySet()) {
//            RelWarehouse w = entry.getValue();
//            MappingManager manager = new MappingManager(session);
//            Mapper<RelWarehouse> mapper = manager.mapper(RelWarehouse.class);
//            RelWarehouse relWarehouse = new RelWarehouse();
//            relWarehouse.setId(w.getId());
//            String query = "INSERT INTO sql_warehouse (W_ID, W_NAME,W_STREET_1,W_STREET_2,W_CITY,W_STATE,W_ZIP,W_TAX) VALUES("
//                    + w.getId() + ", " + w.getName() + ", " + w.getStreet1() +
//                    ", " + w.getStreet2() + ", " + w.getCity() + ", " + w.getState() + ", " + w.getZip() +
//                    w.getTax() + ");";
//            System.out.println(query);
//            session.execute(query);
//        }
//    }
//
//    public static void dumpDistrictData()
//    {
//        for (Entry<String, RelDistrict> entry : districtMap.entrySet()) {
//            RelDistrict d = entry.getValue();
//            String query = "INSERT INTO sql_district (D_W_ID,D_ID, D_NAME,D_STREET,D_STREET_2,D_CITY,D_STATE,D_ZIP,D_TAX) VALUES("
//                    + d.getWarehouseId() + ", " + d.getId() + ", " + d.getName() + ", " + d.
//            () +
//                    ", " + d.getD_STREET_2() + ", " + d.getD_CITY() + ", " + d.getD_STATE() + ", " + d.getD_ZIP() +
//                    d.getD_TAX() + ");";
//            System.out.println(query);
//            session.execute(query);
//        }
//    }
//
//    public static void dumpNextOrderData()
//    {
//        for (Entry<Integer, Warehouse> w_entry : warehouseMap.entrySet()) {
//            int w_id = w_entry.getKey();
//            double w_ytd = w_entry.getValue().getW_YTD();
//            for (Entry<String, District> d_entry : districtMap.entrySet()) {
//                District dist = d_entry.getValue();
//                String query = "INSERT INTO sql_next_order (NO_W_ID,NO_D_ID, NO_D_NEXT_OID,NO_W_YTD,NO_D_YTD) VALUES("
//                        + w_id + ", " + dist.getD_ID() + ", " + dist.getD_NEXT_O_ID()
//                        + ", " + w_ytd + ", " + dist.getD_YTD() + ");";
//                System.out.println(query);
//                session.execute(query);
//            }
//
//        }
//    }
//
//    public static void dumpStockandItemData()
//    {
//        for (Entry<String, RelStock> entry : stockMap.entrySet()) {
//            //Stock static tables
//            RelStock stock = entry.getValue();
//            RelItem item =itemMap.get(stock.getS_ID());
//            String query = "INSERT INTO sql_stock (S_W_ID, S_ID,S_DIST_01,S_DIST_02,S_DIST_03,S_DIST_04,S_DIST_05" +
//                    ",S_DIST_06,S_DIST_07,S_DIST_08,S_DIST_09,S_DIST_10,S_DATA,S_I_NAME,S_I_IM_D,S_I_PRICE,S_I_DATA) VALUES("
//                    + stock.getS_W_ID()  + ", " + stock.getS_ID() + ", " + stock.getS_DIST_01() +", " + stock.getS_DIST_02() +
//                    ", " + stock.getS_DIST_03() +", " + stock.getS_DIST_04() +", " + stock.getS_DIST_05() +", "
//                    + stock.getS_DIST_06() + ", " + stock.getS_DIST_07() +", " + stock.getS_DIST_08() +", "
//                    + stock.getS_DIST_09() +", " + stock.getS_DIST_10() + ", " + stock.getS_DATA() +", "
//                    + item.getI_NAME() +", " + item.getI_IM_ID() +", " + item.getI_PRICE() + item.getI_DATA() + ");";
//            System.out.println(query);
//            session.execute(query);
//
//            //Stock transaction table
//            query = "INSERT INTO stock_level_transaction (S_W_ID, S_ID,S_QUANTITY,S_YTD,S_ORDER,S_REMOTE_CNT VALUES("
//                    + stock.getS_W_ID()  + ", " + stock.getS_ID() + ", " + stock.getS_QUANTITY()
//                    + ", " + stock.getS_YTD() + ", " + stock.getS_ORDER_CNT() + ", " + stock.getS_REMOTE_CNT() + ");";
//            System.out.println(query);
//            session.execute(query);
//        }
//    }
//
//    public static void dumpCustomerData()
//    {
//        for (Entry<String, Customer> entry : customerMap.entrySet()) {
//            Customer cust = entry.getValue();
//
//            //Static customer table
//            String query = "INSERT INTO sql_customer (C_W_ID,C_D_ID,C_ID,C_FIRST,C_MIDDLE,C_LAST,C_STREET_1" +
//                            ",C_STREET_2,C_CITY,C_STATE,C_ZIP,C_PHONE,C_SINCE,C_CREDIT,C_CREDIT_LIM,C_DISCOUNT,C_DATA) VALUES("
//                    + cust.getC_W_ID() + ", " + cust.getC_D_ID() + ", " + cust.getC_ID() + ", " + cust.getC_FIRST() +
//                    ", " + cust.getC_MIDDLE() +", " + cust.getC_LAST() +", " + cust.getC_STREET_1() +", " + cust.getC_STREET_2() +
//                    ", " + cust.getC_CITY() +", " + cust.getC_STATE() +", " + cust.getC_ZIP() +", " + cust.getC_PHONE() +
//                    ", " + cust.getC_D_ID() +", " + cust.getC_D_ID() +", " + cust.getC_D_ID() +", " + cust.getC_D_ID() +
//                    ", " + cust.getC_SINCE() +", " + cust.getC_CREDIT() +", " + cust.getC_CREDIT_LIM() +", " + cust.getC_DISCOUNT() +
//                    ", " + cust.getC_DATA() + ");";
//            System.out.println(query);
//            session.execute(query);
//
//            //Customer transaction table
//            String c_w_id = warehouseMap.get(cust.getC_W_ID()).getW_NAME();
//            String c_d_id = districtMap.get(cust.getC_D_ID()).getD_NAME();
//            query = "INSERT INTO customer_data(C_W_ID,C_D_ID,C_ID,C_BALANCE,C_W_NAME,C_D_NAME,C_DELIVERY,C_YTD,C_PAYMENT) VALUES("
//                    + cust.getC_W_ID() + ", " + cust.getC_D_ID() + ", " + cust.getC_ID()  + ", " + cust.getC_BALANCE()  +
//                    ", " + c_w_id  + ", " + c_d_id  + ", " + cust.getC_DELIVERY_CNT()  + ", "
//                    + cust.getC_YTD_PAYMENT()  + ", " + cust.getC_PAYMENT_CNT() + ");";
//            System.out.println(query);
//            session.execute(query);
//        }
//    }
//}