package com.cassandra.transactions;

import java.io.PrintWriter;
import java.util.*;

import com.cassandra.TransactionDriver;
import com.cassandra.beans.Item;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class PopularItemTransaction {

	final String[] columns_next_order = {"no_d_next_o_id"};
	final String[] columns_stock = {"s_i_id","s_quantity"};

	public void checkPopularItem(int w_id,int d_id,int num_last_orders,Session session)
	{
		try
		{
			//Get the latest d_next_oid
			Statement getDNextOID = QueryBuilder.select(columns_next_order).from("next_order")
					.where(QueryBuilder.eq("no_w_id",w_id))
					.and(QueryBuilder.eq("no_d_id",d_id));
			ResultSet results= session.execute(getDNextOID);
			int d_next_oid = results.one().getInt("no_d_next_o_id");
			int start_index = d_next_oid - num_last_orders;


			//Get the last L orders
			Statement getLastLOrders = QueryBuilder.select().all().from("new_order_transaction")
					.where(QueryBuilder.eq("o_w_id",w_id))
					.and(QueryBuilder.eq("o_d_id",d_id))
					.and(QueryBuilder.gte("o_id",start_index))
					.and(QueryBuilder.lte("o_id",d_next_oid));
			results= session.execute(getLastLOrders);
			Set<Integer> itemids = new HashSet<Integer>();
			Map<Integer,List<Integer>> orderItemsMapping = new HashMap<Integer,List<Integer>>();


			//Add itemids to a list to fetch the quantity for each item
			//Create a map of order-items.
			for(Row r:results.all()){
				Set<Item> orders = r.getSet("o_items",Item.class);
				int order_id = r.getInt("o_id");
				Iterator<Item> order_items = orders.iterator();
				while(order_items.hasNext())
				{
					Item item = order_items.next();
					itemids.add(item.getOlItemId());
					List<Integer> items = null;
					if(orderItemsMapping.get(order_id) == null)
					{
						items = new ArrayList<Integer>();
					}
					else
					{
						items = orderItemsMapping.get(order_id);
					}
					items.add(item.getOlItemId());
					orderItemsMapping.put(order_id,items);
				}
			}

			//Get quantity for each item and store in item-quantity map
			Statement itemPrices = QueryBuilder.select(columns_stock).from("stock_level_transaction")
					.where(QueryBuilder.eq("s_w_id",w_id))
					.and(QueryBuilder.in("s_i_id",itemids.toArray()));
			results = session.execute(itemPrices);
			Map<Integer,Double> orderItemQuantity = new HashMap<Integer,Double>();
			for(Row r:results.all()){
				double item_quantity = r.getDouble("s_quantity");
				int item_id = r.getInt("s_i_id");
				orderItemQuantity.put(item_id,item_quantity);
			}

			//Iterate over order-item map and get max item id for each order
			PrintWriter pw = TransactionDriver.pw;
			pw.write("Popular Item Transaction--------"+"\n");
			for (Map.Entry<Integer, List<Integer>> entry : orderItemsMapping.entrySet())
			{

				pw.write("Order Id : "+entry.getKey()
						+"\n"+"Item Id : "+getMaxQuantity(entry.getValue(),orderItemQuantity)+"\n");
				//System.out.println("Order Id : "+entry.getKey());
				//System.out.println("Item Id : "+getMaxQuantity(entry.getValue(),orderItemQuantity));
			}
			pw.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//Returns max item id
	private int getMaxQuantity(List<Integer> value, Map<Integer, Double> orderItemQuantity) {

		Iterator<Integer> it = value.iterator();
		double max = Integer.MIN_VALUE;
		int max_item_id = 0;
		while(it.hasNext())
		{
			int item_id = it.next();
			double item_quantity = orderItemQuantity.get(item_id);
			if(item_quantity > max)
			{
				max = item_quantity;
				max_item_id = item_id;
			}
		}
		return max_item_id;
	}
}
