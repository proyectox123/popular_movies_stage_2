package com.example.android.popularmoviesstate1.features.moviedetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;
import com.example.android.popularmoviesstate1.data.remote.models.Review;
import com.example.android.popularmoviesstate1.data.remote.models.Trailer;
import com.example.android.popularmoviesstate1.data.remote.requests.review.ReviewListTask;
import com.example.android.popularmoviesstate1.data.remote.requests.trailer.TrailerListTask;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.popularmoviesstate1.utils.Constants.EXTRA_MOVIE;

public class MovieDetailViewModel extends AndroidViewModel implements
        ReviewListTask.OnReviewListTaskListener,
        TrailerListTask.OnTrailerListTaskListener {

    //region Constants

    private final static String TAG = MovieDetailViewModel.class.getSimpleName();

    //endregion

    //region Fields

    private MovieDetailNavigator navigator;

    private MutableLiveData<MovieEntity> movieData = new MutableLiveData<>();
    private MutableLiveData<List<Trailer>> trailerListData = new MutableLiveData<>();
    private MutableLiveData<List<Review>> reviewListData = new MutableLiveData<>();

    //endregion

    //region Constructors

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void updateTrailerList(List<Trailer> movieList) {
        trailerListData.setValue(movieList);
    }

    @Override
    public void showTrailerListError() {
        trailerListData.setValue(new ArrayList<Trailer>());
    }

    @Override
    public void updateReviewList(List<Review> reviewList) {
        reviewListData.setValue(reviewList);
    }

    @Override
    public void showReviewListError() {
        reviewListData.setValue(new ArrayList<Review>());
    }

    //endregion

    //region Private Methods

    //region LiveData Private Getters

    MutableLiveData<MovieEntity> getMovieData() {
        return movieData;
    }

    MutableLiveData<List<Trailer>> getTrailerListData() {
        return trailerListData;
    }

    MutableLiveData<List<Review>> getReviewListData() {
        return reviewListData;
    }

    //endregion

    void setNavigator(MovieDetailNavigator navigator){
        this.navigator = navigator;
    }

    void validateMovieDetailExtraData(Intent intent){
        Log.d(TAG, "validateMovieDetailExtraData");
        if (intent == null) {
            Log.d(TAG, "validateMovieDetailExtraData closeOnError");
            navigator.closeOnError();
            return;
        }

        MovieEntity movie = intent.getParcelableExtra(EXTRA_MOVIE);
        if (movie == null) {
            Log.d(TAG, "validateMovieDetailExtraData closeOnError");
            navigator.closeOnError();
            return;
        }

        validateMovieDetailData(movie);
    }

    private void validateMovieDetailData(MovieEntity movie){
        Log.d(TAG, "validateMovieDetailData movie " + movie.toString());
        movieData.setValue(movie);

        new TrailerListTask(this).execute(movie.getId());
        new ReviewListTask(this).execute(movie.getId());
    }

    //endregion
}
