package com.example.android.popularmoviesstate1.data.remote.requests.trailer;

import android.net.Uri;

import com.example.android.popularmoviesstate1.BuildConfig;
import com.example.android.popularmoviesstate1.data.remote.requests.BaseMovieRequest;

public class TrailerRequest extends BaseMovieRequest {

    //region Constants

    private final static String MOVIE_TRAILER_URL = BASE_MOVIE_DB_URL + "movie/";
    private final static String MOVIE_TRAILER_VIDEO_PATH = "/videos";

    //endregion

    //region Fields

    private final int movieId;

    //endregion

    //region Constructors

    TrailerRequest(int movieId){
        this.movieId = movieId;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public Uri createBuiltUri() {
        String movieTrailerUrl = MOVIE_TRAILER_URL + movieId + MOVIE_TRAILER_VIDEO_PATH;

        return Uri.parse(movieTrailerUrl).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_API_KEY, BuildConfig.MOVIE_DB_API_KEY)
                .build();
    }

    //endregion
}