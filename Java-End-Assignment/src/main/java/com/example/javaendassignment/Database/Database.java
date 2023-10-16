package com.example.javaendassignment.Database;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Map<String, User> users = new HashMap<>();

    // Constructor to initialize the database with sample data
    public Database() {
        users.put("faizan", new User("Muhammad Faizan", "Manager", "12390"));
        users.put("jane", new User("Jane Smith", "Salesman", "12345"));
        users.put("john", new User("John Doe", "Accountant", "23467"));
        users.put("susan", new User("Susan Johnson", "Supervisor", "45368"));
    }

    // Method to get all users
    public Map<String, User> getAllUsers() {
        return users;
    }

    // Method to get a user by their ID
    public User getUserById(String userId) {
        return users.get(userId);
    }

    // Inner class representing a user
    public static class User {
        private String name;
        private String role;
        private String password;

        public String getPassword() {
            return password;
        }

        public User(String name, String role, String password) {
            this.name = name;
            this.role = role;
            this.password = password;
        }
    }

    public boolean isValidUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }
}
