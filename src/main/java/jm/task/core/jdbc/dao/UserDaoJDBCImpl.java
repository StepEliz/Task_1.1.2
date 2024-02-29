package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
        try {
            Util util = new Util();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() throws SQLException {
        try (Statement statement = Util.getConnection().createStatement()) {
            String customerTableQuery = "CREATE TABLE user " +
                    "(id INT AUTO_INCREMENT PRIMARY KEY, Name TEXT, LastName TEXT, Age INTEGER)";
            statement.executeUpdate(customerTableQuery);
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Таблица с таким именем уже создана");
        }

    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            String dropTableQuery = "DROP TABLE user";
            statement.executeUpdate(dropTableQuery);
        } catch (SQLException e) {
            System.out.println("Такой таблицы не существует");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = Util.getConnection().createStatement()) {
            String insertTableQuery = "INSERT INTO user(Name, LastName, Age) " +
                    "VALUES ('" + name + "', '" + lastName + "', '" + age + "')";
            statement.executeUpdate(insertTableQuery);
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = Util.getConnection().createStatement()) {
            String removeUserByIdQuery = "DELETE FROM user WHERE id = " + id;
            statement.executeUpdate(removeUserByIdQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try (Statement statement = Util.getConnection().createStatement()) {
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
        try (Statement statement = Util.getConnection().createStatement()) {
            String removeUserByIdQuery = "DELETE FROM user";
            statement.executeUpdate(removeUserByIdQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
