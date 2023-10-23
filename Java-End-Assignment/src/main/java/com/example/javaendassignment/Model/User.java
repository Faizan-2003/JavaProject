package com.example.javaendassignment.Model;

import java.io.Serializable;

public class User implements Serializable {
    private final String fullName;
    private String name;
    private String password;
    private UserRole role;

    public User(String name, UserRole role, String password) {
        this.name = name;
        this.password = password;
        this.fullName = name;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public UserRole getRole() {return role;}

}