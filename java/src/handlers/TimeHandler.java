package handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;

import java.util.Date;

public class TimeHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        Date date = new Date();
        exchange.getResponseHeaders().add(Headers.CONTENT_TYPE,"text/plain");
        exchange.setStatusCode(StatusCodes.OK);
        exchange.getResponseSender().send(date.toString());

    }
}
