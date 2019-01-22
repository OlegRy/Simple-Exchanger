package ru.olegry.simpleexchanger.data.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestHelper {

    private static final int DEFAULT_TIMEOUT = 5_000;

    private String baseUrl;

    public RequestHelper(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String makeGetRequest(String path) throws IOException {
        BufferedReader reader = null;
        String result = null;
        try {
            URL url = UrlHelper.makeUrl(baseUrl, path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(DEFAULT_TIMEOUT);
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            result = builder.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }
}
