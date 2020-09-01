package com.example.utmklqras;

public class UserProfileActivity {
    public String userEmail;
    public String userType;
    public String userPassword;
    public String userName;
    public String userMatric;

    public UserProfileActivity(){
    }

    public UserProfileActivity(String userEmail, String userType, String userPassword, String userName, String userMatric) {
        this.userEmail = userEmail;
        this.userType = userType;
        this.userPassword = userPassword;
        this.userName = userName;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    //@Override
    //public String toString(){
    //    return userEmail + " \n" + userName + " \n" + userMatric + " \n" + userPassword + " \n" + userType;
    //}
}
