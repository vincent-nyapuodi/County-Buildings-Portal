package com.project.buildingapp.utils;

import android.util.Patterns;

import java.util.regex.Pattern;

public class ValidationsClass {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z]).{6,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(?=.*[0-9]).{10,10}$");

    private String password, email, phoneno, username;

    // Mpesa phone no class
    public void setPhoneNo(String phoneNo) {
        this.phoneno = phoneNo;
    }

    public int validatePhoneNo() {
        int validphone = 0;
        if (!this.phoneno.isEmpty()){
            if (this.phoneno.length() == 10){
                if (!PHONE_PATTERN.matcher(this.phoneno).matches()){
                    validphone = 2; // Only numbers
                } else {
                    validphone = 3;     // successful
                }
            } else {
                validphone = 1;     // length ten
            }
        } else {
            validphone = 0; // Fill text fields
        }
        return validphone;
    }

    // Edit profile
    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getUsername() {
        boolean check = false;
        if (this.username.isEmpty()) {
            check = false;
        } else {
            check = true;
        }
        return check;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int validateEmail() {
        int validemail = 0;
        if (!this.email.isEmpty()) {
            if (!Patterns.EMAIL_ADDRESS.matcher(this.email).matches()) {
                validemail = 1; // invalid email
            } else {
                validemail = 2;
            }
        } else {
            validemail = 0; // empty
        }
        return validemail;
    }

    // Change password
    public void setPassword(String password) {
        this.password = password;
    }

    public int validatePassword() {
        int validpassword = 0;

        if (!this.password.isEmpty()) {
            if (this.password.length() < 6) {
                validpassword = 1; // less than 6
            } else {
                if (!PASSWORD_PATTERN.matcher(this.password).matches()) {
                    validpassword = 2; // at least one number and one letter
                } else {
                    validpassword = 3; // successfull
                }
            }
        } else {
            validpassword = 0; // empty password
        }
        return validpassword;
    }

}
