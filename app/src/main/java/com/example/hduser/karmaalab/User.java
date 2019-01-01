package com.example.hduser.karmaalab;

public class User {

    private int token;
    private String username ;

    public User(int token) {
        this.token = token;
        this.username = username;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
