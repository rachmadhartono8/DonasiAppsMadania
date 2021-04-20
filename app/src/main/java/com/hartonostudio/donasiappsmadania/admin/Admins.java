package com.hartonostudio.donasiappsmadania.admin;

public class Admins {

    public String username;
    public String email;

    public Admins() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Admins(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
