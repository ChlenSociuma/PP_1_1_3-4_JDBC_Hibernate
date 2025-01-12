package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.hibernate.SessionFactory;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;


public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        User user = new User("Alex", "Popov", (byte) 18);
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        User user2 = new User("Popo", "Nepopov", (byte) 40);
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        User user3 = new User("Nick", "Nicov", (byte) 25);
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        User user4 = new User("Mike", "Vazovskiy", (byte) 20);
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        List<User> users = userService.getAllUsers();
        users.stream().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();



    }

}
