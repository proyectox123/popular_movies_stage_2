package com.example.android.popularmoviesstate1.data.remote.requests.movie;

import android.net.Uri;

import com.example.android.popularmoviesstate1.BuildConfig;
import com.example.android.popularmoviesstate1.data.remote.requests.BaseMovieRequest;

public class MoviePopularRequest extends BaseMovieRequest {

    //region Constants

    private final static String MOVIE_POPULAR_URL = BASE_MOVIE_DB_URL + "movie/popular";

    //endregion

    //region Override Methods & Callbacks

    @Override
    public Uri createBuiltUri() {
        return Uri.parse(MOVIE_POPULAR_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_API_KEY, BuildConfig.MOVIE_DB_API_KEY)
                .build();
    }

    //endregion
}