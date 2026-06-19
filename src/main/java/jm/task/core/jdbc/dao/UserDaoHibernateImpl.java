package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {

        Transaction transaction = null;

        try (Session session =
                     Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.createSQLQuery(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                            "name VARCHAR(255)," +
                            "lastName VARCHAR(255)," +
                            "age TINYINT)"
            ).executeUpdate();

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {

        Transaction transaction = null;

        try (Session session =
                     Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.createSQLQuery(
                    "DROP TABLE IF EXISTS users"
            ).executeUpdate();

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name,
                         String lastName,
                         byte age) {

        Transaction transaction = null;

        try (Session session =
                     Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            User user =
                    new User(
                            name,
                            lastName,
                            age
                    );

            session.save(user);

            transaction.commit();

            System.out.println(
                    "User с именем "
                            + name
                            + " добавлен в базу данных"
            );

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;

        try (Session session =
                     Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            User user =
                    session.get(
                            User.class,
                            id
                    );

            if (user != null) {
                session.delete(user);
            }

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {

        try (Session session =
                     Util.getSessionFactory().openSession()) {

            return session
                    .createQuery(
                            "from User",
                            User.class
                    )
                    .getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public void cleanUsersTable() {

        Transaction transaction = null;

        try (Session session =
                     Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.createQuery(
                    "delete User"
            ).executeUpdate();

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }
}