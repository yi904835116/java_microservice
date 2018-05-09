package handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

public class Errorhandler implements HttpHandler {

    private  HttpHandler handler;

    public Errorhandler(HttpHandler handler){
        if(handler == null){
            throw new IllegalArgumentException("handler cannot be null");
        }
        this.handler = handler;
    }
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        try{
            this.handler.handleRequest(exchange);
        }catch (HttpException e){
            exchange.setStatusCode(e.getStatus());
            exchange.getResponseSender().send(e.getMessage());
        }catch(Exception e){
            exchange.setStatusCode(StatusCodes.INTERNAL_SERVER_ERROR);
            exchange.getResponseSender().send(e.getMessage());
        }

    }
}
