package com.example.utmklqras;

public class UserProfile {
    public String Age;
    public String Email;
    public String Name;

    public UserProfile(){

    }

    public UserProfile(String userAge, String userEmail, String userName) {
        this.Age = userAge;
        this.Email = userEmail;
        this.Name = userName;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
