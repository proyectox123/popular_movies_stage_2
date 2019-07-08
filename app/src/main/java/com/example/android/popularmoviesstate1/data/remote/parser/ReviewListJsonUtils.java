package com.example.android.popularmoviesstate1.data.remote.parser;

import com.example.android.popularmoviesstate1.data.remote.models.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReviewListJsonUtils {

    private final static String JSON_ARRAY_RESULTS = "results";
    private final static String JSON_OBJECT_ID = "id";
    private final static String JSON_OBJECT_AUTHOR = "author";
    private final static String JSON_OBJECT_CONTENT = "content";
    private final static String JSON_OBJECT_URL = "url";

    public static List<Review> getReviewListFromJson(String listJsonStr)
            throws JSONException {
        JSONObject listJson = new JSONObject(listJsonStr);
        JSONArray resultJsonArray = listJson.getJSONArray(JSON_ARRAY_RESULTS);

        List<Review> reviewList = new ArrayList<>();
        for(int i=0; i<resultJsonArray.length(); i++){
            JSONObject reviewFromJsonObject = resultJsonArray.getJSONObject(i);
            reviewList.add(getReviewFromJsonObject(reviewFromJsonObject));
        }

        return reviewList;
    }

    private static Review getReviewFromJsonObject(JSONObject jsonObject) throws JSONException{
        String id = jsonObject.getString(JSON_OBJECT_ID);
        String author =  jsonObject.getString(JSON_OBJECT_AUTHOR);
        String content =  jsonObject.getString(JSON_OBJECT_CONTENT);
        String url =  jsonObject.getString(JSON_OBJECT_URL);

        return new Review(id, author, content, url);
    }
}