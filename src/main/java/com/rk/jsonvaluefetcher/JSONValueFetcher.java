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
        System.out.println("URL: ");
        String URL = scanner.next();
        System.out.println("Path (in format ' foo/bar/foo ' ): ");
        String path = scanner.next();

        try {
            System.out.println("Your value is" + collectInt(fetchResponse(new URL(URL)),
                    path));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject fetchResponse(URL url) {
        JSONObject responseJson = null;
        try {
            responseJson  = fetchJSONObject(url);
        } catch (IOException e) {
            logger.info("URL error");
            e.printStackTrace();
        }
        return responseJson;
    }

    public static int collectInt(JSONObject responseJson, String path) {
        ArrayList<String> pathList = parsePath(path);
        JSONObject valueJson = responseJson;
        int value = 0;
        for(int i = 0; i < pathList.size(); i++) {
            if((pathList.size() - i) != 1) {
                logger.info("Current path: " + pathList.get(i));
                valueJson = valueJson.getJSONObject(pathList.get(i));
                logger.info("Current JSON: " + valueJson);

            } else {
                logger.info("Getting the value");
                value = valueJson.getInt(pathList.get(i));
            }
        }

        return value;
    }

    public static ArrayList parsePath(String path) {
        int slashCount = StringUtils.countMatches(path, "/");
        int startIndex = 0;
        logger.info(slashCount + "of " + path + ": slashes");

        ArrayList<String> pathArticles = new ArrayList<>();

        // response/blog/title
        for(int i = 0; i <= slashCount; i++) {
            if((slashCount - i) != 0) {

                int slashIndex = path.indexOf("/", startIndex);
                logger.info("Slash index: " + slashIndex);

                String pathArticle = path.substring(startIndex, slashIndex);
                logger.info("Path article: " + pathArticle);

                pathArticles.add(pathArticle);
                int newStartIndex = slashIndex + 1;
                logger.info("New start index: " + newStartIndex);

                path = path.substring(newStartIndex);
                logger.info("New string: " + path);
            } else {
                pathArticles.add(path);
            }
        }

        for(int i = 0; i < pathArticles.size(); i++) {
            logger.info("Article " + i + ": " + pathArticles.get(i));
        }

        return pathArticles;
    }

}
