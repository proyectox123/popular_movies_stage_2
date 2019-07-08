package com.example.android.popularmoviesstate1.data.remote.requests;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public abstract class BaseMovieRequest {

    private final static String TAG = BaseMovieRequest.class.getSimpleName();

    protected final static String BASE_MOVIE_DB_URL = "https://api.themoviedb.org/3/";
    protected final static String QUERY_PARAMETER_API_KEY = "api_key";

    protected abstract Uri createBuiltUri();

    public URL buildUrl() {
        URL url = null;
        try {
            url = new URL(createBuiltUri().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}