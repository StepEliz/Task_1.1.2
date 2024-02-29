package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    static Connection connection;

    public Util() throws SQLException {

        final String URL = "jdbc:mysql://localhost:3306/mydb";
        final String USER = "root";
        final String PASSWORD = "root";

        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Не удалось зарегестрировать драйвер");
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
