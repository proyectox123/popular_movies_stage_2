package com.example.android.popularmoviesstate1.data.local.database.repositories;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.example.android.popularmoviesstate1.data.local.database.AppDatabase;
import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    //region Constants

    private final AppDatabase database;

    private final MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();
    private final MutableLiveData<List<MovieEntity>> favoriteMovieListData = new MutableLiveData<>();

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

    public MutableLiveData<List<MovieEntity>> getFavoriteMovieListData() {
        return favoriteMovieListData;
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

    public void readFavoriteMovieList() {
        List<MovieEntity> favoriteMovieList = database.movieDao().loadAllMovies();
        if(favoriteMovieList == null){
            favoriteMovieList = new ArrayList<>();
        }

        favoriteMovieListData.setValue(favoriteMovieList);
    }

    //endregion
}