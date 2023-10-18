package com.example.javaendassignment.Model;

public class User {
    private final String fullName;
    private String name;
    private String role;
    private String password;

    public User(String name, String role, String password) {
        this.name = name;
        this.role = role;
        this.password = password;
        this.fullName = name;
    }
    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }
}