package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import jm.task.core.jdbc.util.Util;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        String createTableSQL = """
        CREATE TABLE IF NOT EXISTS users (
            id SERIAL PRIMARY KEY,
            name VARCHAR(255) NOT NULL,
            lastName VARCHAR(255) NOT NULL,
            age SMALLINT
        )
        """;
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createNativeQuery(createTableSQL).executeUpdate();
            tx.commit();
            System.out.println("Таблица создана");
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Ошибка при создании таблицы.");
        }
    }

    @Override
    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS users";
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createNativeQuery(dropTableSQL).executeUpdate();
            tx.commit();
            System.out.println("Таблица удалена.");
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Ошибка при удалении таблицы.");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            tx.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных.");
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Ошибка при сохранении юзера.");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            tx.commit();
            System.out.println("Пользователь с id " + id + " удален.");
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Ошибка при удалении юзера");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            users = session.createQuery("FROM User", User.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Ошибка при попытке вызвать список юзеров");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
            tx.commit();
            System.out.println("Таблица очищена.");
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Ошибка при попытке очистки таблицы");
        }
    }
}
