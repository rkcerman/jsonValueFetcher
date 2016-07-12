package main.java.com.rk.jsonvaluefetcher;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by rkcerman on 11.7.2016.
 */
public class Collector extends Fetcher {

    Logger logger = LoggerFactory.getLogger(Fetcher.class);

    public Collector() {

    }

    public JSONObject fetchResponse(URL url) {
        JSONObject responseJson = null;
        try {
            responseJson  = fetchJSONObject(url);
        } catch (IOException e) {
            logger.info("URL error");
            e.printStackTrace();
        }
        return responseJson;
    }

    public Object collect(JSONObject responseJson, String path) {
        ArrayList<String> pathList = parsePath(path);
        JSONObject valueJson = responseJson;
        Object value = 0;
        for(int i = 0; i < pathList.size(); i++) {
            if((pathList.size() - i) != 1) {
                logger.info("Current path: " + pathList.get(i));
                valueJson = valueJson.getJSONObject(pathList.get(i));

            } else {
                logger.info("Getting the value");
                value = valueJson.get(pathList.get(i));
            }
        }

        return value;
    }

    public ArrayList parsePath(String path) {
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
