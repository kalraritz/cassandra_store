package com.cassandra.csv;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.Iterator;

/**
 * Created by manisha on 09/10/2016.
 */
public class NextOrderCsv {

    public void prepareCsv() {
        try {
            PrintWriter pw = new PrintWriter(new File("/Users/manisha/NUS/DD/project/csvFiles/newOrderTransaction.csv"));
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = new FileInputStream("/Users/manisha/Downloads/D8-data/district.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            CSVReader districtCsv = new CSVReader(inputStreamReader);
            Iterator<String[]> iterator = districtCsv.iterator();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
