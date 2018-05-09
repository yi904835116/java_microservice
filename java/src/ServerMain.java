import handlers.*;
import io.undertow.Undertow;
import io.undertow.Undertow.Builder;
import io.undertow.server.handlers.PathHandler;
import models.SQLStore;

import java.sql.SQLException;

import static io.undertow.Handlers.path;


public class ServerMain {
    public static void main(String[] args) throws SQLException {
        // Implement the main method here

        int port = Integer.parseInt(requireENV("JAVA_PORT","4000"));
        String addr = requireENV("JAVA_ADDR","0.0.0.0");

        String mysqlAddr = requireENV("JAVA_MYSQL_ADDR","");
        String mysqlDB = requireENV("JAVA_MYSQL_DB","");
        String mysqlPW = requireENV("JAVA_MYSQL_PASS","");
        String mysqlUser = requireENV("JAVA_SQL_USER","root");

        SQLStore stote = new SQLStore(mysqlAddr,mysqlUser,mysqlPW,mysqlDB);

        Builder builder = Undertow.builder();

        PathHandler mux = path();
        mux.addPrefixPath("/time",new TimeHandler());
        mux.addPrefixPath("/hello",new Errorhandler(new HelloHandler()));
        mux.addPrefixPath("/task",new AsyncErrorHandler(new TaskHandler(stote)));


        builder.addHttpListener(port,addr);

        builder.setHandler(mux);

        Undertow server = builder.build();

        System.out.printf("Server is listening on %s:%d...\n",addr,port);

        server.start();



    }

    /**
     * Gets the environment variable at "env" or returns the default value "def" if
     * the environment variable is not set.
     * @param env environment variable to get value of
     * @param def default value to use if environment variable is not set
     * @return String the value of the environment variable or the default value if not defined.
     * @throws UnsetVariableException If environment variable is not set and no default is provided.
     */
    private static String requireENV(String env, String def) throws UnsetVariableException {
        String envVal = System.getenv(env);
        if (envVal == null || envVal.length() == 0) {
            if (def.length() == 0) {
                throw new UnsetVariableException(env + " is not set and no default was provided");
            }
            return def;
        }
        return envVal;
    }
}
