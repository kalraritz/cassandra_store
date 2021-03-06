package com.cassandra.transactions;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cassandra.TransactionDriver;
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
    static String ytdcolums[] = {"no_w_ytd", "no_d_ytd"};
    static String customercolums[] = {"c_balance", "c_ytd_payment", "c_payment_cnt"};
    static Statement statement;

    public void readOrderStatus(int w_id, int d_id, int c_id, double payment, Session session, Lucene lucene, PrintWriter printWriter) {
        try {
            Statement statement = QueryBuilder.select(ytdcolums).from("next_order")
                    .where(QueryBuilder.eq("no_w_id", w_id))
                    .and(QueryBuilder.eq("no_d_id", d_id));
            Row results = session.execute(statement).one();
            double d_ytd = results.getDouble("no_d_ytd") + payment;
            double w_ytd = results.getDouble("no_w_ytd") + payment;
            String dupdate = "update next_order set no_w_ytd = " + w_ytd + ", no_d_ytd=" + d_ytd + " where no_w_id =" + w_id + " and no_d_id =" + d_id;
            session.execute(dupdate);

            statement = QueryBuilder.select(customercolums).from("customer_data")
                    .where(QueryBuilder.eq("c_w_id", w_id))
                    .and(QueryBuilder.eq("c_d_id", d_id))
                    .and(QueryBuilder.eq("c_id", c_id));
            results = session.execute(statement).one();
            double c_balance = results.getDouble("c_balance");
            double c_ytd = results.getDouble("c_ytd_payment");
            int c_payment_cnt = results.getInt("c_payment_cnt");
            statement = QueryBuilder.update("customer_data").with(QueryBuilder.set("c_balance", c_balance - payment))
                    .and(QueryBuilder.set("c_ytd_payment", c_ytd + payment))
                    .and(QueryBuilder.set("c_payment_cnt", c_payment_cnt + 1))
                    .where(QueryBuilder.eq("c_w_id", w_id))
                    .and(QueryBuilder.eq("c_d_id", d_id))
                    .and(QueryBuilder.eq("c_id", c_id));
            session.execute(statement);

            String customerStaticInfo = lucene.search(w_id + "" + d_id + "" + c_id, "customer-id", "customer-csv").get(0);
            String indexData[] = customerStaticInfo.split(",");
            String output = "PAYMENT TRANSACTION--------" + "\n";

            //printWriter.write();
            output += "Customer Identifier : " + w_id + "" + d_id + "" + c_id +
                    " | Customer name : " + indexData[3] + " " + indexData[4] + " " + indexData[5] +
                    " | Customer address : " + indexData[6] + " " + indexData[7] + " " + indexData[8] + " " + indexData[9] +
                    " " + indexData[10] + "\n" + "Customer phone : " + indexData[11] + "\n" + "Entry created date : " + indexData[11] +
                    " | Customer credit status : " + indexData[12] + "\n" + "Customer credit limit : " + indexData[13] +
                    " | Customer discount rate : " + indexData[14] + "\n" + "Customer balance payment : " + indexData[15] +
                    " | Customer credit limit : " + indexData[16] + "\n" + "Customer discount rate : " + indexData[17] +
                    " | Customer outstanding balance : " + indexData[18] + "\n";

            String warehouseStaticInfo = lucene.search(w_id + "", "warehouse-id", "warehouse-csv").get(0);
            indexData = warehouseStaticInfo.split(",");
            output += "Warehouse address : " + indexData[2] + " " + indexData[3] + " " + indexData[4] + " "
                    + indexData[5] + " " + indexData[6] + "\n";

            String districtStaticInfo = lucene.search(w_id + "" + d_id, "district-id", "district-csv").get(0);
            indexData = districtStaticInfo.split(",");
            output += "District address : " + indexData[2] + " " + indexData[3] + " " + indexData[4] + " "
                    + indexData[5] + " " + indexData[6] + "\n";
            output += "Payment amount : " + payment + "\n";
            printWriter.write(output + "\n");
            printWriter.write("\n");
            printWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}