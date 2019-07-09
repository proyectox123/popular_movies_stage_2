package com.example.android.popularmoviesstate1.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, VH extends BaseViewHolder<T>>
        extends RecyclerView.Adapter<VH>{

    //region Fields

    private List<T> itemList = new ArrayList<>();

    //endregion

    //region Constructors

    protected BaseRecyclerViewAdapter(){ }

    //endregion

    //region Abstract Methods

    protected abstract int getLayoutIdForListItem();

    //endregion

    //region Override Methods & Callbacks

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    //endregion

    //region Public Methods

    public void setList(List<T> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public void clearList(){
        if(this.itemList == null){
            this.itemList = new ArrayList<>();
        }else{
            this.itemList.clear();
        }

        notifyDataSetChanged();
    }

    //endregion

    //region Private Methods

    protected T getItemByPosition(int position){
        return itemList.get(position);
    }

    protected View createItemView(@NonNull ViewGroup viewGroup){
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        return inflater.inflate(getLayoutIdForListItem(), viewGroup, false);
    }

    //endregion

}