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
public class DistrictCsv {

    private static Logger logger = Logger.getLogger(DistrictCsv.class);

    public void prepareCsv() {
        PrintWriter pw = null;
        logger.info("Preparing csv for District...");
        try{
            pw= new PrintWriter(new File("/Users/manisha/NUS/DD/project/csvFiles/district.csv"));
            InputStream inputStream = new FileInputStream("/Users/manisha/Downloads/D8-data/district.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            CSVReader districtCsv = new CSVReader(inputStreamReader);
            Iterator<String[]> iterator = districtCsv.iterator();
            while(iterator.hasNext()) {
                List<String> districtRowList = new ArrayList<>();
                String[] districtRow = iterator.next();
                districtRowList.add(districtRow[1]); //w_id
                districtRowList.add(districtRow[0]); //d_id
                districtRowList.add(districtRow[2]); //d_name
                districtRowList.add(districtRow[3]); // street 1
                districtRowList.add(districtRow[4]); //street2
                districtRowList.add(districtRow[5]); //city
                districtRowList.add(districtRow[6]); // state
                districtRowList.add(districtRow[7]); //zip
                districtRowList.add(districtRow[8]); //tax
                String district = StringUtils.join(districtRowList, ",");
                pw.write(district+"\n");
                pw.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in preparing district csv!!");
        } finally {
            pw.close();
            logger.info("Done preparing district csv!!");

        }
    }


}
