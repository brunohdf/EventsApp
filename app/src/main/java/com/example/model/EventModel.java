package com.example.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class EventModel implements Parcelable {
    private int id;
    private String title;
    private String date;
    private String time;
    private String location;
    private String description;
    private List<ScheduleModel> schedule;
    private int enrolledUsers;

    public EventModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ScheduleModel> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ScheduleModel> schedule) {
        this.schedule = schedule;
    }

    public int getEnrolledUsers() {
        return enrolledUsers;
    }

    public void setEnrolledUsers(int enrolledUsers) {
        this.enrolledUsers = enrolledUsers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeString(this.location);
        dest.writeString(this.description);
        dest.writeList(this.schedule);
        dest.writeInt(this.enrolledUsers);
    }

    protected EventModel(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.date = in.readString();
        this.time = in.readString();
        this.location = in.readString();
        this.description = in.readString();
        this.schedule = new ArrayList<ScheduleModel>();
        in.readList(this.schedule, EventModel.class.getClassLoader());
        this.enrolledUsers = in.readInt();
    }

    public static final Parcelable.Creator<EventModel> CREATOR = new Parcelable.Creator<EventModel>() {
        @Override
        public EventModel createFromParcel(Parcel source) {
            return new EventModel(source);
        }

        @Override
        public EventModel[] newArray(int size) {
            return new EventModel[size];
        }
    };
}
