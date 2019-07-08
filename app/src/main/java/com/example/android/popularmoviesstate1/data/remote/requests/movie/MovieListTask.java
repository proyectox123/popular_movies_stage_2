package com.example.android.popularmoviesstate1.data.remote.requests.movie;

import android.os.AsyncTask;

import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;
import com.example.android.popularmoviesstate1.data.remote.models.Movie;
import com.example.android.popularmoviesstate1.data.remote.parser.MovieListJsonUtils;
import com.example.android.popularmoviesstate1.data.translators.MovieTranslator;
import com.example.android.popularmoviesstate1.enums.MovieEnum;

import java.net.URL;
import java.util.List;

public class MovieListTask extends AsyncTask<MovieEnum, Void, List<MovieEntity>> {

    //region Fields

    private final OnMovieListTaskListener onMovieListTaskListener;

    private MovieEnum sortBy;

    //endregion

    //region Constructors

    public MovieListTask(OnMovieListTaskListener onMovieListTaskListener){
        this.onMovieListTaskListener = onMovieListTaskListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    protected List<MovieEntity> doInBackground(MovieEnum... params) {
        if (params.length > 0) {
            sortBy = params[0];
            return MovieTranslator.translateMovieList(getMovieList(sortBy));
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<MovieEntity> movieList) {
        super.onPostExecute(movieList);
        if (movieList != null && movieList.size() > 0) {
            onMovieListTaskListener.updateMovieList(sortBy, movieList);
            return;
        }

        onMovieListTaskListener.showErrorTask();
    }

    //endregion

    //region Private Methods

    private List<Movie> getMovieList(MovieEnum sortBy){
        switch (sortBy){
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

    //endregion

    //region Inner Classes & Interfaces

    public interface OnMovieListTaskListener {
        void updateMovieList(MovieEnum sortBy, List<MovieEntity> movieList);
        void showErrorTask();
    }

    //endregion
}
