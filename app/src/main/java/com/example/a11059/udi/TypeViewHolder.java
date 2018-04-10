package com.example.a11059.udi;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 11059 on 2016/10/16.
 */
public abstract class TypeViewHolder extends RecyclerView.ViewHolder {
    public TypeViewHolder(View itemView) {
        super(itemView);
    }
    public abstract   void bindHolder();
}
