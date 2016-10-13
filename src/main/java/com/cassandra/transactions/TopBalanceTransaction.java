package com.cassandra.transactions;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class TopBalanceTransaction {


	public void getTopBalance(Session session)
	{
		try
		{
			Statement topBalance = QueryBuilder.select().all().from("customer_data")
					.limit(10);
			ResultSet results= session.execute(topBalance);

			for(Row r:results.all()){
				//System.out.println("Customer name : "+r.getString("c_first")
				//+r.getString("c_middle")+r.getString("c_last"));
				System.out.println(r.getInt("c_id"));
				System.out.println(r.getDouble("c_balance"));
				//System.out.println("Warehouse name : "+r.getString("w_name"));
				//System.out.println("District name : "+r.getString("d_name"));
			}
		}
		catch(Exception e)
		{}
	}
}