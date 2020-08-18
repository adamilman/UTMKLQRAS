package com.example.utmklqras;

public class UserProfileActivity {
    public String userName;
    public String userEmail;
    public String userMatric;

    public UserProfileActivity(){
    }

    public UserProfileActivity(String userMatric, String userEmail, String userName) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userMatric = userMatric;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMatric() {
        return userMatric;
    }

    public void setUserMatric(String userMatric) {
        this.userMatric = userMatric;
    }
}
