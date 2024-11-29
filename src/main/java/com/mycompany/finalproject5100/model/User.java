package com.mycompany.finalproject5100.model;

public class User {
    private String username;
    private String password;
    private String role;
    private String address;

    public User(String username, String password, String role, String address) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.address = address;
    }

    // Add a second constructor for backward compatibility if needed
    public User(String username, String password, String role) {
        this(username, password, role, ""); // Default empty address
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    //validate the password
    public boolean validatePassword(String inputPassword){
        return this.password.equals(inputPassword);
    }
}