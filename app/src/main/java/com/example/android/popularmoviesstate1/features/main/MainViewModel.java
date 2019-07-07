package com.example.android.popularmoviesstate1.features.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.popularmoviesstate1.data.local.database.AppDatabase;
import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;
import com.example.android.popularmoviesstate1.data.remote.MovieListTask;
import com.example.android.popularmoviesstate1.enums.MovieEnum;

import java.util.List;

public class MainViewModel extends AndroidViewModel implements MovieListTask.OnMovieListTaskListener{

    //region Fields

    private AppDatabase database;

    private LiveData<List<MovieEntity>> movieListData;

    private MainNavigator navigator;

    //endregion

    //region Constructors

    public MainViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getInstance(this.getApplication());
        movieListData = database.movieDao().loadAllMovies();
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void showErrorTask() {
        navigator.showErrorMessage();
    }

    //endregion

    //region Public Methods

    //region LiveData Public Getters

    public LiveData<List<MovieEntity>> getMovieList() {
        return movieListData;
    }

    //endregion

    //region Presenter Methods

    public void setNavigator(MainNavigator navigator){
        this.navigator = navigator;
    }

    public void initMovieList(MovieEnum movieType) {
        navigator.showProgressBar();

        new MovieListTask(database, this).execute(movieType);
    }

    //endregion

    //endregion
}