package com.example.a11059.udi;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.MyFragmentAdapter;
import home.Home;

import model.TakeUser;
import take.Take;
import adapter.TakeAdapter;
import user.User;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener , TabHost.OnTabChangeListener{
    private FragmentTabHost fragmentTabHost;
    private ViewPager viewPager;
    private View view;
    private    View view1;
    private List<Fragment> fragments;
    private  String [] name={"代取","发布","我的"};
    private  int []  v={R.drawable.select_item,R.drawable.select_item1,
            R.drawable.select_item2};
    private  Class [] list=new Class[3];
    private List<String> ints=new ArrayList<>();
    private RecyclerView recyclerView;
//    private List<HomeDateType> homeDateTypes=new ArrayList<>();
//    private   MyHomeAdapter myHomeAdapter;

    private  List<Bitmap> bitmaps=new ArrayList<>();
    private Home homeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置调整软件盘，隐藏背景，不进行压缩
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        viewPager= (ViewPager) findViewById(R.id.pager);
        viewPager.addOnPageChangeListener((ViewPager.OnPageChangeListener) this);
//        view= LayoutInflater.from(this).inflate(R.layout.home_fragment,null);
        fragmentTabHost= (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this,getSupportFragmentManager(),R.id.pager);



//定义四个fragement
        Class home= new Home().getClass();//每个菜单绑定一个Fragment
        Class take=new Take().getClass();
        Class user=new User().getClass();
        list[0]=user;
        list[1]=take;
        list[2]=home;

        for (int i=0;i<3;i++) {//添加四个底部菜单
            fragmentTabHost.addTab(fragmentTabHost.newTabSpec(name[i]).setIndicator(getView(i)), list[i], null);
            //给每一个Tab添加点击监听事件，TabOnClickListener是自己定义的OnclickListener
            fragmentTabHost.getTabWidget().getChildTabViewAt(i).setOnClickListener(new TabOnClickListener(fragmentTabHost,viewPager,i));
        }
        init();


    }

//    private void TakeInit() {
//
//            List<TakeUser> strings=new ArrayList<>();
//            recyclerView= (RecyclerView) findViewById(R.id.take_recycler);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
//            recyclerView.setLayoutManager(linearLayoutManager);
//        TakeAdapter takeAdapter=new TakeAdapter(strings,MainActivity.this);
//        recyclerView.setAdapter(takeAdapter);
//    }

    //实现下拉刷新功能



//    public android.os.Handler d = new android.os.Handler() {
//        @Override
//        public void handleMessage(s msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 1:
//                    homeDateTypes = (List<HomeDateType>) msg.obj;
//                    for (int i = 0; i < homeDateTypes.size(); i++) {
////                        System.out.println("545555555555555" + homeDateTypes.get(i).getText());
//                    }
//                    homeFragment.setDate(homeDateTypes);
//                    break;
//
//                case 4:
//                    List<Bitmap> bitmaps  = (List<Bitmap>) msg.obj;;
//                    homeFragment.setDate1(bitmaps);
//                    break;
//                case 2:
//                    List<String> strings= (List<String>) msg.obj;
//
//                    homeFragment.setDate2(strings);
//                    break;
//                case 3:
//                    List<String>  strings1= (List<String>) msg.obj;
//
//                    homeFragment.setDate3(strings1);
//                    break;
//                case 5:   List<String> strings2= (List<String>) msg.obj;
//                    System.out.println(strings2);
//                    homeFragment.setDate4(strings2);
//                    break;
//                default:
//                    break;
//            }
//        };
//    };

//    private void datelsit() {
//        String [] s={"1","d","d","h"};
//        MyHomeAdapter myHomeAdapter=new MyHomeAdapter(this,s);
//        RecyclerView recyclerView= (RecyclerView) LayoutInflater.from(this).inflate(R.layout.home_fragment,null).findViewById(R.id.home_recycler);
//        //这个是必须设置的，设置recyclerview的布局管理器为2列，垂直排布
////        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(myHomeAdapter);
//    }





    private View getView(int i) {//给每一个菜单设置图片和文字
        view= LayoutInflater.from(this).inflate(R.layout.tab,null);
        TextView textView;
        textView = (TextView) view.findViewById(R.id.text_shouye);
        textView.setText(name[i]);
        ImageView imageView;
        imageView= (ImageView) view.findViewById(R.id.button);
        imageView.setBackgroundResource(v[i]);//给每一个菜单设置点击效果
        return  view;
    }

    //给viewpager添加fragment，添加适配器
    private void init() {
        fragments=new ArrayList<Fragment>();
        Home home =new Home();
        Take take=new Take();
        User user=new User();

        fragments.add(take);
        fragments.add(user);
        fragments.add(home);
       System.out.println("555555555555"+fragments.size());
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(),fragments));
        fragmentTabHost.getTabWidget().setDividerDrawable(null);//取消分割线
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }



    @Override
    public void onPageSelected(int position) {//position表示当前的选中的位置，这事件是页面跳转完毕的时候调用的
        TabWidget tabWidget=fragmentTabHost.getTabWidget();
        int oldFoucsability= tabWidget.getDescendantFocusability();
        tabWidget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);//设置view覆盖子类控件而获得焦点
        fragmentTabHost.setCurrentTab(position);//根据位置position设置当前的tab
        tabWidget.setDescendantFocusability(oldFoucsability);//设置取消分割线
        if (position>4){
            viewPager.setCurrentItem(0,false);//false不显示调转画面
            position=1;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        int position=fragmentTabHost.getCurrentTab();
        viewPager.setCurrentItem(position);//把选中的Tab位置赋给适配器，让他控制页面改变
    }

    public static class CommonUtils {
        private static long lastClickTime;
        public static boolean isFastDoubleClick() {
            long time = System.currentTimeMillis();
            long timeD = time - lastClickTime;
            if ( 0 < timeD && timeD < 800) {
                return true;
            }
            lastClickTime = time;
            return false;
        }
    }
}
