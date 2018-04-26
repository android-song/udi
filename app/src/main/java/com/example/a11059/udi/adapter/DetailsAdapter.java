package com.example.a11059.udi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a11059.udi.DetailsActivity;

import java.util.List;

/**
 * Created by 11059 on 2016/10/22.
 */
public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private  String [] lists;
    private LinearLayout view;
    private DetailsActivity context;
    private  ListView listView;
    private List<String> list;
    public DetailsAdapter(DetailsActivity context, String[] lists, RecyclerView listView, List<String> strings) {
        this.lists=lists;
        this.list=strings;
        this.context=context;
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public   class   ViewHolder{
        private TextView text;
        private TextView editText;
    }
}
