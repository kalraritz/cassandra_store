//package com.cassandra.transactions;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//
//import com.cassandra.beans.Item;
//import com.datastax.driver.core.Cluster;
//import com.datastax.driver.core.ResultSet;
//import com.datastax.driver.core.Row;
//import com.datastax.driver.core.Session;
//import com.datastax.driver.core.Statement;
//import com.datastax.driver.core.querybuilder.QueryBuilder;
//
//public class StockLevelTransaction {
//
//	final String[] columns_next_order = {"d_next_o_id"};
//	final String[] columns_stock = {"s_i_id","s_quantity"};
//
//
//	public void checkStockThreshold(int w_id,int d_id,int threshold,int num_last_orders,Session session)
//	{
//		try
//		{
//			//Get the latest d_next_oid
//			Statement getDNextOID = QueryBuilder.select(columns_next_order).from("next_order")
//					.where(QueryBuilder.eq("o_w_id",w_id));
//			ResultSet results= session.execute(getDNextOID);
//			int d_next_oid = results.one().getInt("d_next_o_id");
//			int start_index = d_next_oid - num_last_orders;
//
//			//Get the last L orders
//			Statement getLastLOrders = QueryBuilder.select().all().from("new_order")
//					.where(QueryBuilder.gte("o_id",start_index))
//					.and(QueryBuilder.lte("o_id",d_next_oid));
//			results= session.execute(getLastLOrders);
//
//			Set<Integer> itemids = new HashSet<Integer>();
//			Map<Integer,String> itemName = new HashMap<Integer,String>();
//
//			//Add itemids to a list to fetch the quantity for each item
//			for(Row r:results.all()){
//				Set<Item> orders = r.getSet("ol_i_id",Item.class);
//				Iterator<Item> order_items = orders.iterator();
//				while(order_items.hasNext())
//				{
//					Item item = order_items.next();
//					itemids.add(item.getOlItemId());
//					itemName.put(item.getOlItemId(), item.getOlItemName());
//				}
//			}
//			Statement itemQuantity = QueryBuilder.select(columns_stock).from("stock")
//					.where(QueryBuilder.eq("w_id",w_id))
//					.and(QueryBuilder.in("s_id",itemids.toArray()));
//			results = session.execute(itemQuantity);
//
//			for(Row r:results.all()){
//				int item_quantity = r.getInt("s_quantity");
//				if(item_quantity < threshold)
//				{
//					int item_id = r.getInt("s_i_id");
//					System.out.println("Item id : "+ item_id);
//					System.out.println("Item name : "+itemName.get(item_id));
//				}
//			}
//		}
//		catch(Exception e)
//		{}
//	}
//}
