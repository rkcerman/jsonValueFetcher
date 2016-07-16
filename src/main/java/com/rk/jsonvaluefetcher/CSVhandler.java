package main.java.com.rk.jsonvaluefetcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.Map;


/**
 * Created by rkcerman on 11.7.2016.
 */
public class CSVhandler {

    private static String CSV_FILENAME;
    private static final String VALUE_HEADER = "value";
    private static Logger logger = LoggerFactory.getLogger(CSVhandler.class);

    public CSVhandler(String csvFilename) {
        CSV_FILENAME = csvFilename;
    }

    public void handle() {
        try {
            readWithCsvBeanReader();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private static CellProcessor[] getProcessors() {

        final CellProcessor[] processors = new CellProcessor[]{
                new NotNull(),
                new NotNull()
        };

        return processors;
    }

    public static void readWithCsvBeanReader() throws Exception {

        ICsvMapReader mapReader = null;
        ICsvMapWriter mapWriter = null;
        try {
             mapReader = new CsvMapReader(new FileReader(CSV_FILENAME), CsvPreference
                    .STANDARD_PREFERENCE);
             mapWriter = new CsvMapWriter(new FileWriter(CSV_FILENAME.replace(".csv",
                     "_new.csv")),
                     CsvPreference
                     .STANDARD_PREFERENCE);

            final String[] header = mapReader.getHeader(true);
            final CellProcessor[] processors = getProcessors();

            final String[] writeHeader = new String[header.length + 1];
            System.arraycopy(header, 0, writeHeader, 0, header.length);
            writeHeader[writeHeader.length - 1] = VALUE_HEADER;

            mapWriter.writeHeader(writeHeader);

            Map<String, Object> linkMap;
            while( (linkMap = mapReader.read(header, processors)) !=
                    null ) {

                Collector collector = new Collector();
                URL URL = new URL(linkMap.get("link").toString());
                String path = linkMap.get("path").toString();

                Object value = collector.collect(collector
                    .fetchResponse(URL), path);
                logger.info("Value is: " + value);

                linkMap.put(VALUE_HEADER, value);
                mapWriter.write(linkMap, writeHeader);
            }
        }
        finally {
            if( mapReader != null ) {
                mapReader.close();
            }
            if( mapWriter != null) {
                mapWriter.close();
            }
        }
    }
}
