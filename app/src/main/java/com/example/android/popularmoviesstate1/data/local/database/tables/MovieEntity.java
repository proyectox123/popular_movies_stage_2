package com.example.android.popularmoviesstate1.data.local.database.tables;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.popularmoviesstate1.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.android.popularmoviesstate1.utils.Constants.SIMPLE_DATE_FORMAT;

@Entity(tableName = "movie")
public class MovieEntity implements Parcelable {

    //region Fields

    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    private int id;
    @ColumnInfo(name = "movie_title")
    private String title;
    @ColumnInfo(name = "movie_release_date")
    private Date releaseDate;
    @ColumnInfo(name = "movie_poster_path")
    private String posterPath;
    @ColumnInfo(name = "movie_vote_average")
    private double voteAverage;
    @ColumnInfo(name = "movie_plot_synopsis")
    private String plotSynopsis;

    public static final Creator<MovieEntity> CREATOR = new Creator<MovieEntity>() {
        @Override
        public MovieEntity createFromParcel(Parcel in) {
            return new MovieEntity(in);
        }

        @Override
        public MovieEntity[] newArray(int size) {
            return new MovieEntity[size];
        }
    };

    //endregion

    //region Constructors

    @Ignore
    public MovieEntity(){ }

    public MovieEntity(int id, String title, Date releaseDate, String posterPath,
                       double voteAverage, String plotSynopsis) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
    }

    @Ignore
    private MovieEntity(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.releaseDate = (Date) in.readSerializable();
        this.posterPath = in.readString();
        this.voteAverage = in.readDouble();
        this.plotSynopsis = in.readString();
    }

    //endregion

    //region Public Methods

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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getReleaseDateLabel(){
        SimpleDateFormat outFormatDate = new SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.US);
        return outFormatDate.format(releaseDate);
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getVoteAverageLabel(Context context){
        String voteAverageLabel = voteAverage + "/10";
        return context.getString(R.string.text_movie_detail_vote_average, voteAverageLabel);
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeSerializable(releaseDate);
        dest.writeString(posterPath);
        dest.writeDouble(voteAverage);
        dest.writeString(plotSynopsis);
    }

    //endregion
}