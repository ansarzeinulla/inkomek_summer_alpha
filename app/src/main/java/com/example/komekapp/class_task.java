package com.example.komekapp;

public class class_task {
    public String timedate;
    public String inc_login,inc_name,inc_initial,inc_detail,task_title,task_description;
    public Boolean isAssigned, isClosed;
    public Integer task_points,numOfVolunteers;
    public class_task(String timedate,String inc_login,String inc_name,String inc_initial, String inc_detail,
                      String task_title,String task_description,Boolean isAssigned,Boolean isClosed,
                      Integer task_points,Integer numOfVolunteers){
        this.timedate=timedate;
        this.inc_login=inc_login;
        this.inc_name=inc_name;
        this.inc_initial=inc_initial;
        this.inc_detail=inc_detail;
        this.task_title=task_title;
        this.task_description=task_description;
        this.isAssigned=isAssigned;
        this.isClosed=isClosed;
        this.task_points=task_points;
        this.numOfVolunteers=numOfVolunteers;
    }
//    public String toString(){
//        return this.timedate+this.task_title;
//    }
}
