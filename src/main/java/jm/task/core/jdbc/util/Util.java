package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/habrdb";
        String user = "habrpguser";
        String pass = "3232";
        return DriverManager.getConnection(url, user, pass);
    }
}
