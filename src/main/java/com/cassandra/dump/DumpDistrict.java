package com.cassandra.dump;

import com.cassandra.CassandraSession;
import com.cassandra.models.District;
import com.cassandra.models.Warehouse;
import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.opencsv.CSVReader;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ritesh on 05/10/16.
 */
public class DumpDistrict {
    private static Logger logger = Logger.getLogger(DumpDistrict.class);

    public void dump() {
        /*Session session = CassandraSession.getSession();
        MappingManager manager = new MappingManager(session);
        Mapper<Warehouse> mapper = manager.mapper(Warehouse.class);

        // D_W_ID,D_ID,D_NAME,D_STREET_1,D_STREET_2,D_CITY,D_STATE,D_ZIP,D_TAX,D_YTD,D_NEXT_O_ID
        // 1,1,crobqijak,inapdxtqbxkvxpypsxb,jfzeqkdcgifsqq,rwbwegnnsoebotmunf,ZX,123456789,0.1319,30000.0,3001
        BatchStatement batch = new BatchStatement();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStreamReader warehousefile = new InputStreamReader(classLoader.getResource("district.csv").openStream());
            CSVReader warehousecsv = new CSVReader(warehousefile);
            String[] nextLine;
            while ((nextLine = warehousecsv.readNext()) != null) {
                if (nextLine.length < 8) {
                    logger.error("Error while parsing row " + nextLine + " in district table");
                }
                int id = Integer.parseInt(nextLine[0]);
                int wid = Integer.parseInt(nextLine[1]);
                double tax = Double.parseDouble(nextLine[7]);
                District district = new District(id, wid, name, street, street2, city, state, zip, tax);
                Statement statement = mapper.saveQuery(warehouse);
                batch.add(statement);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        session.execute(batch);*/
    }

}
