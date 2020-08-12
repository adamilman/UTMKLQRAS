package com.example.utmklqras;

public class UserProfileActivity {
    public String userName;
    public String userEmail;
    public String userAge;

    public UserProfileActivity(){
    }

    public UserProfileActivity(String userAge, String userEmail, String userName) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userAge = userAge;
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

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }
}
