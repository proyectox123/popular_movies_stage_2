package com.example.android.popularmoviesstate1.data.local.database.repositories;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.example.android.popularmoviesstate1.data.local.database.AppDatabase;
import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;

public class MovieRepository {

    private AppDatabase database;

    private MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();

    public MovieRepository(Application application){
        database = AppDatabase.getInstance(application);
    }

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
}