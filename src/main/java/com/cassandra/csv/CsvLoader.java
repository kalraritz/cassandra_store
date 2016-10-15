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
            String configFilePath = System.getenv("DD_CONFIG_FILE");
            InputStream inputStream = new FileInputStream(configFilePath);
            properties = new Properties();
            properties.load(inputStream);
            lucene.initSearch(properties);
        new CustomerDataCsv().prepareCsv(properties);
//        new OrderStatusTransactionCsv().prepareCsv();
//            new NewOrderTransactionCsv().prepareCsv(lucene, properties);
        new NextOrderCsv().prepareCsv(properties);
        new StockLevelTransactionCsv().prepareCsv(properties);
        new WarehouseCsv().prepareCsv(properties);
        new DistrictCsv().prepareCsv(properties);
        new CustomerCsv().prepareCsv(properties);
//        new StockCsv().prepareCsv(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
