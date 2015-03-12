package com.nikhil.sugar.sugar_demo;

import com.orm.SugarRecord;

/**
 * Created by Nikhil Sharma on 026-26-02.
 */
public class User extends SugarRecord<User> {
    private String name;
    private String password;
    private String email;
    private Integer age;

    public User() {

    }

    public User(String name, Integer age, String email, String password) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
