package com.example.android.popularmoviesstate1.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    //region Constructors

    protected BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    //endregion

    //region Abstract Methods

    public abstract void bind(T item);

    //endregion
}
