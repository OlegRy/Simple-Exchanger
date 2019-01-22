package ru.olegry.simpleexchanger.data.network;

import ru.olegry.simpleexchanger.models.Response;

public class NetworkManager {

    private final RequestHelper requestHelper;
    private final Parser parser;

    private NetworkManager(RequestHelper requestHelper, Parser parser) {
        this.requestHelper = requestHelper;
        this.parser = parser;
    }

    public Response currencies() throws Exception {
        final String responseString = requestHelper.makeGetRequest("scripts/XML_daily.asp");
        if (responseString != null) {
            return parser.parse(responseString, Response.class);
        }
        return null;
    }

    public static class Builder {

        private RequestHelper requestHelper;
        private Parser parser;

        public Builder withRequestHelper(RequestHelper requestHelper) {
            this.requestHelper = requestHelper;
            return this;
        }

        public Builder withParser(Parser parser) {
            this.parser = parser;
            return this;
        }

        public NetworkManager build() {
            return new NetworkManager(requestHelper, parser);
        }
    }
}
