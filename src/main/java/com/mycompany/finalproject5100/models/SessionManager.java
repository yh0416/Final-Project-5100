package com.mycompany.finalproject5100.models;

/**
 * Singleton class to manage user session
 */
public class SessionManager {
    private static SessionManager instance;
    private User currentUser;
    
    private SessionManager() {
        // Private constructor to enforce singleton pattern
    }
    
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public String getUserRole() {
        return currentUser != null ? currentUser.getRole() : "";
    }
    
    public void clearSession() {
        currentUser = null;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}