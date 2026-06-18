package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" + //создать если не нашел юзера
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY," + //наш ключик
                        "name VARCHAR(255)," +
                        "lastName VARCHAR(255)," +
                        "age TINYINT)";

        try (Connection connection = Util.getConnection(); //попытка соединение дефолтыч трай кетч
             Statement statement = connection.createStatement()) { //statement вроде нужен для выполнения sql
            statement.executeUpdate(sql); //отправляем sql на сервер
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users"; //если табличка есть, сносим ее нахуй

        try (Connection connection = Util.getConnection(); //описылва выше
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(name,lastName,age) " + "VALUES(?,?,?)"; //вопросы это наши парамеетры которые указаны слева

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {//я юзал statement но чат GPT сказал что PreparedStatement лучше

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);

            statement.executeUpdate();

            System.out.println("User с именем " + name + " добавлен в базу данных");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?"; //ну прост удаляем юзера с id

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            // оч долго провозился но здесь нужен PreparedStatement, так как он сначала создает шаблон,
            // а после уже подставляет значение, а до этого отправлялись знаки вопроса и он ругался

            statement.setLong(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>(); // список куда все скидываем

        String sql = "SELECT * FROM users"; //выбрать все строки таблицы

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) { //получили табличыку

            while (resultSet.next()) { //перебираем то шо получили

                User user = new User();

                user.setId(resultSet.getLong("id"));

                user.setName(resultSet.getString("name"));

                user.setLastName(resultSet.getString("lastName"));

                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users"; //удалить

        try (Connection connection =
                     Util.getConnection();
             Statement statement =
                     connection.createStatement()) {

            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
