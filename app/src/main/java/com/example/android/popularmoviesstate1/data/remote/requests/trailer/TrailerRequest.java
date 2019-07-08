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

    private int movieId;

    //endregion

    //region Constructors

    public TrailerRequest(int movieId){
        this.movieId = movieId;
    }

    //endregion

    //region Override Methods & Callbacks

    /*
    https://api.themoviedb.org/3/movie/299534/videos?api_key=4dabe23583a36b836ccd9044465fc287
     */

    @Override
    public Uri createBuiltUri() {
        String movieTrailerUrl = MOVIE_TRAILER_URL + movieId + MOVIE_TRAILER_VIDEO_PATH;

        return Uri.parse(movieTrailerUrl).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_API_KEY, BuildConfig.MOVIE_DB_API_KEY)
                .build();
    }

    //endregion
}