package com.mattsteban.checkyoself.models;

/**
 * Created by Esteban on 8/1/16.
 */
public class User {

    public String id;
    public boolean isOnline;
    public String name;
    public String email;
    public String photoURL;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String name, String id, boolean isOnline, String photoURL) {
        this.email = email;
        this.name = name;
        this.id = id;
        this.isOnline = isOnline;
        this.photoURL = photoURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
