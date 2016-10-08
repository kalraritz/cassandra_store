package com.cassandra.transactions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.cassandra.beans.Item;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.CodecRegistry;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
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
		try
		{
			String ytdcolums[]={"w_ytd","d_ytd"};
			Statement getYTDInfo = QueryBuilder.select(ytdcolums).from("next_order")
					.where(QueryBuilder.eq("w_id",w_id))
					.and(QueryBuilder.eq("d_id",d_id));

			ResultSet results= session.execute(getYTDInfo);
			double d_ytd = results.one().getDouble("d_ytd");
			double w_ytd = results.one().getDouble("w_ytd");
			QueryBuilder.update("payment_transaction").with(QueryBuilder.set("w_ytd",w_ytd + payment ))
			.and(QueryBuilder.set("d_ytd", d_ytd + payment))
			.where(QueryBuilder.eq("w_id", d_id))
			.and(QueryBuilder.eq("d_id",d_id));

			String customercolums[]={"c_balance","c_ytd","c_payment"};
			Statement customerInfo = QueryBuilder.select(customercolums).from("customer")
					.where(QueryBuilder.eq("w_id",w_id))
					.and(QueryBuilder.eq("d_id",d_id))
					.and(QueryBuilder.eq("c_id",c_id));
			results= session.execute(customerInfo);
			double c_balance = results.one().getDouble("c_balance");
			double c_ytd = results.one().getDouble("c_ytd");
			double c_payment = results.one().getDouble("c_payment");
			QueryBuilder.update("customer").with(QueryBuilder.set("c_balance",c_balance - payment ))
			.and(QueryBuilder.set("c_ytd", c_ytd + payment))
			.and(QueryBuilder.set("c_payment", c_payment + 1))
			.where(QueryBuilder.eq("w_id",w_id))
			.and(QueryBuilder.eq("d_id",d_id))
			.and(QueryBuilder.eq("c_id",c_id));

			String customerStaticInfo = new PaymentTransaction().search(w_id+ "" + d_id+""+c_id, "customer-id", "customer-csv").get(0);
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


			String warehouseStaticInfo = new PaymentTransaction().search(w_id+"", "warehouse-id", "warehouse-csv").get(0);
			indexData = warehouseStaticInfo.split(",");
			System.out.println("Warehouse address : "+ indexData[2]+ " " + indexData[3]+ " " + indexData[4] + " " 
					+ indexData[5]+ " " + indexData[6]);

			String districtStaticInfo = new PaymentTransaction().search(w_id+""+d_id, "district-id", "district-csv").get(0);
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

	public List<String> search(String searchQuery, String keyType, String csvType) throws Exception{
		File file = new File(luceneIndexFolder);
		Directory directory = FSDirectory.open(file.toPath());
		DirectoryReader ireader = DirectoryReader.open(directory);
		IndexSearcher isearcher = new IndexSearcher(ireader);

		QueryParser parser = new QueryParser(keyType, new StandardAnalyzer());
		Query query = parser.parse(searchQuery);
		TotalHitCountCollector collector = new TotalHitCountCollector();
		isearcher.search(query, collector);
		TopDocs topDocs = isearcher.search(query, Math.max(1, collector.getTotalHits()));
		ScoreDoc[] hits = isearcher.search(query, topDocs.totalHits).scoreDocs;
		List<String> items = new ArrayList<>();
		for (int i = 0; i < hits.length; i++) {
			Document hitDoc = isearcher.doc(hits[i].doc);
			items.add(hitDoc.getField(csvType).stringValue());
		}
		return items;
	}
}