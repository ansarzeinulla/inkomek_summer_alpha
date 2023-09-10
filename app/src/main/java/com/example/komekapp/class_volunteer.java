package com.example.komekapp;

public class class_volunteer {
    public String login,name,surname,fathername,phone,email,password,detail;
    public Boolean isAnonymous;
    public int points;
    public Boolean isBlocked,isBanned;
    public int counterOfHelp;
    public class_volunteer(String login,String name,String surname,String fathername,String phone,String email,
                           String password,String detail,Boolean isAnonymous, int points,Boolean isBlocked,
                           Boolean isBanned, int counterOfHelp){
        this.login=login;
        this.name=name;
        this.surname=surname;
        this.fathername=fathername;
        this.phone=phone;
        this.email=email;
        this.password=password;
        this.detail=detail;
        this.isAnonymous=isAnonymous;
        this.points=points;
        this.isBlocked=isBlocked;
        this.isBanned=isBanned;
        this.counterOfHelp=counterOfHelp;
    }
}
