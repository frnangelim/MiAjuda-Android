package com.grupogtd.es20182.monitoriasufcg.service.domain;

/**
 * Created by francisco on 27/09/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Course {

    @SerializedName("_id")
    @Expose
    private String _id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("teacherId")
    @Expose
    private User teacher;

    @SerializedName("students")
    @Expose

    private ArrayList<String> students;
    @SerializedName("monitors")
    @Expose
    private ArrayList<String> monitors;

    public Course(String name, User teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<String> students) {
        this.students = students;
    }

    public ArrayList<String> getMonitors() {
        return monitors;
    }

    public void setMonitors(ArrayList<String> monitors) {
        this.monitors = monitors;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }
}
