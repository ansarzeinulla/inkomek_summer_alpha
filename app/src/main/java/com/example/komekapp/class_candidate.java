package com.example.komekapp;

public class class_candidate {
    public String login,name,surname,fathername,phone,detail;
    public Boolean isAnonymous;
    public int counterOfHelp;
    public class_candidate(String login,String name,String surname,String fathername,String phone,String detail,Boolean isAnonymous
                           , int counterOfHelp){
        this.login=login;
        this.name=name;
        this.surname=surname;
        this.fathername=fathername;
        this.phone=phone;
        this.detail=detail;
        this.isAnonymous=isAnonymous;
        this.counterOfHelp=counterOfHelp;
    }
}
