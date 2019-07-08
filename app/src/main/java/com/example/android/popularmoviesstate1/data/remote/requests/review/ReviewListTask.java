package com.example.android.popularmoviesstate1.data.remote.requests.review;

import android.os.AsyncTask;

import com.example.android.popularmoviesstate1.data.remote.models.Review;
import com.example.android.popularmoviesstate1.data.remote.parser.ReviewListJsonUtils;

import java.net.URL;
import java.util.List;

public class ReviewListTask extends AsyncTask<Integer, Void, List<Review>> {

    //region Fields

    private final OnReviewListTaskListener onReviewListTaskListener;

    //endregion

    //region Constructors

    public ReviewListTask(OnReviewListTaskListener onReviewListTaskListener){
        this.onReviewListTaskListener = onReviewListTaskListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    protected List<Review> doInBackground(Integer... params) {
        if (params.length > 0 && params[0] != null) {
            ReviewRequest reviewRequest = new ReviewRequest(params[0]);
            URL reviewListRequestUrl = reviewRequest.buildUrl();
            try {
                String jsonReviewListResponse = reviewRequest.getResponseFromHttpUrl(reviewListRequestUrl);

                return ReviewListJsonUtils.getReviewListFromJson(jsonReviewListResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Review> reviewList) {
        super.onPostExecute(reviewList);
        if (reviewList != null && reviewList.size() > 0) {
            onReviewListTaskListener.updateReviewList(reviewList);
            return;
        }

        onReviewListTaskListener.showReviewListError();
    }

    //endregion

    //region Inner Classes & Interfaces

    public interface OnReviewListTaskListener {
        void updateReviewList(List<Review> reviewList);
        void showReviewListError();
    }

    //endregion
}
