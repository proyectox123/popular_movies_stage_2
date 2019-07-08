package com.example.android.popularmoviesstate1.data.remote.parser;

import com.example.android.popularmoviesstate1.data.remote.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class MovieListJsonUtils {

    private final static String URL_IMAGE_TBMD = "http://image.tmdb.org/t/p/w185/";

    private final static String JSON_ARRAY_RESULTS = "results";
    private final static String JSON_OBJECT_ID = "id";
    private final static String JSON_OBJECT_TITLE = "title";
    private final static String JSON_OBJECT_RELEASE_DATE = "release_date";
    private final static String JSON_OBJECT_POSTER_PATH = "poster_path";
    private final static String JSON_OBJECT_VOTE_AVERAGE = "vote_average";
    private final static String JSON_OBJECT_OVERVIEW = "overview";

    public static List<Movie> getMovieListFromJson(String listJsonStr)
            throws JSONException {
        JSONObject listJson = new JSONObject(listJsonStr);
        JSONArray resultJsonArray = listJson.getJSONArray(JSON_ARRAY_RESULTS);

        List<Movie> movieList = new ArrayList<>();
        for(int i=0; i<resultJsonArray.length(); i++){
            JSONObject movieFromJsonObject = resultJsonArray.getJSONObject(i);
            movieList.add(getMovieFromJsonObject(movieFromJsonObject));
        }

        return movieList;
    }

    private static Movie getMovieFromJsonObject(JSONObject jsonObject) throws JSONException{
        int id = jsonObject.getInt(JSON_OBJECT_ID);
        String title = jsonObject.getString(JSON_OBJECT_TITLE);
        String releaseDate = jsonObject.getString(JSON_OBJECT_RELEASE_DATE);
        String posterPath = URL_IMAGE_TBMD + jsonObject.getString(JSON_OBJECT_POSTER_PATH);
        double voteAverage = jsonObject.getDouble(JSON_OBJECT_VOTE_AVERAGE);
        String plotSynopsis = jsonObject.getString(JSON_OBJECT_OVERVIEW);

        return new Movie(id, title, releaseDate, posterPath, voteAverage, plotSynopsis);
    }
}