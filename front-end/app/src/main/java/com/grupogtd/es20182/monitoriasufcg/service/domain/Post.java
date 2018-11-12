package com.grupogtd.es20182.monitoriasufcg.service.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by francisco on 11/11/18.
 */

public class Post implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String _id;

    @SerializedName("author")
    @Expose
    private User author;

    @SerializedName("class")
    @Expose
    private Course course;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("text_body")
    @Expose
    private String textBody;

    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Post(Parcel in) {
        _id = in.readString();
        author = (User) in.readParcelable(User.class.getClassLoader());
        course = (Course) in.readParcelable(Course.class.getClassLoader());
        title = in.readString();
        textBody = in.readString();
        createdAt = (java.util.Date) in.readSerializable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeParcelable(author, flags);
        dest.writeParcelable(course, flags);
        dest.writeString(title);
        dest.writeString(textBody);
        dest.writeSerializable(createdAt);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (_id != null ? !_id.equals(post._id) : post._id != null) return false;
        if (author != null ? !author.equals(post.author) : post.author != null) return false;
        return createdAt != null ? createdAt.equals(post.createdAt) : post.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
