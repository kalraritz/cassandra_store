package com.cassandra.dump;

import com.cassandra.CassandraSession;
import com.cassandra.models.CustomerData;
import com.cassandra.models.District;
import com.cassandra.utilities.Lucene;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.opencsv.CSVReader;
import org.apache.log4j.Logger;

import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

/**
 * Created by manisha on 08/10/2016.
 */
public class DumpCustomerData {

    private static Logger logger = Logger.getLogger(DumpCustomerData.class);

    public void dump() {
        try {
            Session session = CassandraSession.getSession();
            MappingManager manager = new MappingManager(session);
            Mapper<CustomerData> mapper = manager.mapper(CustomerData.class);
            ClassLoader classLoader = getClass().getClassLoader();
            InputStreamReader customerFile = new InputStreamReader(classLoader.getResource("customer.csv").openStream());
            CSVReader customerCsv = new CSVReader(customerFile);
            Iterator<String[]> iterator =  customerCsv.iterator();
            while(iterator.hasNext()) {
                String[] customerRow = iterator.next();
                int customerId = Integer.parseInt(customerRow[2]);
                int warehouseId = Integer.parseInt(customerRow[0]);
                int districtId = Integer.parseInt(customerRow[1]);
                double balance = Double.parseDouble(customerRow[16]);
                CustomerData customerData = new CustomerData(customerId, warehouseId, districtId, balance,
                        Integer.parseInt(customerRow[19]), Double.parseDouble(customerRow[17]), Integer.parseInt(customerRow[18]));
                mapper.save(customerData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CassandraSession.closeSession();
        }

    }
}
