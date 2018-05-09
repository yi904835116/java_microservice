package handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

public class AsyncErrorHandler extends Errorhandler{
    public AsyncErrorHandler(HttpHandler handler) {
        super(handler);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        if(exchange.isInIoThread()){
            exchange.dispatch(this);
            return;
        }
        exchange.startBlocking();
        super.handleRequest(exchange);
    }

}
