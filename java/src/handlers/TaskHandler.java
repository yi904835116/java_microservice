package handlers;

import com.google.gson.Gson;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;
import models.SQLStore;
import models.Task;

import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;

public class TaskHandler implements HttpHandler {
    private SQLStore store;

    public TaskHandler(SQLStore store) {
        if(store == null){
            throw new IllegalArgumentException("mysql store cannot be null");
        }
        this.store = store;
    }


    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        if(exchange.isInIoThread()){
            exchange.dispatch(this);
            return;
        }

        String method = exchange.getRequestMethod().toString();
        if(method.equals("POST")){
            exchange.startBlocking();

            final Reader body = new InputStreamReader(exchange.getInputStream());


            Gson gson = new Gson();
            Task task = gson.fromJson(body, Task.class);
            try{
                task = this.store.insertNewTask(task.getTitle(),task.isComplete());

            }catch (SQLException e){
                throw new HttpException("error inserting task " + e.getMessage(), StatusCodes.INTERNAL_SERVER_ERROR);
            }

            exchange.getResponseHeaders().add(Headers.CONTENT_TYPE,"application/json");
        }else{
            throw new HttpException("Method not allowed", StatusCodes.METHOD_NOT_ALLOWED);
        }
    }
}
