package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String url = "jdbc:postgresql://localhost:5432/habrdb";
    private static final String user = "habrpguser";
    private static final String pass = "3232";
    private static Connection connection;
    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("JDBC соединение установлено.");
        }
        return connection;
    }
}
