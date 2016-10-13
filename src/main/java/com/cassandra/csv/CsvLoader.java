package com.cassandra.csv;

import com.cassandra.utilities.Lucene;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by manisha on 09/10/2016.
 */
public class CsvLoader {

    public static void main(String[] args){
        Properties properties =null;
        Lucene lucene = new Lucene();
        try {
            InputStream inputStream = new FileInputStream("/Users/manisha/NUS/DD/gitProjects/cassandra_store/src/main/resources/config.properties");
            properties = new Properties();
            properties.load(inputStream);
            lucene.initSearch(properties);
//        new CustomerDataCsv().prepareCsv();
//        new OrderStatusTransactionCsv().prepareCsv();
            new NewOrderTransactionCsv().prepareCsv(lucene, properties);
//        new NextOrderCsv().prepareCsv();
//        new StockLevelTransactionCsv().prepareCsv();
//        new WarehouseCsv().prepareCsv();
//        new DistrictCsv().prepareCsv();
//        new CustomerCsv().prepareCsv();
//        new StockCsv().prepareCsv();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
