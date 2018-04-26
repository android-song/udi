package com.example.a11059.udi.adapter;

import android.database.DataSetObserver;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.example.a11059.udi.MainActivity;
import com.example.a11059.udi.R;

/**
 * Created by 11059 on 2016/11/23.
 */
public class ExpandableAdapter implements ExpandableListAdapter {
private  String [] group;
    private String [][] child;
    private MainActivity mainActivity;
    private TextView textView;
    public ExpandableAdapter(String[] s, String[][] data, FragmentActivity activity) {
        this.group=s;
        this.child=data;
        this.mainActivity= (MainActivity) activity;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return group.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.length;
    }


    //获取group数据
    @Override
    public Object getGroup(int groupPosition) {
        return group[groupPosition];
    }


    //每个组里的成员
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
           view= LayoutInflater.from(mainActivity).inflate(R.layout.take_tab,parent,false);
          textView= (TextView) view.findViewById(R.id.take_text);
        textView.setText(group[groupPosition]);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        View view;
        view= LayoutInflater.from(mainActivity).inflate(R.layout.take_tab,parent,false);
        textView= (TextView) view.findViewById(R.id.take_text);
        System.out.println("g"+groupPosition+"c"+childPosition);
//        textView.setText( getChild(groupPosition,childPosition).toString());
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
}
