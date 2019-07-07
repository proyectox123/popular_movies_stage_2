package com.example.android.popularmoviesstate1.data.remote;

import android.os.AsyncTask;

import com.example.android.popularmoviesstate1.data.local.database.AppDatabase;
import com.example.android.popularmoviesstate1.data.local.database.converters.DateConverter;
import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;
import com.example.android.popularmoviesstate1.data.remote.models.Movie;
import com.example.android.popularmoviesstate1.data.remote.parser.MovieListJsonUtils;
import com.example.android.popularmoviesstate1.data.remote.requests.MoviePopularRequest;
import com.example.android.popularmoviesstate1.data.remote.requests.MovieTopRatedRequest;
import com.example.android.popularmoviesstate1.enums.MovieEnum;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieListTask extends AsyncTask<MovieEnum, Void, Boolean> {

    //region Fields

    private final AppDatabase database;

    private final OnMovieListTaskListener onMovieListTaskListener;

    //endregion

    //region Constructors

    public MovieListTask(AppDatabase database, OnMovieListTaskListener onMovieListTaskListener){
        this.database = database;
        this.onMovieListTaskListener = onMovieListTaskListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    protected Boolean doInBackground(MovieEnum... params) {
        if (params.length > 0) {
            List<Movie> movieList = getMovieList(params[0]);
            if (movieList != null && movieList.size() > 0) {
                List<MovieEntity> movieEntityList = translateMovieList(movieList);
                database.movieDao().insertAllMovies(movieEntityList);
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean wasSuccessful) {
        super.onPostExecute(wasSuccessful);
        if(wasSuccessful){
            return;
        }

        onMovieListTaskListener.showErrorTask();
    }

    //endregion

    //region Private Methods

    private List<Movie> getMovieList(MovieEnum movieType){
        switch (movieType){
            case POPULAR:
                return getMoviePopularList();
            case TOP_RATED:
                return getMovieTopRatedList();
            default:
                return null;
        }
    }

    private List<Movie> getMoviePopularList(){
        MoviePopularRequest moviePopular = new MoviePopularRequest();
        URL movieListRequestUrl = moviePopular.buildUrl();
        try {
            String jsonMovieListResponse = moviePopular.getResponseFromHttpUrl(movieListRequestUrl);

            return MovieListJsonUtils.getMovieListFromJson(jsonMovieListResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Movie> getMovieTopRatedList(){
        MovieTopRatedRequest moviePopular = new MovieTopRatedRequest();
        URL movieListRequestUrl = moviePopular.buildUrl();
        try {
            String jsonMovieListResponse = moviePopular.getResponseFromHttpUrl(movieListRequestUrl);

            return MovieListJsonUtils.getMovieListFromJson(jsonMovieListResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<MovieEntity> translateMovieList(List<Movie> movieList){
        List<MovieEntity> movieEntityList = new ArrayList<>();
        for(Movie movie : movieList){
            movieEntityList.add(translateMovie(movie));
        }

        return movieEntityList;
    }

    private MovieEntity translateMovie(Movie movie){
        return new MovieEntity(
                movie.getId(),
                movie.getTitle(),
                DateConverter.toDate(movie.getReleaseDate()),
                movie.getPosterPath(),
                movie.getVoteAverage(),
                movie.getPlotSynopsis()
        );
    }

    //endregion

    //region Inner Classes & Interfaces

    public interface OnMovieListTaskListener {
        //void updateMovieList(List<Movie> movieList);
        void showErrorTask();
    }

    //endregion
}
