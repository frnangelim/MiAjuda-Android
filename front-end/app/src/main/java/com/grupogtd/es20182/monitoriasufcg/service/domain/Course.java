package com.grupogtd.es20182.monitoriasufcg.service.domain;

/**
 * Created by francisco on 27/09/18.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Course implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String _id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("schedule")
    @Expose
    private String schedule;

    @SerializedName("number")
    @Expose
    private String roomNumber;

    @SerializedName("semester")
    @Expose
    private String semester;

    @SerializedName("office_hours")
    @Expose
    private String office_hours;

    @SerializedName("info")
    @Expose
    private String info;

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

    public String getSchedule() {
        return schedule;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getSemester() {
        return semester;
    }

    public String getOffice_hours() {
        return office_hours;
    }

    public String getInfo() {
        return info;
    }


    public Course(Parcel in) {
        _id = in.readString();
        name = in.readString();
        schedule = in.readString();
        roomNumber = in.readString();
        semester = in.readString();
        office_hours = in.readString();
        info = in.readString();
        teacher = (User) in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(schedule);
        dest.writeString(roomNumber);
        dest.writeString(semester);
        dest.writeString(office_hours);
        dest.writeString(info);
        dest.writeParcelable(teacher, flags);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (_id != null ? !_id.equals(course._id) : course._id != null) return false;
        if (name != null ? !name.equals(course.name) : course.name != null) return false;
        return teacher != null ? teacher.equals(course.teacher) : course.teacher == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        return result;
    }
}
