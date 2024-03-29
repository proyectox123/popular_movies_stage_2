package com.example.android.popularmoviesstate1.features.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.popularmoviesstate1.R;
import com.example.android.popularmoviesstate1.data.local.database.AppDatabase;
import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;
import com.example.android.popularmoviesstate1.data.remote.requests.movie.MovieListTask;
import com.example.android.popularmoviesstate1.enums.MovieEnum;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.popularmoviesstate1.utils.Constants.BUNDLE_MOVIE_FAVORITE_LIST;
import static com.example.android.popularmoviesstate1.utils.Constants.BUNDLE_MOVIE_POPULAR_LIST;
import static com.example.android.popularmoviesstate1.utils.Constants.BUNDLE_MOVIE_TOP_RATED_LIST;
import static com.example.android.popularmoviesstate1.utils.Constants.BUNDLE_SORT_BY;

public class MainViewModel extends AndroidViewModel implements MovieListTask.OnMovieListTaskListener{

    //region Constants

    private final static String TAG = MainViewModel.class.getSimpleName();

    private final MutableLiveData<List<MovieEntity>> movieListData = new MutableLiveData<>();

    private final AppDatabase database;

    //endregion

    //region Fields

    private MovieEnum sortBy = MovieEnum.TOP_RATED;

    private List<MovieEntity> moviePopularList = new ArrayList<>();
    private List<MovieEntity> movieTopRatedList = new ArrayList<>();
    private List<MovieEntity> movieFavoriteList = new ArrayList<>();

    private LiveData<List<MovieEntity>> favoriteMovieListData;

    private MainNavigator navigator;

    //endregion

    //region Constructors

    public MainViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getInstance(this.getApplication());
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void updateMovieList(MovieEnum sortBy, List<MovieEntity> movieList) {
        switch (sortBy){
            case TOP_RATED:
                movieTopRatedList = movieList;
                break;
            case POPULAR:
                moviePopularList = movieList;
                break;
            case FAVORITE:
                movieFavoriteList = movieList;
                break;
        }

        movieListData.setValue(movieList);
    }

    @Override
    public void showErrorTask() {
        navigator.showErrorMessage();
    }

    //endregion

    //region Private Methods

    //region LiveData Private Getters

    LiveData<List<MovieEntity>> getMovieList() {
        return movieListData;
    }

    LiveData<List<MovieEntity>> getFavoriteMovieListData() {
        if(favoriteMovieListData == null) {
            favoriteMovieListData = new MutableLiveData<>();
            favoriteMovieListData = database.movieDao().loadAllMovies();
        }

        return favoriteMovieListData;
    }

    //endregion

    void setNavigator(MainNavigator navigator){
        this.navigator = navigator;
    }

    void validateInstanceState(@Nullable Bundle savedInstanceState){
        Log.d(TAG, "validateInstanceState");
        if (savedInstanceState != null) {
            MovieEnum sortBySaved = (MovieEnum) savedInstanceState.get(BUNDLE_SORT_BY);
            if(sortBySaved == null){
                sortBySaved = MovieEnum.TOP_RATED;
            }

            validateSortByCurrentAction(sortBySaved);

            List<MovieEntity> movieSavedList = null;
            switch (sortBySaved){
                case TOP_RATED:
                    movieSavedList = savedInstanceState.getParcelableArrayList(BUNDLE_MOVIE_TOP_RATED_LIST);
                    break;
                case POPULAR:
                    movieSavedList = savedInstanceState.getParcelableArrayList(BUNDLE_MOVIE_POPULAR_LIST);
                    break;
                case FAVORITE:
                    movieSavedList = savedInstanceState.getParcelableArrayList(BUNDLE_MOVIE_FAVORITE_LIST);
                    break;
            }

            validateSavedMovieList(movieSavedList);
        } else {
            initMovieList();
        }
    }

    private void validateSortByCurrentAction(MovieEnum sortBy){
        int menuItemId;
        switch (sortBy){
            case FAVORITE:
                menuItemId = R.id.menu_movie_favorites;
                break;
            case POPULAR:
                menuItemId = R.id.menu_movie_most_popular;
                break;
            default:
                menuItemId = R.id.menu_movie_highest_rated;
                break;
        }

        navigator.updateMenuItemChecked(menuItemId);
    }

    void validateMenuItemSelected() {
        validateSortByCurrentAction(sortBy);
    }

    void initMovieList() {
        Log.d(TAG, "initMovieList sortBy " + sortBy);
        navigator.showProgressBar();

        validateSortByCurrentAction(sortBy);
        if(sortBy == MovieEnum.FAVORITE){
            List<MovieEntity> favoriteMovieList = getFavoriteMovieListData().getValue();
            updateMovieList(favoriteMovieList);
        }else{
            new MovieListTask(this).execute(sortBy);
        }
    }

    void updateFavoriteMovieList(List<MovieEntity> movieList) {
        movieFavoriteList = movieList;
        if(sortBy == MovieEnum.FAVORITE){
            updateMovieList(movieList);
        }
    }

    void selectMovieMostPopularOption(){
        sortBy = MovieEnum.POPULAR;
        initMovieList();
    }

    void selectMovieHighestRatedOption(){
        sortBy = MovieEnum.TOP_RATED;
        initMovieList();
    }

    void selectMovieFavoriteOption(){
        sortBy = MovieEnum.FAVORITE;
        initMovieList();
    }

    void saveInstanceState(Bundle outState) {
        if(moviePopularList != null){
            outState.putParcelableArrayList(BUNDLE_MOVIE_POPULAR_LIST, new ArrayList<>(moviePopularList));
        }

        if(movieTopRatedList != null){
            outState.putParcelableArrayList(BUNDLE_MOVIE_TOP_RATED_LIST, new ArrayList<>(movieTopRatedList));
        }

        if(movieFavoriteList != null){
            outState.putParcelableArrayList(BUNDLE_MOVIE_FAVORITE_LIST, new ArrayList<>(movieFavoriteList));
        }

        outState.putSerializable(BUNDLE_SORT_BY, sortBy);
    }

    private void validateSavedMovieList(@Nullable List<MovieEntity> movieSavedList){
        Log.d(TAG, "validateSavedMovieList");
        Log.d(TAG, "validateSavedMovieList sortBy " + sortBy);
        if(movieSavedList != null){
            List<MovieEntity> movieList = new ArrayList<>(movieSavedList);
            if(movieList.size() > 0){
                Log.d(TAG, "validateSavedMovieList update liveData");
                movieListData.setValue(movieList);
                return;
            }
        }

        initMovieList();
    }

    private void updateMovieList(List<MovieEntity> movieList) {
        updateMovieList(sortBy, movieList);
    }

    //endregion
}