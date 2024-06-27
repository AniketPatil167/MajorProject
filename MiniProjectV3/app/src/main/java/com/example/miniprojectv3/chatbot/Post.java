package com.example.miniprojectv3.chatbot;


import java.util.List;

public class Post {

    private String username;
    private String message;

    public Post(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
