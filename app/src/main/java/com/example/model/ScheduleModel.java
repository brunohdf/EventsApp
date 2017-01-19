package com.example.model;


import android.os.Parcel;
import android.os.Parcelable;

public class ScheduleModel implements Parcelable {
    private String title;
    private String presenter;

    public ScheduleModel() {

    }

    public ScheduleModel(String title, String presenter) {
        this.title = title;
        this.presenter = presenter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPresenter() {
        return presenter;
    }

    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.presenter);
    }

    protected ScheduleModel(Parcel in) {
        this.title = in.readString();
        this.presenter = in.readString();
    }

    public static final Parcelable.Creator<ScheduleModel> CREATOR = new Parcelable.Creator<ScheduleModel>() {
        @Override
        public ScheduleModel createFromParcel(Parcel source) {
            return new ScheduleModel(source);
        }

        @Override
        public ScheduleModel[] newArray(int size) {
            return new ScheduleModel[size];
        }
    };
}
