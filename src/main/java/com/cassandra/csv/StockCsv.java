package com.cassandra.csv;

import com.cassandra.utilities.Lucene;
import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by manisha on 10/10/2016.
 */
public class StockCsv {
    private static Logger logger = Logger.getLogger(StockCsv.class);

    public void prepareCsv() {
        PrintWriter pw = null;
        logger.info("Preparing csv for stock...");
        try{
            pw= new PrintWriter(new File("/Users/manisha/NUS/DD/project/csvFiles/stock.csv"));
            InputStream inputStream = new FileInputStream("/Users/manisha/Downloads/D8-data/stock.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            CSVReader stockCsv = new CSVReader(inputStreamReader);
            Iterator<String[]> iterator = stockCsv.iterator();
            while(iterator.hasNext()) {
                List<String> stockRowList = new ArrayList<>();
                String[] stockRow = iterator.next();
                stockRowList.add(stockRow[0]); //w_id
                stockRowList.add(stockRow[1]); //i_id
                stockRowList.add(stockRow[6]); //dist_01
                stockRowList.add(stockRow[7]); //dist_02
                stockRowList.add(stockRow[8]); //dist_03
                stockRowList.add(stockRow[9]); //dist_04
                stockRowList.add(stockRow[10]); //dist_05
                stockRowList.add(stockRow[11]); //dist_06
                stockRowList.add(stockRow[12]); //dist_07
                stockRowList.add(stockRow[13]); //dist_08
                stockRowList.add(stockRow[14]); //dist_09
                stockRowList.add(stockRow[15]); //dist_10
                stockRowList.add(stockRow[16]); //data
                List<String> items = new Lucene().search(stockRow[1], "item-id", "item-csv");
                String[] itemRow = items.get(0).split(",");
                stockRowList.add(itemRow[1]); //i_name
                stockRowList.add(itemRow[3]); //img_id
                stockRowList.add(itemRow[2]); //i_price
                stockRowList.add(itemRow[4]); //i_data
                String stock = StringUtils.join(stockRowList, ",");
                pw.write(stock+"\n");
                pw.flush();

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in  preparing stock csv");

        } finally {
            pw.close();
            logger.info("done preparing stock csv!!");
        }
    }


}
