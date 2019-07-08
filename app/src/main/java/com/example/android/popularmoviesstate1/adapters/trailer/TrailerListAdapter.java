package com.example.android.popularmoviesstate1.adapters.trailer;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.example.android.popularmoviesstate1.R;
import com.example.android.popularmoviesstate1.adapters.BaseRecyclerViewAdapter;
import com.example.android.popularmoviesstate1.data.remote.models.Trailer;

public class TrailerListAdapter extends BaseRecyclerViewAdapter<Trailer, TrailerViewHolder> {

    //region Fields

    private OnTrailerListAdapterListener onTrailerListAdapterListener;

    //endregion

    //region Constructors

    public TrailerListAdapter(OnTrailerListAdapterListener onTrailerListAdapterListener){
        this.onTrailerListAdapterListener = onTrailerListAdapterListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public int getLayoutIdForListItem() {
        return R.layout.layout_item_trailer;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TrailerViewHolder(createItemView(viewGroup), onTrailerListAdapterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder trailerViewHolder, int position) {
        trailerViewHolder.bind(getItemByPosition(position));
    }

    //endregion

    //region Inner classes & Interfaces

    public interface OnTrailerListAdapterListener {
        void onClickedTrailerItem(Trailer trailer);
    }

    //endregion
}