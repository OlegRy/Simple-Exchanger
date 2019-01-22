package ru.olegry.simpleexchanger.data.network;

import java.net.MalformedURLException;
import java.net.URL;

class UrlHelper {

    private UrlHelper() {}

    static URL makeUrl(String baseUrl, String path) throws IllegalArgumentException {
        String urlString;
        if (path != null) {
            StringBuilder builder = new StringBuilder();
            builder.append(baseUrl);
            if (!path.startsWith("/")) builder.append("/");
            builder.append(path);
            urlString = builder.toString();
        } else {
            urlString = baseUrl;
        }
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("You should pass only url parts");
        }
    }
}
