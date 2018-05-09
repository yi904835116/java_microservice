package models;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;

public class SQLStore {

    private Connection conn;

    public SQLStore(String addr, String user,String password,String db) throws IllegalArgumentException,SQLException {

        String[] splitAddr = addr.split(":");

        int port = Integer.parseInt(splitAddr[1]);

        String addr2 = splitAddr[0];

        MysqlDataSource dsn = new MysqlDataSource();
        dsn.setUser(user);
        dsn.setDatabaseName(db);
        dsn.setPassword(password);
        dsn.setServerName(addr2);
        dsn.setPort(port);

        this.conn = dsn.getConnection();
    }

    public  Task insertNewTask(String title, boolean complete) throws IllegalArgumentException,SQLException{
        PreparedStatement stmt = this.conn.prepareStatement("INSERT INTO task(title, complete) VALUES  (? , ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1,title);
        stmt.setBoolean(2,complete);
        stmt.execute();


        int id = 0;
        ResultSet rs = stmt.getGeneratedKeys();

        if(rs.next()){
            id = rs.getInt(1);

        }

        return new Task(id,title,complete);
    }
}
