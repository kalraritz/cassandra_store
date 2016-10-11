package com.cassandra.dump;

import com.cassandra.CassandraSession;
import com.cassandra.models.StockTransaction;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.opencsv.CSVReader;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * Created by manisha on 08/10/2016.
 */
public class DumpStockLevelTransaction {

    private static Logger logger = Logger.getLogger(DumpStockLevelTransaction.class);

    public void dump() {
        try {
            Session session = CassandraSession.getSession();
            MappingManager manager = new MappingManager(session);
            Mapper<StockTransaction> mapper = manager.mapper(StockTransaction.class);
            ClassLoader classLoader = getClass().getClassLoader();
//            InputStreamReader stockFile = new InputStreamReader(classLoader.getResource("stock.csv").openStream());
            InputStream inputStream = new FileInputStream("/home/m/manisha/D8-data/stock.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            CSVReader stockCsv = new CSVReader(inputStreamReader);
            Iterator<String[]> iterator = stockCsv.iterator();
            while(iterator.hasNext()) {
                String[] stockRow = iterator.next();
                int itemId = Integer.parseInt(stockRow[1]);
                int warehouseId = Integer.parseInt(stockRow[0]);
                StockTransaction stockTransaction = new StockTransaction(itemId, warehouseId, Double.parseDouble(stockRow[2]),
                        Double.parseDouble(stockRow[3]), Integer.parseInt(stockRow[4]), Integer.parseInt(stockRow[5]));
                mapper.save(stockTransaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CassandraSession.closeSession();
        }
    }


}
