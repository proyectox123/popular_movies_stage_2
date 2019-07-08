package com.example.android.popularmoviesstate1.data.remote.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {

    //region Constants

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    //endregion

    //region Fields

    private final String id;
    private final String author;
    private final String content;
    private final String url;

    //endregion

    //region Constructors


    public Review(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    private Review(Parcel in){
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
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
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }

    //endregion

    //region Public Methods

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }


    //endregion
}