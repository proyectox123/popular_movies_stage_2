package com.example.android.popularmoviesstate1.adapters.review;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.example.android.popularmoviesstate1.R;
import com.example.android.popularmoviesstate1.adapters.BaseRecyclerViewAdapter;
import com.example.android.popularmoviesstate1.data.remote.models.Review;

public class ReviewListAdapter extends BaseRecyclerViewAdapter<Review, ReviewViewHolder> {

    //region Fields

    private final OnReviewListAdapterListener onReviewListAdapterListener;

    //endregion

    //region Constructors

    public ReviewListAdapter(OnReviewListAdapterListener onReviewListAdapterListener){
        this.onReviewListAdapterListener = onReviewListAdapterListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ReviewViewHolder(createItemView(viewGroup), onReviewListAdapterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewListViewHolder, int position) {
        reviewListViewHolder.bind(getItemByPosition(position));
    }

    @LayoutRes
    @Override
    public int getLayoutIdForListItem(){
        return R.layout.layout_item_review;
    }

    //region Inner classes & Interfaces

    public interface OnReviewListAdapterListener {
        void onClickedReviewItem(Review review);
    }

    //endregion
}

