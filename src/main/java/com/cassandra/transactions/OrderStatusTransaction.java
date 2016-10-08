package com.cassandra.transactions;

import java.util.Iterator;
import java.util.Set;

import com.cassandra.ItemCodec;
import com.cassandra.beans.Item;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.CodecRegistry;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class OrderStatusTransaction {
	
	public void readOrderStatus(int w_id,int d_id,int c_id,Session session)
	{
		try
		{
			Statement orderStatus = QueryBuilder.select().all().from("order_status_transaction")
					.where(QueryBuilder.eq("o_w_id",w_id))
			        .and(QueryBuilder.eq("o_d_id",d_id)) 
			        .and(QueryBuilder.eq("o_c_id",c_id))
					.limit(1);
			ResultSet results= session.execute(orderStatus);
			
			for(Row r:results.all()){
				System.out.println("Customer name : "+r.getString("c_first")
				+r.getString("c_middle")+r.getString("c_last"));
			    System.out.println(r.getDouble("c_balance"));
			    Set<Item> orders = r.getSet("ol_i_id",Item.class);
			    Iterator<Item> order_items = orders.iterator();
			    while(order_items.hasNext())
			    {
			    	Item item = order_items.next();
			    	System.out.println("Item number: "+item.getOlItemId());
			    	System.out.println("Warehouse number: "+item.getOlSuppWarehouseId());
			    	System.out.println("Quantity number: "+item.getOlQuantity());
			    	System.out.println("Total price: "+item.getOlAmount());
			    	System.out.println("Date and time of delivery: "+item.getOlDeliveryDate());
			    }
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
