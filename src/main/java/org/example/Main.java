package org.example;

import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.model.User;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final UserDao userDao = new UserDaoImpl();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n=== USER SERVICE ===");
            System.out.println("1. Create User");
            System.out.println("2. Find a user by ID");
            System.out.println("3. Show all users");
            System.out.println("4. Update user");
            System.out.println("5. Delete user");
            System.out.println("0. Exit");
            System.out.print("Enter your choice : ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> createUser();
                case 2 -> readUser();
                case 3 -> readAllUsers();
                case 4 -> updateUser();
                case 5 -> deleteUser();
                case 0 -> {
                    System.out.println("Exit...");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void createUser() {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Age: ");
        int age = sc.nextInt();
        sc.nextLine();

        User user = new User(name, email, age);
        userDao.create(user);
        System.out.println("User created" + user);
    }

    private static void readUser() {
        System.out.print("ID: ");
        Long id = sc.nextLong();
        sc.nextLine();

        User user = userDao.read(id);
        System.out.println(user != null ? user : "User not found");
    }

    private static void readAllUsers() {
        List<User> users = userDao.readAll();
        users.forEach(System.out::println);
    }

    private static void updateUser() {
        System.out.print("ID for update: ");
        Long id = sc.nextLong();
        sc.nextLine();

        User user = userDao.read(id);
        if (user == null) {
            System.out.println("User not found");
            return;
        }

        System.out.print("New name: (" +  user.getName() + "): ");
        String name =  sc.nextLine();
        if (!name.isEmpty()) user.setName(name);

        System.out.print("New email: (" +  user.getEmail() + "): ");
        String email = sc.nextLine();
        if (!email.isEmpty()) user.setEmail(email);

        System.out.print("New age: (" +  user.getAge() + "): ");
        String ageStr = sc.nextLine();
        if (!ageStr.isEmpty()) user.setAge(Integer.parseInt(ageStr));

        userDao.update(user);
        System.out.println("User updated" + user);
    }

    private static void deleteUser() {
        System.out.print("ID for delete: ");
        Long id = sc.nextLong();
        sc.nextLine();

        userDao.delete(id);
        System.out.println("User deleted");
    }
}