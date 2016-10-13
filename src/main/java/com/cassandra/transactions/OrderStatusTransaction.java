package com.cassandra.transactions;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cassandra.beans.Item;
import com.cassandra.utilities.Lucene;
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

	public void readOrderStatus(int w_id, int d_id, int c_id, Session session, Lucene index)
	{
		try
		{
			System.out.println(w_id + " "+d_id + " "+c_id);
			Statement orderStatus = QueryBuilder.select().all().from("new_order_transaction")
					.where(QueryBuilder.eq("o_w_id",w_id))
					.and(QueryBuilder.eq("o_d_id",d_id))
					.and(QueryBuilder.eq("o_c_id",c_id))
					.limit(1);
			ResultSet results= session.execute(orderStatus);
			String[] customerData = index.search(w_id+""+d_id+""+c_id, "customer-id", "customer-csv").get(0).split(",");
			Statement CustomerData = QueryBuilder.select().all().from("customer_data")
					.where(QueryBuilder.eq("c_w_id",w_id))
					.and(QueryBuilder.eq("c_d_id",d_id))
					.and(QueryBuilder.eq("c_id",c_id));
			Row customerRow = session.execute(CustomerData).one();
			for(Row r:results.all()){
				System.out.println("Customer name : "+customerData[3]
						+" "+customerData[4]+" "+customerData[5]);
				System.out.println(customerRow.getDouble("c_balance"));
				Set<Item> orders = r.getSet("o_items",Item.class);
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