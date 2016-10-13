package com.cassandra.transactions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.cassandra.beans.Item;
import com.cassandra.utilities.Lucene;
import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class PaymentTransaction {
	private final String luceneIndexFolder = "./lucene-index";
	public void readOrderStatus(int w_id,int d_id,int c_id,double payment,Session session)
	{
		Lucene index = new Lucene();
		try
		{
			String ytdcolums[]={"w_ytd","d_ytd"};
			Statement getYTDInfo = QueryBuilder.select(ytdcolums).from("next_order")
					.where(QueryBuilder.eq("w_id",w_id))
					.and(QueryBuilder.eq("d_id",d_id));

			Row results= session.execute(getYTDInfo).one();

			double d_ytd = results.getDouble("d_ytd");
			double w_ytd = results.getDouble("w_ytd");
			QueryBuilder.update("payment_transaction").with(QueryBuilder.set("w_ytd",w_ytd + payment ))
					.and(QueryBuilder.set("d_ytd", d_ytd + payment))
					.where(QueryBuilder.eq("w_id", d_id))
					.and(QueryBuilder.eq("d_id",d_id));

			String customercolums[]={"c_balance","c_ytd","c_payment"};
			Statement customerInfo = QueryBuilder.select(customercolums).from("customer")
					.where(QueryBuilder.eq("w_id",w_id))
					.and(QueryBuilder.eq("d_id",d_id))
					.and(QueryBuilder.eq("c_id",c_id));
			results= session.execute(customerInfo).one();
			double c_balance = results.getDouble("c_balance");
			double c_ytd = results.getDouble("c_ytd");
			double c_payment = results.getDouble("c_payment");
			QueryBuilder.update("customer").with(QueryBuilder.set("c_balance",c_balance - payment ))
					.and(QueryBuilder.set("c_ytd", c_ytd + payment))
					.and(QueryBuilder.set("c_payment", c_payment + 1))
					.where(QueryBuilder.eq("w_id",w_id))
					.and(QueryBuilder.eq("d_id",d_id))
					.and(QueryBuilder.eq("c_id",c_id));

			String customerStaticInfo = index.search(w_id+ "" + d_id+""+c_id, "customer-id", "customer-csv").get(0);
			String indexData[] = customerStaticInfo.split(",");
			System.out.println("Customer Identifier : "+w_id+""+d_id+""+c_id);
			System.out.println("Customer name : " +indexData[3]+" "+indexData[4]+" "+indexData[5]);
			System.out.println("Customer address : "+indexData[6]+" "+indexData[7]+" "+indexData[8]+" "+indexData[9]+
					" "+indexData[10]);
			System.out.println("Customer phone : "+indexData[11]);
			System.out.println("Entry created date : "+indexData[11]);
			System.out.println("Customer credit status : "+indexData[12]);
			System.out.println("Customer credit limit : "+indexData[13]);
			System.out.println("Customer discount rate : "+indexData[14]);
			System.out.println("Customer balance payment : "+indexData[15]);
			System.out.println("Customer credit limit : "+indexData[16]);
			System.out.println("Customer discount rate : "+indexData[17]);
			System.out.println("Customer outstanding balance : "+indexData[18]);


			String warehouseStaticInfo = index.search(w_id+"", "warehouse-id", "warehouse-csv").get(0);
			indexData = warehouseStaticInfo.split(",");
			System.out.println("Warehouse address : "+ indexData[2]+ " " + indexData[3]+ " " + indexData[4] + " "
					+ indexData[5]+ " " + indexData[6]);

			String districtStaticInfo = index.search(w_id+""+d_id, "district-id", "district-csv").get(0);
			indexData = districtStaticInfo.split(",");
			System.out.println("District address : "+ indexData[2]+ " " + indexData[3]+ " " + indexData[4] + " "
					+ indexData[5]+ " " + indexData[6]);

			System.out.println("Payment amount : "+payment);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}