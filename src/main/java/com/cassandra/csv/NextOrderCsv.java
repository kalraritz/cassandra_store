package com.cassandra.csv;

import com.cassandra.models.NextOrder;
import com.cassandra.utilities.Lucene;
import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created by manisha on 09/10/2016.
 */
public class NextOrderCsv {

    private static Logger logger = Logger.getLogger(NextOrderCsv.class);

    public void prepareCsv(Properties properties) {
        String csv_dump_path = properties.getProperty("csv_dump_path");
        String csv_files_path = properties.getProperty("csv_files_path");
        PrintWriter pw = null;
        logger.info("Preparing Csv for NextOrder....");
        try {
            pw = new PrintWriter(new File(csv_dump_path + "next_order.csv"));
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = new FileInputStream(csv_files_path + "district.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            CSVReader districtCsv = new CSVReader(inputStreamReader);
            Iterator<String[]> iterator = districtCsv.iterator();
            while(iterator.hasNext()) {
                List<String> nextOrderRowList = new ArrayList<>();
                String[] districtRow = iterator.next();
                List<String> warehouse = new Lucene().search(districtRow[0], "warehouse-id", "warehouse-csv");
                String[] warehouseRow = warehouse.get(0).split(",");
                nextOrderRowList.add(districtRow[0]);
                nextOrderRowList.add(districtRow[1]);
                nextOrderRowList.add(districtRow[10]);
                nextOrderRowList.add(warehouseRow[8]);
                nextOrderRowList.add(districtRow[9]);
                String nextOrder = StringUtils.join(nextOrderRowList, ",");
                pw.write(nextOrder+"\n");
                pw.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in preparing NextOrder csv!!");
        } finally {
            pw.close();
            logger.info("Done preparing NextOrder csv!!");
        }
    }
}
