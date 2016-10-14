package com.cassandra.transactions;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class TopBalanceTransaction {


	final String[] columns_customer = {"c_balance"};


	public void getTopBalance(Session session)
	{
		try
		{
			Statement topBalance = QueryBuilder.select(columns_customer).from("customer_data");
			ResultSet results= session.execute(topBalance);

			for(Row row : results)
			{
				double balance = row.getDouble("c_balance");
			}
			System.out.println("done");
			/*
			for(Row r:results.all()){
				//System.out.println("Customer name : "+r.getString("c_first")
				//+r.getString("c_middle")+r.getString("c_last"));
				System.out.println(r.getInt("c_id"));
				System.out.println(r.getDouble("c_balance"));
				//System.out.println("Warehouse name : "+r.getString("w_name"));
				//System.out.println("District name : "+r.getString("d_name"));
			}*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}