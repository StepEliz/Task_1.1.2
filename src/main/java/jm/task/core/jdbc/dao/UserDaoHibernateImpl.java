package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory;
    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        String customerTableQuery = "CREATE TABLE User (id INT AUTO_INCREMENT PRIMARY KEY, Name TEXT, LastName TEXT, Age INTEGER)";
        try (Session session = sessionFactory.openSession()) {
            Transaction txn = session.beginTransaction();
            session.createSQLQuery(customerTableQuery).executeUpdate();
            txn.commit();
        } catch (Exception e) {
            System.out.println("Таблица с таким именем уже существует");
        }
    }

    @Override
    public void dropUsersTable() {
        String customerTableQuery = "DROP TABLE User";
        try (Session session = sessionFactory.openSession()) {
            Transaction txn = session.beginTransaction();
            session.createSQLQuery(customerTableQuery).executeUpdate();
            txn.commit();
        } catch (Exception e) {
            System.out.println("Такой таблицы не существует");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction txn = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            txn.commit();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction txn = session.beginTransaction();
            session.delete(session.get(User.class, id));
            txn.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            String query = "SELECT user FROM User user";
            return session.createQuery(query, User.class).getResultList();
        }

    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction txn = session.beginTransaction();
            String sql = "DELETE FROM User";
            session.createQuery(sql).executeUpdate();
            txn.commit();
        }
    }

    public void closeSessionFactory() {
        sessionFactory.close();
    }
}
