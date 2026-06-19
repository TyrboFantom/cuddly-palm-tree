package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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

    private static SessionFactory sessionFactory;

    //это для 1 задачки
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

    // это для Hibernate
    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {

            try {

                Configuration configuration =
                        new Configuration();

                configuration.setProperty(
                        "hibernate.connection.driver_class",
                        "com.mysql.cj.jdbc.Driver");

                configuration.setProperty(
                        "hibernate.connection.url",
                        URL);

                configuration.setProperty(
                        "hibernate.connection.username",
                        USER);

                configuration.setProperty(
                        "hibernate.connection.password",
                        PASSWORD);

                configuration.setProperty(
                        "hibernate.dialect",
                        "org.hibernate.dialect.MySQL8Dialect");

                configuration.setProperty(
                        "hibernate.show_sql",
                        "true");

                configuration.addAnnotatedClass(User.class);

                sessionFactory =
                        configuration.buildSessionFactory();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }
}