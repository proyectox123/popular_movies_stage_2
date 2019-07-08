package com.example.android.popularmoviesstate1.adapters.movie;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.example.android.popularmoviesstate1.R;
import com.example.android.popularmoviesstate1.adapters.BaseRecyclerViewAdapter;
import com.example.android.popularmoviesstate1.data.local.database.tables.MovieEntity;

public class MovieListAdapter extends BaseRecyclerViewAdapter<MovieEntity, MovieViewHolder> {

    //region Fields

    private final OnMovieListAdapterListener onMovieListAdapterListener;

    //endregion

    //region Constructors

    public MovieListAdapter(OnMovieListAdapterListener onMovieListAdapterListener){
        this.onMovieListAdapterListener = onMovieListAdapterListener;
    }

    //endregion

    //region Override Methods & Callbacks

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MovieViewHolder(createItemView(viewGroup), onMovieListAdapterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieListViewHolder, int position) {
        movieListViewHolder.bind(getItemByPosition(position));
    }

    @LayoutRes
    @Override
    public int getLayoutIdForListItem(){
        return R.layout.layout_item_movie_poster;
    }

    //region Inner classes & Interfaces

    public interface OnMovieListAdapterListener {
        void onClickedMovieItem(MovieEntity movie);
    }

    //endregion
}

