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
 * Created by manisha on 09/10/2016.
 */
public class CustomerDataCsv {

    private static Logger logger = Logger.getLogger(CustomerDataCsv.class);

    public void prepareCsv(Properties properties) {
        PrintWriter pw = null;
        logger.info("Preparing csv for CustomerData...");
        try {
            String csv_dump_path = properties.getProperty("csv_dump_path");
            String csv_files_path = properties.getProperty("csv_files_path");
            pw = new PrintWriter(new File(csv_dump_path + "customer_data.csv"));
            InputStream inputStream = new FileInputStream(csv_files_path + "customer.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            CSVReader customerCsv = new CSVReader(inputStreamReader);
            Iterator<String[]> iterator = customerCsv.iterator();
            while (iterator.hasNext()) {
                String[] customerRow = iterator.next();
                List<String> customerDataRowList = new ArrayList<>();
                customerDataRowList.add(customerRow[0]);
                customerDataRowList.add(customerRow[1]);
                customerDataRowList.add(customerRow[2]);
                customerDataRowList.add(customerRow[16]);
                customerDataRowList.add(customerRow[17]);
                customerDataRowList.add(customerRow[18]);
                customerDataRowList.add(customerRow[19]);
                String str = StringUtils.join(customerDataRowList, ",");
                pw.write(str + "\n");
                pw.flush();

            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in  preparing CustomerData csv");
        } finally {
            pw.close();
            logger.info("done preparing CustomerData csv!!");
        }
    }

}
