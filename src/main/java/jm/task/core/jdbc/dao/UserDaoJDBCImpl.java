package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() throws SQLException {
        try (Connection connection = Util.getConnection();
              Statement statement = connection.createStatement()) {
            String customerTableQuery = "CREATE TABLE user " +
                    "(id INT AUTO_INCREMENT PRIMARY KEY, Name TEXT, LastName TEXT, Age INTEGER)";
            statement.executeUpdate(customerTableQuery);
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Таблица с таким именем уже создана");
        }

    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String dropTableQuery = "DROP TABLE user";
            statement.executeUpdate(dropTableQuery);
        } catch (SQLException e) {
            System.out.println("Такой таблицы не существует");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertTableQuery = "INSERT INTO user(Name, LastName, Age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertTableQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String removeUserByIdQuery = "DELETE FROM user WHERE id = ?";
        try (Connection connection = Util.getConnection();
              PreparedStatement preparedStatement = connection.prepareStatement(removeUserByIdQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String selectUserQuery = "SELECT * FROM user";
            ResultSet resultSet = statement.executeQuery(selectUserQuery);
            List<User> users = new ArrayList<>();
            while(resultSet.next()){
                User user = new User (resultSet.getString("Name"),
                        resultSet.getString("LastName"),
                        (byte) resultSet.getInt("Age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String removeUserByIdQuery = "DELETE FROM user";
            statement.executeUpdate(removeUserByIdQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
