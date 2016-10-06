package com.cassandra.dump;

import com.cassandra.CassandraInit;
import com.cassandra.CassandraSession;
import com.cassandra.models.Order;
import com.cassandra.models.Warehouse;
import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.opencsv.CSVReader;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import com.sun.tools.internal.ws.wscompile.Options;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ritesh on 05/10/16.
 */
public class DumpWarehouse {

    private static Logger logger = Logger.getLogger(DumpWarehouse.class);

    public void dump() {
        Session session = CassandraSession.getSession();
        MappingManager manager = new MappingManager(session);
        Mapper<Warehouse> mapper = manager.mapper(Warehouse.class);

        // W ID,W NAME,W STREET 1,W STREET 2,W CITY,W STATE,W ZIP,W TAX,W YTD
        // 1,sxvnjhpdq,xvcrastvybcwvmgny,rxvzxkgxtspsjdgy,uegqflaqlo,FL,123456789,0.129,300000.0
        BatchStatement batch = new BatchStatement();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStreamReader warehousefile = new InputStreamReader(classLoader.getResource("warehouse.csv").openStream());
            CSVReader warehousecsv = new CSVReader(warehousefile);
            String[] nextLine;
            while ((nextLine = warehousecsv.readNext()) != null) {
                if (nextLine.length < 8) {
                    logger.error("Error while parsing row " + nextLine + " in warehouse table");
                }
                int id = Integer.parseInt(nextLine[0]);
                double tax = Double.parseDouble(nextLine[7]);
                Warehouse warehouse = new Warehouse(id, nextLine[1], nextLine[2], nextLine[3], nextLine[4], nextLine[5], nextLine[6], tax);
                Statement statement = mapper.saveQuery(warehouse);
                batch.add(statement);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Dumped Warehouse");
        session.execute(batch);
    }
}
