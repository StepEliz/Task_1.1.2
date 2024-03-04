package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Иван", "Сидоров", (byte) 10);
        userService.saveUser("Анна", "Иванова", (byte) 26);
        userService.saveUser("Анатолий", "Степанов", (byte) 43);
        userService.saveUser("Екатерина", "Попова", (byte) 12);
        List<User> users = userService.getAllUsers();
        users.forEach(i -> System.out.println(i.toString()));
        userService.cleanUsersTable();
        userService.dropUsersTable();
        userService.closeSessionFactory();
    }
}
