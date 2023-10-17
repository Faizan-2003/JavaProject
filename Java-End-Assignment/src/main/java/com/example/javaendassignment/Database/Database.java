package com.example.javaendassignment.Database;
import com.example.javaendassignment.Model.User;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Map<String, User> users = new HashMap<>();

    // Constructor to initialize the database with sample data
    public Database() {
        users.put("faizan", new User("Muhammad Faizan", "Manager", "Faizan@321"));
        users.put("tommy", new User("Tommy Shebly", "Salesperson", "Tommy@123"));
    }

    // Method to get all users
    public Map<String, User> getAllUsers() {
        return users;
    }

    // Method to get a user by their ID
    public User getUserById(String userId) {
        return users.get(userId);
    }

    // Method to insert a new user into the database
    public void addUser(String username, User user) {
        users.put(username, user);
    }

    // Inner class representing a user

    public String getUserRole(String username) {
        User user = users.get(username);
        if (user != null) {
            return user.getRole();
        } else {
            return null; // Return null or a default role when the user is not found
        }
    }
    public boolean isValidUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }
}
