package com.example.android.popularmoviesstate1.data.remote.parser;

import com.example.android.popularmoviesstate1.data.remote.models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrailerListJsonUtils {

    private final static String URL_THUMBNAIL_YOUTUBE_VIDEO = "http://img.youtube.com/vi/";
    private final static String URL_THUMBNAIL_YOUTUBE_VIDEO_PATH_DEFAULT = "/hqdefault.jpg";
    private final static String URL_YOUTUBE_VIDEO = "https://www.youtube.com/watch?v=";

    private final static String JSON_ARRAY_RESULTS = "results";
    private final static String JSON_OBJECT_ID = "id";
    private final static String JSON_OBJECT_KEY = "key";
    private final static String JSON_OBJECT_NAME = "name";

    public static List<Trailer> getTrailerListFromJson(String listJsonStr)
            throws JSONException {
        JSONObject listJson = new JSONObject(listJsonStr);
        JSONArray resultJsonArray = listJson.getJSONArray(JSON_ARRAY_RESULTS);

        List<Trailer> trailerList = new ArrayList<>();
        for(int i=0; i<resultJsonArray.length(); i++){
            JSONObject trailerFromJsonObject = resultJsonArray.getJSONObject(i);
            trailerList.add(getTrailerFromJsonObject(trailerFromJsonObject));
        }

        return trailerList;
    }

    private static Trailer getTrailerFromJsonObject(JSONObject jsonObject) throws JSONException{
        String id = jsonObject.getString(JSON_OBJECT_ID);
        String name =  jsonObject.getString(JSON_OBJECT_NAME);

        String key = jsonObject.getString(JSON_OBJECT_KEY);
        String videoThumbnail = URL_THUMBNAIL_YOUTUBE_VIDEO + key + URL_THUMBNAIL_YOUTUBE_VIDEO_PATH_DEFAULT;
        String videoPath = URL_YOUTUBE_VIDEO + key;

        return new Trailer(id, name, videoThumbnail, videoPath);
    }
}