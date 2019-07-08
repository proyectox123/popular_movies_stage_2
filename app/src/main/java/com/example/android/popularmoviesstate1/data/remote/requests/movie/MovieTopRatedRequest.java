package com.example.android.popularmoviesstate1.data.remote.requests.movie;

import android.net.Uri;

import com.example.android.popularmoviesstate1.BuildConfig;
import com.example.android.popularmoviesstate1.data.remote.requests.BaseMovieRequest;

public class MovieTopRatedRequest extends BaseMovieRequest {

    //region Constants

    private final static String MOVIE_TOP_RATED_URL = BASE_MOVIE_DB_URL + "movie/top_rated";

    //endregion

    //region Override Methods & Callbacks

    @Override
    public Uri createBuiltUri() {
        return Uri.parse(MOVIE_TOP_RATED_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_API_KEY, BuildConfig.MOVIE_DB_API_KEY)
                .build();
    }

    //endregion
}