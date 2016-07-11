package main.java.com.rk.jsonvaluefetcher;

import com.github.rholder.retry.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;



/**
 * Created by rkcerman on 11.7.2016.
 */
public class Fetcher {

    private static Logger logger = LoggerFactory.getLogger(Fetcher.class);
    public static String text;

    public Fetcher() {

    }

    /**
     * Obtains raw JSON from the server.
     *
     * @param url URL to fetch JSON from
     * @return text.toString Fetched JSON as a String
     * @throws IOException
     */
    public static void fetch(URL url) throws IOException {
        final int[] tryNumber = {0};

        Callable<Boolean> callable = new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                tryNumber[0]++;
                URLConnection conn = url.openConnection();
                logger.info("Opened URL connection...({})", tryNumber[0]);
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                    logger.info("Fetching text.");
                    text = reader.lines().collect(Collectors.joining("\n"));
                    logger.info("-Fetched text.");

                    return true;
                }

            }
        };

        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfExceptionOfType(IOException.class)
                .retryIfRuntimeException()
                .withWaitStrategy(WaitStrategies.fibonacciWait(100, 2, TimeUnit.MINUTES))
                .withStopStrategy(StopStrategies.neverStop())
                .build();

        try {
            retryer.call(callable);
        } catch (ExecutionException e) {
            logger.info("Could not execute the 'callable' fetch class.");
            e.printStackTrace();

        } catch (RetryException e) {
            logger.info("Connection timed out.");
            e.printStackTrace();
        }
    }

    public JSONArray fetchJSONArray(URL url) throws IOException {
        fetch(url);
        return new JSONArray(text);
    }

    public static JSONObject fetchJSONObject(URL url) throws IOException {
        fetch(url);
        return new JSONObject(text);
    }

}


