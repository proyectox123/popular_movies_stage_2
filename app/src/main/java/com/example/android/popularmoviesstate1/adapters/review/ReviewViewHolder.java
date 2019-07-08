package com.example.android.popularmoviesstate1.adapters.review;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.android.popularmoviesstate1.R;
import com.example.android.popularmoviesstate1.adapters.BaseViewHolder;
import com.example.android.popularmoviesstate1.data.remote.models.Review;

public class ReviewViewHolder extends BaseViewHolder<Review> {

    //region Fields

    private final TextView reviewAuthorTextView;
    private final TextView reviewContentTextView;
    private final View reviewContent;

    private ReviewListAdapter.OnReviewListAdapterListener onReviewListAdapterListener;

    //endregion

    //region Constructors

    public ReviewViewHolder(@NonNull View itemView,
                            ReviewListAdapter.OnReviewListAdapterListener onReviewListAdapterListener) {
        super(itemView);

        reviewAuthorTextView = itemView.findViewById(R.id.reviewAuthorTextView);
        reviewContentTextView = itemView.findViewById(R.id.reviewContentTextView);
        reviewContent = itemView.findViewById(R.id.reviewContent);

        this.onReviewListAdapterListener = onReviewListAdapterListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void bind(final Review review) {
        reviewAuthorTextView.setText(review.getAuthor());
        reviewContentTextView.setText(review.getContent());

        reviewContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReviewListAdapterListener.onClickedReviewItem(review);
            }
        });
    }

    //endregion
}