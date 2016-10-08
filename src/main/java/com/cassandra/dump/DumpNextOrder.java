package com.cassandra.dump;

import com.cassandra.CassandraSession;
import com.cassandra.models.CustomerData;
import com.cassandra.models.NextOrder;
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
public class DumpNextOrder {

    private static Logger logger = Logger.getLogger(DumpNextOrder.class);

    public void dump() {
        try {
            Session session = CassandraSession.getSession();
            MappingManager manager = new MappingManager(session);
            Mapper<NextOrder> mapper = manager.mapper(NextOrder.class);
            ClassLoader classLoader = getClass().getClassLoader();
            InputStreamReader districtFile = new InputStreamReader(classLoader.getResource("district.csv").openStream());
            CSVReader districtCsv = new CSVReader(districtFile);
            Iterator<String[]> iterator = districtCsv.iterator();
            while(iterator.hasNext()) {
                String[] districtRow = iterator.next();
                List<String> warehouse = new Lucene().search(districtRow[0], "warehouse-id", "warehouse-csv");
                String[] warehouseRow = warehouse.get(0).split(",");
                NextOrder nextOrder = new NextOrder(Integer.parseInt(districtRow[0]), Integer.parseInt(districtRow[1]),
                        Integer.parseInt(districtRow[10]), Double.parseDouble(warehouseRow[8]), Double.parseDouble(districtRow[9]));
                mapper.save(nextOrder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CassandraSession.closeSession();
        }
    }

}
