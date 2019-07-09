package com.example.android.popularmoviesstate1.features.moviedetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmoviesstate1.data.local.database.repositories.MovieRepository;
import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;
import com.example.android.popularmoviesstate1.data.remote.models.Review;
import com.example.android.popularmoviesstate1.data.remote.models.Trailer;
import com.example.android.popularmoviesstate1.data.remote.requests.review.ReviewListTask;
import com.example.android.popularmoviesstate1.data.remote.requests.trailer.TrailerListTask;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.popularmoviesstate1.utils.Constants.BUNDLE_MOVIE;
import static com.example.android.popularmoviesstate1.utils.Constants.BUNDLE_REVIEW_LIST;
import static com.example.android.popularmoviesstate1.utils.Constants.BUNDLE_TRAILER_LIST;
import static com.example.android.popularmoviesstate1.utils.Constants.EXTRA_MOVIE;

public class MovieDetailViewModel extends AndroidViewModel implements
        ReviewListTask.OnReviewListTaskListener,
        TrailerListTask.OnTrailerListTaskListener {

    //region Constants

    private final static String TAG = MovieDetailViewModel.class.getSimpleName();

    //endregion

    //region Fields

    private MovieDetailNavigator navigator;

    private MovieEntity movie;
    private List<Trailer> trailerList = new ArrayList<>();
    private List<Review> reviewList = new ArrayList<>();

    private LiveData<Boolean> isFavorite;

    private MutableLiveData<MovieEntity> movieData = new MutableLiveData<>();
    private MutableLiveData<List<Trailer>> trailerListData = new MutableLiveData<>();
    private MutableLiveData<List<Review>> reviewListData = new MutableLiveData<>();

    private MovieRepository movieRepository;

    //endregion

    //region Constructors

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);

        movieRepository = new MovieRepository(application);

        this.isFavorite = movieRepository.getIsFavorite();
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void updateTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;

        trailerListData.setValue(this.trailerList);
    }

    @Override
    public void showTrailerListError() {
        this.trailerList = new ArrayList<>();

        trailerListData.setValue(this.trailerList);
    }

    @Override
    public void updateReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;

        reviewListData.setValue(this.reviewList);
    }

    @Override
    public void showReviewListError() {
        this.reviewList = new ArrayList<>();

        reviewListData.setValue(this.reviewList);
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

    LiveData<Boolean> getIsFavorite() {
        return isFavorite;
    }

    //endregion

    void setNavigator(MovieDetailNavigator navigator){
        this.navigator = navigator;
    }

    void updateFavoriteMovieStatus() {
        movieRepository.updateFavoriteMovieStatus(movie);
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

    void validateInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "validateInstanceState");
        if (savedInstanceState != null) {
            trailerList = savedInstanceState.getParcelableArrayList(BUNDLE_TRAILER_LIST);
            reviewList = savedInstanceState.getParcelableArrayList(BUNDLE_REVIEW_LIST);
            movie = savedInstanceState.getParcelable(BUNDLE_MOVIE);

            if(movie != null){
                validateMovieDetailData(movie);
            }else{
                navigator.closeOnError();
            }
        }else{
            navigator.validateMovieDetailExtraData();
        }
    }

    void saveInstanceState(Bundle outState) {
        if(trailerList != null){
            outState.putParcelableArrayList(BUNDLE_TRAILER_LIST, new ArrayList<>(trailerList));
        }

        if(reviewList != null){
            outState.putParcelableArrayList(BUNDLE_REVIEW_LIST, new ArrayList<>(reviewList));
        }

        if(movie != null){
            outState.putParcelable(BUNDLE_MOVIE, movie);
        }
    }

    private void validateMovieDetailData(MovieEntity movie){
        Log.d(TAG, "validateMovieDetailData movie " + movie.toString());
        this.movie = movie;

        movieData.setValue(movie);

        validateTrailerList(movie);
        validateReviewList(movie);

        movieRepository.validateFavoriteMovieStatus(movie);
    }

    private void validateTrailerList(MovieEntity movie){
        Log.d(TAG, "validateTrailerList movie " + movie.toString());
        if(trailerList != null && trailerList.size() > 0){
            updateTrailerList(trailerList);
            return;
        }

        new TrailerListTask(this).execute(movie.getId());
    }

    private void validateReviewList(MovieEntity movie){
        Log.d(TAG, "validateReviewList movie " + movie.toString());
        if(reviewList != null && reviewList.size() > 0){
            updateReviewList(reviewList);
            return;
        }

        new ReviewListTask(this).execute(movie.getId());
    }

    //endregion
}
