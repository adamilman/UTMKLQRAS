package com.example.utmklqras;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.security.acl.LastOwnerException;

public class UserProfileActivity {
    public String userEmail;
    public String userType;
    public String userPassword;
    public String userName;
    public String userMatric;
    private String Status;
    private LayoutInflater inflater;
    private Context context;

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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String toString(){
        return userEmail + " \n" + userName + " \n" + userMatric + " \n" + userPassword + " \n" + userType;
    }
}
