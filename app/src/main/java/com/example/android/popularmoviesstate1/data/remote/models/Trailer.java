package com.example.android.popularmoviesstate1.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Trailer implements Parcelable {

    //region Constants

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    //endregion

    //region Fields

    private final String id;
    private final String name;
    private final String thumbnail;
    private final String videoPath;

    //endregion

    //region Constructors

    public Trailer(String id, String name, String thumbnail, String videoPath) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.videoPath = videoPath;
    }

    private Trailer(Parcel in){
        this.id = in.readString();
        this.name = in.readString();
        this.thumbnail = in.readString();
        this.videoPath = in.readString();
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.thumbnail);
        dest.writeString(this.videoPath);
    }

    //endregion

    //region Public Methods

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getVideoPath() {
        return videoPath;
    }

    //endregion
}