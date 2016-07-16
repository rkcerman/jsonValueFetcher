package main.java.com.rk.jsonvaluefetcher;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.PathNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
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
        Object value = null;
        try {
            Object document = Configuration.defaultConfiguration()
//                .addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL)
                    .jsonProvider().parse
                            (responseJson.toString());
            value = JsonPath.read(document, path);
        } catch (PathNotFoundException e) {
            logger.debug("Path not found");
            e.printStackTrace();
        }
        return value;
    }

}
