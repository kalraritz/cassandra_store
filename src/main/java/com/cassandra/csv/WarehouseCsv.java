package com.cassandra.csv;

import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created by manisha on 10/10/2016.
 */
public class WarehouseCsv {

    private static Logger logger = Logger.getLogger(WarehouseCsv.class);

    public void prepareCsv(Properties properties) {
        String csv_dump_path = properties.getProperty("csv_dump_path");
        String csv_files_path = properties.getProperty("csv_files_path");
        PrintWriter pw = null;
        logger.info("Preparing csv for warehouse....");
        try{
            pw= new PrintWriter(new File(csv_dump_path + "warehouse.csv"));
            InputStream inputStream = new FileInputStream(csv_files_path + "warehouse.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            CSVReader warehouseCsv = new CSVReader(inputStreamReader);
            Iterator<String[]> iterator = warehouseCsv.iterator();
            while(iterator.hasNext()){
                List<String> warehouseRowList = new ArrayList<>();
                String[] warehouseRow = iterator.next();
                warehouseRowList.add(warehouseRow[0]); //warehouse_id
                warehouseRowList.add(warehouseRow[1]); //warehouse_name
                warehouseRowList.add(warehouseRow[2]); //street 1
                warehouseRowList.add(warehouseRow[3]); //street 2
                warehouseRowList.add(warehouseRow[4]); //city
                warehouseRowList.add(warehouseRow[5]); //state
                warehouseRowList.add(warehouseRow[6]); //zip
                warehouseRowList.add(warehouseRow[7]); //tax
                String warehouse = StringUtils.join(warehouseRowList, ",");
                pw.write(warehouse+"\n");
                pw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in preparing Warehouse csv!!");
        } finally {
            pw.close();
            logger.info("Done preparing Wrehouse csv!!");

        }

    }

}
