package com.example.android.popularmoviesstate1.data.remote.requests.review;

import android.net.Uri;

import com.example.android.popularmoviesstate1.BuildConfig;
import com.example.android.popularmoviesstate1.data.remote.requests.BaseMovieRequest;

public class ReviewRequest extends BaseMovieRequest {

    //region Constants

    private final static String MOVIE_REVIEW_URL = BASE_MOVIE_DB_URL + "movie/";
    private final static String MOVIE_REVIEW_PATH = "/reviews";

    //endregion

    //region Fields

    private int movieId;

    //endregion

    //region Constructors

    ReviewRequest(int movieId){
        this.movieId = movieId;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public Uri createBuiltUri() {
        String movieTrailerUrl = MOVIE_REVIEW_URL + movieId + MOVIE_REVIEW_PATH;

        return Uri.parse(movieTrailerUrl).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_API_KEY, BuildConfig.MOVIE_DB_API_KEY)
                .build();
    }

    //endregion
}