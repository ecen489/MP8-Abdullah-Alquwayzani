package com.example.mp5;

import java.io.Serializable;

public class Student implements Serializable {
    String email;
    int id;
    String name;
    String password;

    public Student() {
    }

    public Student(String email, int id, String name, String password) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
