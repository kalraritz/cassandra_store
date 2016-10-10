package com.cassandra.csv;

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
public class CustomerCsv {

    private static Logger logger = Logger.getLogger(CustomerCsv.class);

    public void prepareCsv() {
        PrintWriter pw = null;
        logger.info("Preparing csv for Customer...");
        try{
            pw = new PrintWriter(new File("/Users/manisha/NUS/DD/project/csvFiles/customer.csv"));
            InputStream inputStream = new FileInputStream("/Users/manisha/Downloads/D8-data/customer.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            CSVReader customerCsv = new CSVReader(inputStreamReader);
            Iterator<String[]> iterator = customerCsv.iterator();
            while(iterator.hasNext()) {
                List<String> customerRowList = new ArrayList<>();
                String[] customerRow = iterator.next();
                customerRowList.add(customerRow[0]); // w_id
                customerRowList.add(customerRow[1]); //d_id
                customerRowList.add(customerRow[2]); //c_id
                customerRowList.add(customerRow[3]); //c_first
                customerRowList.add(customerRow[4]); //c_middle
                customerRowList.add(customerRow[5]); //c_last
                customerRowList.add(customerRow[6]); //street 1
                customerRowList.add(customerRow[7]); //street 2
                customerRowList.add(customerRow[8]); //city
                customerRowList.add(customerRow[9]); //state
                customerRowList.add(customerRow[10]); //zip
                customerRowList.add(customerRow[11]); //c_phone
                customerRowList.add(customerRow[12]); //since
                customerRowList.add(customerRow[13]); //credit
                customerRowList.add(customerRow[14]); //credit_lim
                customerRowList.add(customerRow[15]); //discount
                customerRowList.add(customerRow[16]); //data
                String customer = StringUtils.join(customerRowList, ",");
                pw.write(customer+"\n");
                pw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in  preparing customer csv");

        } finally {
            pw.close();
            logger.info("Done preparing customer csv!!");

        }
    }

}
