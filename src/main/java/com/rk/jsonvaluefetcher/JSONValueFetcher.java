package main.java.com.rk.jsonvaluefetcher;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static spark.Spark.*;

/**
 * Created by rkcerman on 11.7.2016.
 */
public class JSONValueFetcher extends Fetcher {

    private static String fName = null;
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

        post("/upload", "multipart/form-data", (req, res) -> {

            String location = "";          // the directory location where files will
            // be stored
            long maxFileSize = 100000000;       // the maximum size allowed for
            // uploaded files
            long maxRequestSize = 100000000;    // the maximum size allowed for multipart/form-data requests
            int fileSizeThreshold = 1024;       // the size threshold after which files will be written to disk

            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                    location, maxFileSize, maxRequestSize, fileSizeThreshold);
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                    multipartConfigElement);

            Collection<Part> parts = req.raw().getParts();
            for (Part part : parts) {
                System.out.println("Name: " + part.getName());
                System.out.println("Size: " + part.getSize());
                System.out.println("Filename: " + part.getSubmittedFileName());
            }

            fName = req.raw().getPart("file").getSubmittedFileName();
            UUID uuid = UUID.randomUUID();
            fName = fName.replace(".csv", uuid + ".csv");

            System.out.println("File: " + fName);

            Part uploadedFile = req.raw().getPart("file");
            Path out = Paths.get("" + fName);
            try (final InputStream in = uploadedFile.getInputStream()) {
                Files.copy(in, out);
                uploadedFile.delete();
            }
            multipartConfigElement = null;
            parts = null;
            uploadedFile = null;

            app();

//            Map<String, Object> map = new HashMap<>();
//            map.put("dlink", fName);
//            return freeMarkerEngine.render(new ModelAndView(map, "download.ftl"));
            return "OK";
        });

    }

    public static void app() {

/*        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name of the csv file: ");
        String fileName = scanner.next();*/

        CSVhandler csVhandler = new CSVhandler(fName);
        csVhandler.handle();

//        scanner.close();
    }

}
