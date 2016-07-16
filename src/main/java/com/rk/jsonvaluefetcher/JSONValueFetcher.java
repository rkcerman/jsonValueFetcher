package main.java.com.rk.jsonvaluefetcher;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static spark.Spark.*;

/**
 * Created by rkcerman on 11.7.2016.
 */
public class JSONValueFetcher extends Fetcher {

    static Logger logger = LoggerFactory.getLogger(JSONValueFetcher.class);

    public static void main(String[] args) {
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        Configuration freeMarkerConfiguration = new Configuration();
        freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(JSONValueFetcher.class, "/"));
        freeMarkerEngine.setConfiguration(freeMarkerConfiguration);

        get("/jsonfetch", (req, res) -> {
            res.status(200);
            res.type("text/html");
            Map<String, Object> map = new HashMap<>();
            map.put("message", "MESSAGE!!!");
            return freeMarkerEngine.render(new ModelAndView(map, "home.ftl"));
            });
        app();
    }

    public static void app() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name of the csv file: ");
        String fileName = scanner.next();

        CSVhandler csVhandler = new CSVhandler(fileName);
        csVhandler.handle();

        scanner.close();
    }

}
