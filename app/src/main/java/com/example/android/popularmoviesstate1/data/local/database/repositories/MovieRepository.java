package com.example.android.popularmoviesstate1.data.local.database.repositories;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.example.android.popularmoviesstate1.data.local.database.AppDatabase;
import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;

public class MovieRepository {

    //region Constants

    private final AppDatabase database;

    private final MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();

    //endregion

    //region Constructors

    public MovieRepository(Application application){
        database = AppDatabase.getInstance(application);
    }

    //endregion

    //region Public Methods

    public MutableLiveData<Boolean> getIsFavorite() {
        return isFavorite;
    }

    public void validateFavoriteMovieStatus(MovieEntity movieEntity){
        int movieId = movieEntity.getId();

        MovieEntity movie = database.movieDao().loadMovieById(movieId);
        if(movie == null){
            isFavorite.setValue(false);
        }else{
            isFavorite.setValue(true);
        }
    }

    public void updateFavoriteMovieStatus(MovieEntity movieEntity){
        int movieId = movieEntity.getId();

        MovieEntity movie = database.movieDao().loadMovieById(movieId);
        if(movie == null){
            database.movieDao().insertMovie(movieEntity);
            isFavorite.setValue(true);
        }else{
            database.movieDao().deleteMovie(movieEntity);
            isFavorite.setValue(false);
        }
    }

    //endregion
}