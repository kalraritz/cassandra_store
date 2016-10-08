package com.cassandra.dump;

import com.cassandra.CassandraSession;
import com.cassandra.models.Stock;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import com.opencsv.CSVReader;
import org.apache.log4j.Logger;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by manisha on 07/10/2016.
 */
public class DumpStock {

    private static Logger logger = Logger.getLogger(DumpStock.class);

    Session session = CassandraSession.getSession();
    MappingManager manager = new MappingManager(session);

    public void dump() {

    }
}
