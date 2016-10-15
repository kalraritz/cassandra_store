package com.cassandra.transactions;

import com.cassandra.TransactionDriver;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


class CustomerBalance{
	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public double getC_balance() {
		return c_balance;
	}

	public void setC_balance(double c_balance) {
		this.c_balance = c_balance;
	}

	private int cid;
	private double c_balance;


	public CustomerBalance(int cid, double c_balance) {
		this.cid = cid;
		this.c_balance = c_balance;
	}
}

public class TopBalanceTransaction {


	final String[] columns_customer = {"c_id","c_balance"};


	public void getTopBalance(Session session)
	{
		try
		{
			Statement topBalance = QueryBuilder.select(columns_customer).from("customer_data");
			ResultSet results= session.execute(topBalance);

			ArrayList<CustomerBalance> objs = new ArrayList<CustomerBalance>();
			for(Row row : results)
			{
				objs.add(new CustomerBalance(row.getInt("c_id"),row.getDouble("c_balance")));
			}
			Collections.sort(objs,new CustomerComparator());

			int itr = 0;
			PrintWriter pw = TransactionDriver.pw;
			for(CustomerBalance c : objs)
			{
				itr++;
				pw.write("Top Balance Transaction--------"+"\n");
				pw.write("Customer Id : "+c.getCid()+"\n");
				pw.write("Customer Balance : "+c.getC_balance()+"\n");
				if(itr == 10)
					break;
			}
			pw.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}


class CustomerComparator implements Comparator<CustomerBalance> {
	@Override
	public int compare(CustomerBalance p1, CustomerBalance p2) {
		if(p1.getC_balance() == p2.getC_balance())
			return 0;
		return p1.getC_balance() > p2.getC_balance() ? -1:1;
	}
}