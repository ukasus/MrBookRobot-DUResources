package com.example.duresources;

public class SignUpData {
    private String username;
    private String number;
    private String email;
    private String password;

    public SignUpData() {
    }

    public SignUpData(String username, String number, String email, String password) {
        this.username = username;
        this.number = number;
        this.email = email;
        this.password = password;
    }

    public void set_email(String email) {
        this.email = email;
    }

    public void set_username(String username) {
        this.username = username;
    }

    public void set_number(String number) {
        this.number = number;
    }

    public void set_password(String password) {
        this.password = password;
    }

    public String get_email() {
        return this.email;
    }

    public String get_password() {
        return this.password;
    }

    public String get_number() {
        return this.number;
    }

    public String get_username() {
        return this.username;
    }
}
