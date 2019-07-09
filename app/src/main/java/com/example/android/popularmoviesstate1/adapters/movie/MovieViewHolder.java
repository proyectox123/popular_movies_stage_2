package com.example.android.popularmoviesstate1.adapters.movie;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.example.android.popularmoviesstate1.R;
import com.example.android.popularmoviesstate1.adapters.BaseViewHolder;
import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;
import com.squareup.picasso.Picasso;

public class MovieViewHolder extends BaseViewHolder<MovieEntity> {

    //region Constants

    private final MovieListAdapter.OnMovieListAdapterListener onMovieListAdapterListener;

    //endregion

    //region Fields

    private final ImageView moviePosterImageView;

    //endregion

    //region Constructors

    MovieViewHolder(@NonNull View itemView,
                    MovieListAdapter.OnMovieListAdapterListener onMovieListAdapterListener) {
        super(itemView);

        moviePosterImageView = itemView.findViewById(R.id.moviePosterImageView);

        this.onMovieListAdapterListener = onMovieListAdapterListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void bind(final MovieEntity movie){
        initMoviePoster(movie.getPosterPath());

        moviePosterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMovieListAdapterListener.onClickedMovieItem(movie);
            }
        });
    }

    //endregion

    //Private Methods

    private void initMoviePoster(String moviePosterPath){
        Picasso.get()
                .load(moviePosterPath)
                .placeholder(R.drawable.ic_movie_black_48dp)
                .error(R.drawable.ic_movie_black_48dp)
                .into(moviePosterImageView);
    }

    //endregion
}