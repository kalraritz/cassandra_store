package com.cassandra.csv;

import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by manisha on 09/10/2016.
 */
public class StockLevelTransactionCsv {

    private static Logger logger = Logger.getLogger(StockLevelTransactionCsv.class);

    public void prepareCsv() {
        PrintWriter pw =null;
        logger.info("Preparing csv for StatusLevelTransaction....");

        try {
            pw = new PrintWriter(new File("/Users/manisha/NUS/DD/project/csvFiles/stockLevelTransaction.csv"));
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = new FileInputStream("/Users/manisha/Downloads/D8-data/stock.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            InputStreamReader orderFile = new InputStreamReader(classLoader.getResource("order-test.csv").openStream());
            CSVReader stockCsv = new CSVReader(inputStreamReader);
            Iterator<String[]> iterator = stockCsv.iterator();
            while(iterator.hasNext()) {
                List<String> stockLevelRowList = new ArrayList<>();
                String[] stockRow = iterator.next();
                stockLevelRowList.add(stockRow[0]); //warehouse id
                stockLevelRowList.add(stockRow[1]); //item id
                stockLevelRowList.add(stockRow[2]); //quantity
                stockLevelRowList.add(stockRow[3]); // ytd
                stockLevelRowList.add(stockRow[4]); //order_cnt
                stockLevelRowList.add(stockRow[5]); //remote_Cnt
                String stockLevel = StringUtils.join(stockLevelRowList, ",");
                pw.write(stockLevel+"\n");
                pw.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in preparing StockLevelTransaction csv!!");
        } finally {
            pw.close();
            logger.info("Done preparing StockLevelTransaction csv!!");
        }

    }


}
