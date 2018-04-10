package com.example.a11059.udi;

import android.content.DialogInterface;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by 我 on 2016/9/19.
 */
public class TabOnClickListener implements View.OnClickListener {
    private FragmentTabHost fragmentTabHost;
    private  int index;
    private  ViewPager viewPager;
   public  TabOnClickListener(FragmentTabHost fragmentTabHost, ViewPager viewPager, int index){
        this.fragmentTabHost=fragmentTabHost;
       this.index=index;
       this.viewPager=viewPager;
    }
    @Override
    public void onClick(View v) {
//        for (int i=0;i<fragmentTabHost.getTabWidget().getTabCount();i++){
//            View view=fragmentTabHost.getTabWidget().getChildTabViewAt(i);
//            viewPager.setCurrentTab(index);
//        }
        viewPager.setCurrentItem(index);//设置点击事件的跳转，viewpager的子fragment
    }
}
