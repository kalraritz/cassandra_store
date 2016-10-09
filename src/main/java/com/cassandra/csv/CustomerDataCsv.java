package com.cassandra.csv;

import com.cassandra.dump.DumpCustomer;
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
public class CustomerDataCsv {

    private static Logger logger = Logger.getLogger(CustomerDataCsv.class);

    public void prepareCsv() {
    try {
        PrintWriter pw = new PrintWriter(new File("/Users/manisha/NUS/DD/project/csvFiles/customerData.csv"));
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = new FileInputStream("/Users/manisha/Downloads/D8-data/customer.csv");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        CSVReader customerCsv = new CSVReader(inputStreamReader);
        Iterator<String[]> iterator = customerCsv.iterator();
        while(iterator.hasNext()) {
            String[] customerRow = iterator.next();
            List<String> list = new ArrayList<>();
            list.add(customerRow[0]);
            list.add(customerRow[1]);
            list.add(customerRow[2]);
            list.add(customerRow[16]);
            list.add(customerRow[17]);
            list.add(customerRow[18]);
            list.add(customerRow[19]);
            String str = StringUtils.join(list, ",");
            pw.write(str+"\n");
            pw.flush();

        }
        pw.close();
        logger.info("done preparing CustomerData csv!!");
    } catch (Exception e) {
        e.printStackTrace();
        logger.error("Error in  preparing CustomerData csv");
    }
    }

}
