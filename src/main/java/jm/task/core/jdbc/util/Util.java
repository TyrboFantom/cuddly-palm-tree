package jm.task.core.jdbc.util;

import com.mysql.cj.xdevapi.SessionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String URL =
            "jdbc:mysql://localhost:3306/jm_task";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            "notqwerty10";

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static SessionFactory sessionFactory;
}