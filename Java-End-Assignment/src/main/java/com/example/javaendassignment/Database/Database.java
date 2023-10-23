package com.example.javaendassignment.Database;
import com.example.javaendassignment.Model.User;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Map<String, User> users = new HashMap<>();
    public static String currentUserUsername; // Static variable to store the currently logged-in user's username

    // Constructor to initialize the database with sample data
    public Database() {
        users.put("faizan", new User("Muhammad Faizan", "Manager", "Faizan@321"));
        users.put("tommy", new User("Tommy Shebly", "Salesperson", "Tommy@123"));
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

    public String getUserFullName(String username) {
        User user = users.get(username);
        if (user != null) {
            return user.getFullName();
        }
        return null; // Return null
    }

    public boolean isValidUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }
}
