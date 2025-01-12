package jm.task.core.jdbc.dao;
import java.sql.*;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private static Connection connection;
    {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при подключении к бд");
        }
    }

    public UserDaoJDBCImpl() {}

    @Override
    public void createUsersTable() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                name VARCHAR(50) NOT NULL,
                lastName VARCHAR(50) NOT NULL,
                age INT NOT NULL
            );
        """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Таблица успешно создана.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при попытке создать таблицу.");
        }
    }

    @Override
    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS users;";
        try (Statement statement = connection.createStatement()) {
            statement.execute(dropTableSQL);
            System.out.println("Таблица удалена.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при удалении таблицы.");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String saveUserSQL = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(saveUserSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при добавлении юзера в таблицу.");
        }
    }

    @Override
    public void removeUserById(long id) {
        String removeUserSQL = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeUserSQL)) {
            preparedStatement.setLong(1, id);
            int LinesAfterStatement = preparedStatement.executeUpdate();
            if (LinesAfterStatement > 0) {
                System.out.println("Пользователь с id " + id + " удален.");
            } else {
                System.out.println("Нет пользователя с id " + id + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при удалении юзера.");
        }
    }

    @Override
    public List<User> getAllUsers() {
        String getAllUsersSQL = "SELECT * FROM users;";
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getAllUsersSQL)) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")
                );
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при попытке получении списка всех юзеров.");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String cleanUsersSQL = "TRUNCATE TABLE users";
        try (Statement statement = connection.createStatement()){
            statement.execute(cleanUsersSQL);
            System.out.println("Таблица очищена.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при попытке очистить таблицу.");
        }
    }
}
