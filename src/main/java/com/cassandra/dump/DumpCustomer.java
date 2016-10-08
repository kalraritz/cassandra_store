package com.cassandra.dump;

import com.cassandra.CassandraSession;
import com.cassandra.models.Customer;
import com.cassandra.models.Order;
import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.opencsv.CSVReader;
import org.apache.log4j.Logger;

import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by manisha on 07/10/2016.
 */
public class DumpCustomer {

    private static Logger logger = Logger.getLogger(DumpCustomer.class);


    public void dump() {
        try {
            Session session = CassandraSession.getSession();
            MappingManager manager = new MappingManager(session);
            ClassLoader classLoader = getClass().getClassLoader();
            InputStreamReader customerFile = new InputStreamReader(classLoader.getResource("customer.csv").openStream());
            CSVReader customerCsv = new CSVReader(customerFile);
            Iterator<String[]> iterator = customerCsv.iterator();
            while(iterator.hasNext()) {
                String[] customerRow = iterator.next();
                Date since = new Date();
                if(customerRow[12] != null && !customerRow[12].equals("null")) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.SSS");
                    try {
                        since = simpleDateFormat.parse(customerRow[12]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                Customer customer = new Customer(Integer.parseInt(customerRow[2]), Integer.parseInt(customerRow[0]),
                        Integer.parseInt(customerRow[1]), customerRow[3], customerRow[4], customerRow[5], customerRow[6],
                        customerRow[7], customerRow[8], customerRow[9], customerRow[10], customerRow[11], since, customerRow[13],
                        Double.parseDouble(customerRow[14]), Double.parseDouble(customerRow[15]), customerRow[20]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CassandraSession.closeSession();
        }
    }
}

