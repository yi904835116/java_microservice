package handlers;

import io.undertow.io.Sender;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;

import java.util.Deque;
import java.util.Map;

public class HelloHandler implements HttpHandler {


    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

        exchange.getResponseHeaders().add(Headers.CONTENT_TYPE,"text/plain");
        exchange.setStatusCode(StatusCodes.OK);

        Sender rs = exchange.getResponseSender();

        Map<String,Deque<String>> queries = exchange.getQueryParameters();

        Deque<String> names = queries.get("name");
        if(names != null && names.peek().length() > 0){
            String name = names.pop();
            rs.send("hello" + name);
        }else{
            throw new HttpException("query string name must exist", StatusCodes.BAD_REQUEST);

        }

//        exchange.getResponseSender().send("hello world");
    }
}
