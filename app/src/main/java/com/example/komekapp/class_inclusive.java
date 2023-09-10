package com.example.komekapp;

import com.google.android.material.slider.BaseOnChangeListener;

public class class_inclusive {
    public String login,name,initial,phone,email,password;
    public Boolean isApproved;
    public int category,points;
    public String detail;
    public Boolean isBlocked,isBanned;
    public class_inclusive(String login, String name, String initial, String phone, String email, String password,
                           String detail, Boolean isApproved, int category, int points, Boolean isBlocked, Boolean isBanned){
        this.login=login;
        this.name=name;
        this.initial=initial;
        this.phone=phone;
        this.email=email;
        this.password=password;
        this.detail=detail;
        this.isApproved=isApproved;
        this.category=category;
        this.points=points;
        this.isBlocked=isBlocked;
        this.isBanned=isBanned;
    }
}
