package com.cassandra.utilities;

import com.cassandra.dump.DumpDistrict;
import com.opencsv.CSVReader;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by manisha on 08/10/2016.
 */
public class Lucene {

    private static Logger logger = Logger.getLogger(Lucene.class);
    private final String luceneIndexFolder = "./lucene-index";
    public IndexWriter createIndex() throws Exception{
        IndexWriter indexWriter = null;

        try {
            File file = new File(luceneIndexFolder);
            Directory directory = FSDirectory.open(file.toPath());
            IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
            indexWriter = new IndexWriter(directory, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return indexWriter;
    }

    public void addDocumentToIndex(IndexWriter indexWriter, String csvFile, String csvType, String keyType) throws Exception {
        System.out.println("Adding "+csvFile+"data to index.....");
//        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = new FileInputStream("/Users/ritesh/Documents/D8-data/"+csvFile);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//        InputStreamReader file = new InputStreamReader(classLoader.getResource(csvFile).openStream());
        CSVReader csv = new CSVReader(inputStreamReader);
        Iterator<String[]> iterator = csv.iterator();
        while(iterator.hasNext()){
            String key ="";
            String[] row = iterator.next();
            Document document = new Document();
            if(csvType.equals("item-csv"))
                key = row[0];
            else if(csvType.equals("order-line-csv"))
                key = row[0] + row[1] + row[2];
            else if (csvType == "warehouse-csv")
                key = row[0];
            else if (csvType == "district-csv")
                key = row[0] + row[1];
            else if (csvType == "stock-csv")
                key = row[0] + row[1];
            else if (csvType == "customer-csv")
                key = row[0] + row[1]+ row[2];
            document.add(new Field(keyType, key, TextField.TYPE_STORED));
            document.add(new Field(csvType, String.join(",", row), TextField.TYPE_STORED));
            indexWriter.addDocument(document);
        }
        System.out.println("Added "+ csvFile+"data to index!!!");

    }

    public List<String> search(String searchQuery, String keyType, String csvType) throws Exception{
        File file = new File(luceneIndexFolder);
        Directory directory = FSDirectory.open(file.toPath());
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);

        QueryParser parser = new QueryParser(keyType, new StandardAnalyzer());
        Query query = parser.parse(searchQuery);
        TotalHitCountCollector collector = new TotalHitCountCollector();
        isearcher.search(query, collector);
        TopDocs topDocs = isearcher.search(query, Math.max(1, collector.getTotalHits()));
        ScoreDoc[] hits = isearcher.search(query, topDocs.totalHits).scoreDocs;
        List<String> items = new ArrayList<>();
        for (int i = 0; i < hits.length; i++) {
            Document hitDoc = isearcher.doc(hits[i].doc);
            items.add(hitDoc.getField(csvType).stringValue());
        }
        return items;
    }

    public static void main(String[] args) {
        Lucene lucene = new Lucene();
        try{
            IndexWriter indexWriter = lucene.createIndex();
            lucene.addDocumentToIndex(indexWriter, "item.csv", "item-csv", "item-id");
            lucene.addDocumentToIndex(indexWriter, "order-line.csv", "order-line-csv", "order-id");
            lucene.addDocumentToIndex(indexWriter, "warehouse.csv", "warehouse-csv", "warehouse-id");
            lucene.addDocumentToIndex(indexWriter, "district.csv", "district-csv", "district-id");
            lucene.addDocumentToIndex(indexWriter, "customer.csv", "customer-csv", "customer-id");
            lucene.addDocumentToIndex(indexWriter, "stock.csv", "stock-csv", "stock-id");
            indexWriter.close();
            System.exit(0);

//            lucene.search("111", "order-id", "order-line-csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
