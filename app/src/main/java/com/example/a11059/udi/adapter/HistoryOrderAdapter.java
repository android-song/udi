package com.example.a11059.udi.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.a11059.udi.R;
import com.example.a11059.udi.databinding.HistoryItemBinding;
import com.example.a11059.udi.databinding.ItemBinding;
import com.example.a11059.udi.home.model.UserItem;

import java.util.List;

/**
 * Created by Administrator on 2018/4/9.
 */

public  class HistoryOrderAdapter extends BaseAdapter {
    List<UserItem> userList;
    Context context;




    public HistoryOrderAdapter(List<UserItem> userItems, Context baseContext) {

        userList= userItems;
        this.context=baseContext;
//        userList.addOnListChangedCallback(mDataListener);
    }
    public void  addData(List<UserItem> userItem){
        userList=userItem;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public UserItem getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder=new MyViewHolder();
        if (convertView==null){
          convertView= LayoutInflater.from(context).inflate(R.layout.history_order_item,parent,false);
          myViewHolder.itemBinding=DataBindingUtil.bind(convertView);
          convertView.setTag(myViewHolder);
        }else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.itemBinding.setUser(getItem(position));
        return convertView;
    }

    private class  MyViewHolder  {
        HistoryItemBinding itemBinding;

    }
}
