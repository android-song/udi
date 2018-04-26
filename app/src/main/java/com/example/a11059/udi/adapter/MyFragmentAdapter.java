package com.example.a11059.udi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 我 on 2016/9/19.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter{
    private  List<Fragment> list;
    public MyFragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list=list;
    }

/*
* 解决viewpager中fragment的数据重复加载
* 方法一： 继承FragmentPagerAdapter， 重写destoryItem方法，去掉supper();
* 方法二：第二种解决方案：继承PagerAdapter，重写destroyItem（）和instantiateItem（）方法
*
 * public Object instantiateItem(ViewGroup container, int position) {
          Fragment fragment = fragments.get(position);
          if (!fragment.isAdded()) {
               FragmentTransaction transaction = manager.beginTransaction();
               transaction.add(fragment, fragment.getClass().getSimpleName());
               transaction.commitAllowingStateLoss();
               manager.executePendingTransactions();
          }
          if (fragment.getView().getParent() == null) {
               container.addView(fragment.getView());
          }
          return fragment.getView();
     }
*
*
*
*
* */
//    重写destoryItem移除item
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView(list.get(position).getView());
//        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
