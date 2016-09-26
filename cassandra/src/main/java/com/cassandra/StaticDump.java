import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

public class StaticDump {

	public static final String dir = "C:\\DD\\D8-data\\";
	public static final String cvsSplitBy = ",";

	public void readStaticData(Map<Integer,Warehouse> warehouse
			,Map<String,District> district,Map<String,Customer> customer,
			Map<String,Stock> stock)
	{
		//Warehouse
		String csvFile = dir + "warehouse.csv";
		ArrayList<String> lines = readCSV(csvFile);
		parseWarehouse(lines,warehouse);

		//District
		csvFile = dir + "district.csv";
		lines = readCSV(csvFile);
		parseDistrict(lines,district);


		//Customer
		csvFile = dir + "customer.csv";
		lines = readCSV(csvFile);
		parseCustomer(lines,customer);

		//Stock
		csvFile = dir + "stock.csv";
		lines = readCSV(csvFile);
		parseStock(lines,stock);

		//Item
		csvFile = dir + "item.csv";
		Map<String,Item> item = new HashMap<String,Item>();
		lines = readCSV(csvFile);
		parseItem(lines,item);

	}
	

	public static void parseItem(ArrayList<String> lines,Map<String,Item> items)
	{
		for(String line : lines)
		{
			String[] data = line.split(cvsSplitBy);
			String iid = data[0];
			items.put(iid, new Item(data[0], data[1], data[2], data[3], data[4]));
		}
	}


	public static void parseStock(ArrayList<String> lines,Map<String,Stock> stock)
	{
		for(String line : lines)
		{
			String[] data = line.split(cvsSplitBy);
			String wid_iid = data[0] + data[1];
			stock.put(wid_iid, new Stock(data[0],data[1],data[2],data[3],
					data[4],data[5],data[6],data[7],data[8],data[9], data[10],data[11]
							, data[12], data[13], data[14], data[15], data[16]));
		}
	}

	public static void parseCustomer(ArrayList<String> lines,Map<String,Customer> customer)
	{
		for(String line : lines)
		{
			String[] data = line.split(cvsSplitBy);
			String wid_did_cid = data[0] + data[1] + data[2];
			customer.put(wid_did_cid, new Customer(data[0],data[1],data[2],data[3],
					data[4],data[5],data[6],data[7],data[8],data[9], data[10],data[11]
							, data[12], data[13], data[14], data[15], data[16], 
							data[17], data[18], data[19], data[20]));
		}
	}

	public static void parseDistrict(ArrayList<String> lines,Map<String,District> district)
	{
		for(String line : lines)
		{
			String[] data = line.split(cvsSplitBy);
			String wid_did = data[0] + data[1];
			district.put(wid_did, new District(data[0],data[1],data[2],data[3],
					data[4],data[5],data[6],data[7],data[8],data[9], data[10]));
		}
	}


	public static void parseWarehouse(ArrayList<String> lines,Map<Integer,Warehouse> warehouse)
	{
		for(String line : lines)
		{
			String[] data = line.split(cvsSplitBy);
			int wid = Integer.parseInt(data[0]);
			warehouse.put(wid, new Warehouse(wid,data[1],data[2],data[3],
					data[4],data[5],data[6],data[7],data[8]));
		}
	}

	public static ArrayList<String> readCSV(String csvFile)
	{
		String line = "";
		ArrayList<String> lines = new ArrayList<String>();

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	public static void dumpWarehouseData(Map<Integer, Warehouse> warehouse, Session session)
	{
		PreparedStatement pStatement = session.prepareQuery(SQLConstants.WarehouseStatic);
		
		for (Entry<Integer, Warehouse> entry : warehouse.entrySet())
		{
			Warehouse w = entry.getValue();
			query + "values ("+w.getW_ID()+"";
		    System.out.println(entry.getKey() + "/" + entry.getValue());
		}
		
		
		session.execute("");
	}


	public void dumpStaticData(Map<Integer, Warehouse> warehouse, Map<String, District> district,
			Map<String, Customer> customer, Map<String, Stock> stock, Session session) {
		
		dumpWarehouseData(warehouse,session);
	}
}