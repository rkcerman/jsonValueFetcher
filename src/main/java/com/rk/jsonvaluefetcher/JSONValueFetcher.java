package main.java.com.rk.jsonvaluefetcher;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by rkcerman on 11.7.2016.
 */
public class JSONValueFetcher extends Fetcher {

    static Logger logger = LoggerFactory.getLogger(JSONValueFetcher.class);

    public static void main(String[] args) {
        app();
    }

    public static void app() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name of the csv file: ");
        String fileName = scanner.next();

        CSVhandler csVhandler = new CSVhandler(fileName);

        scanner.close();
    }

}
