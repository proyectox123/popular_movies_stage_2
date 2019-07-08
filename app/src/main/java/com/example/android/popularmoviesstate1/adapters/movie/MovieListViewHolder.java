package com.example.android.popularmoviesstate1.adapters.movie;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.android.popularmoviesstate1.R;
import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;
import com.squareup.picasso.Picasso;

public class MovieListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    //region Fields

    private final ImageView moviePosterImageView;

    private MovieEntity movie;

    private MovieListAdapter.OnMovieListAdapterListener onMovieListAdapterListener;

    //endregion

    //region Constructors

    MovieListViewHolder(@NonNull View itemView,
                        MovieListAdapter.OnMovieListAdapterListener onMovieListAdapterListener) {
        super(itemView);

        moviePosterImageView = itemView.findViewById(R.id.moviePosterImageView);
        moviePosterImageView.setOnClickListener(this);

        this.onMovieListAdapterListener = onMovieListAdapterListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.moviePosterImageView) {
            onMovieListAdapterListener.onClickedMovieItem(movie);
        }
    }

    //endregion

    //Public Methods

    void bind(final MovieEntity movie){
        this.movie = movie;

        initMoviePoster(movie.getPosterPath());
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