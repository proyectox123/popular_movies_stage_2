package com.example.android.popularmoviesstate1.adapters.trailer;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.example.android.popularmoviesstate1.R;
import com.example.android.popularmoviesstate1.adapters.BaseViewHolder;
import com.example.android.popularmoviesstate1.data.remote.models.Trailer;
import com.squareup.picasso.Picasso;

public class TrailerViewHolder extends BaseViewHolder<Trailer> {

    //region Fields

    private final ImageView movieTrailerImageView;

    private final TrailerListAdapter.OnTrailerListAdapterListener onTrailerListAdapterListener;

    //endregion

    //region Constructors

    TrailerViewHolder(@NonNull View itemView,
                      TrailerListAdapter.OnTrailerListAdapterListener onTrailerListAdapterListener) {
        super(itemView);

        movieTrailerImageView = itemView.findViewById(R.id.movieTrailerImageView);

        this.onTrailerListAdapterListener = onTrailerListAdapterListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void bind(final Trailer trailer) {
        initTrailerThumbnail(trailer.getThumbnail());

        movieTrailerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTrailerListAdapterListener.onClickedTrailerItem(trailer);
            }
        });
    }

    //endregion

    //Private Methods

    private void initTrailerThumbnail(String thumbnail){
        Picasso.get()
                .load(thumbnail)
                .placeholder(R.drawable.ic_play_circle_filled)
                .error(R.drawable.ic_play_circle_filled)
                .into(movieTrailerImageView);
    }

    //endregion
}