package com.example.android.popularmoviesstate1.data.remote.requests.trailer;

import android.os.AsyncTask;

import com.example.android.popularmoviesstate1.data.remote.models.Trailer;
import com.example.android.popularmoviesstate1.data.remote.parser.TrailerListJsonUtils;

import java.net.URL;
import java.util.List;

public class TrailerListTask extends AsyncTask<Integer, Void, List<Trailer>> {

    //region Fields

    private final OnTrailerListTaskListener onTrailerListTaskListener;

    //endregion

    //region Constructors

    public TrailerListTask(OnTrailerListTaskListener onTrailerListTaskListener){
        this.onTrailerListTaskListener = onTrailerListTaskListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    protected List<Trailer> doInBackground(Integer... params) {
        if (params.length > 0 && params[0] != null) {
            TrailerRequest trailerRequest = new TrailerRequest(params[0]);
            URL trailerListRequestUrl = trailerRequest.buildUrl();
            try {
                String jsonTrailerListResponse = trailerRequest.getResponseFromHttpUrl(trailerListRequestUrl);

                return TrailerListJsonUtils.getTrailerListFromJson(jsonTrailerListResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Trailer> trailerList) {
        super.onPostExecute(trailerList);
        if (trailerList != null && trailerList.size() > 0) {
            onTrailerListTaskListener.updateTrailerList(trailerList);
            return;
        }

        onTrailerListTaskListener.showTrailerListError();
    }

    //endregion

    //region Inner Classes & Interfaces

    public interface OnTrailerListTaskListener {
        void updateTrailerList(List<Trailer> movieList);
        void showTrailerListError();
    }

    //endregion
}
