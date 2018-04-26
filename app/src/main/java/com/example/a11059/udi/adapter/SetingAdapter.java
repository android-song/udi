package com.example.a11059.udi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a11059.udi.MainActivity;
import com.example.a11059.udi.R;

/**
 * Created by 11059 on 2016/10/22.
 */
public class SetingAdapter  extends BaseAdapter{
    private  String [] lists;
    private LinearLayout view;
    private MainActivity context;
    private  ListView listView;
    public SetingAdapter(MainActivity context, String[] lists, ListView listView) {
        this.lists=lists;
        this.context=context;
        this.listView=listView;
    }



    @Override
    public int getCount() {
        return lists.length;
    }

    @Override
    public Object getItem(int position) {
        return lists[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
//        if (convertView==null){

            viewHolder=new ViewHolder();
             convertView= LayoutInflater.from(context).inflate(R.layout.user_item,null);
             viewHolder.text= (TextView) convertView.findViewById(R.id.user_text);
//        if (position==0){
//       listView.setSelected(true);
//            listView.setItemChecked(0,true);
//            Log.i("TAG","fffffff");
//        }
//        }
//        if (position==listView.getCheckedItemPosition()){
//            viewHolder.text.setTextColor(Color.RED);
//        }else {
//            viewHolder.text.setTextColor(Color.BLUE);
//        }
        viewHolder.text.setText(lists[position]);
        return convertView;
    }
    public   class   ViewHolder{
        private TextView text;

    }
}
