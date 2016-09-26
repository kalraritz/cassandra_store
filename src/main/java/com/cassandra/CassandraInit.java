package com.cassandra;

import com.cassandra.beans.OldItem;
import com.cassandra.beans.Item;
import com.cassandra.beans.OrderKey;

import java.util.Map;
import java.util.Set;

/**
 * Created by manikuramboyina on 25/9/16.
 */
public class CassandraInit {

    public static void main(String[] args) {
        DumpOrderData dump = new DumpOrderData();
        Map<Integer, OldItem> itemsMap = dump.getItems();
        Map<OrderKey, Set<Item>> map = dump.getOrderLineItems(itemsMap);

        dump.dumpNewOrderTransactionData(map);
//        dump.dumpNextOrderData();
//        dump.test();
    }

}
