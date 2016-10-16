package com.cassandra.transactions;

import com.cassandra.TransactionDriver;
import com.cassandra.utilities.Lucene;
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

	public int getWid() {
		return wid;
	}

	public void setWid(int wid) {
		this.wid = wid;
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	private int wid;
	private int did;
	private int cid;
	private double c_balance;


	public CustomerBalance(int wid,int did,int cid, double c_balance) {
		this.wid = wid;
		this.did = did;
		this.cid = cid;
		this.c_balance = c_balance;
	}
}

public class TopBalanceTransaction {


	final String[] columns_customer = {"c_w_id","c_d_id","c_id","c_balance"};


	public void getTopBalance(Session session, PrintWriter printWriter, Lucene index)
	{
		try
		{
			Statement topBalance = QueryBuilder.select(columns_customer).from("customer_data");
			ResultSet results= session.execute(topBalance);

			ArrayList<CustomerBalance> objs = new ArrayList<CustomerBalance>();
			for(Row row : results)
			{
				objs.add(new CustomerBalance(row.getInt("c_w_id"),row.getInt("c_d_id"),row.getInt("c_id"),row.getDouble("c_balance")));
			}
			Collections.sort(objs,new CustomerComparator());

			int itr = 0;
			for(CustomerBalance c : objs)
			{
				itr++;
				String[] customerData = index.search(c.getWid() + "" + c.getDid() + "" + c.getCid(), "customer-id", "customer-csv").get(0).split(",");
                String[] warehouseStaticInfo = index.search(c.getWid() + "", "warehouse-id", "warehouse-csv").get(0).split(",");
                String[] districtStaticInfo = index.search(c.getWid() + "" + c.getDid(), "district-id", "district-csv").get(0).split(",");
                printWriter.write("Top Balance Transaction--------"+"\n");
                printWriter.write("Customer name : " + customerData[3] + " " + customerData[4] + " " + customerData[5]
                                + "| Customer Balance : "+c.getC_balance() +" | Warehouse name "+warehouseStaticInfo[1]
                                + "| District name "+districtStaticInfo[2]);
				if(itr == 10)
					break;
			}
			printWriter.flush();
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