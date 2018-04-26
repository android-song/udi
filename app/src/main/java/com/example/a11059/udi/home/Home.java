package com.example.a11059.udi.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a11059.udi.R;
import com.example.a11059.udi.ToolUtils;
import com.example.a11059.udi.bomb.MyUser;
import com.example.a11059.udi.databinding.PersonalDataBinding;
import com.example.a11059.udi.home.model.DataModel;
import com.example.a11059.udi.home.ui.BaseView;
import com.example.a11059.udi.notify.BaseNotify;
import com.example.a11059.udi.notify.Notification;
import com.example.a11059.udi.notify.NotificationCenter;
import com.example.a11059.udi.notify.NotificationDef;
import com.example.a11059.udi.utils.BaseFragment;


import cn.bmob.v3.BmobUser;

/**
 * Created by 11059 on 2016/11/6.
 */
public class Home extends BaseFragment implements BaseView,BaseNotify {
    private View view;
    PersonalDataBinding dataBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.persion, container, false);
            dataBinding = DataBindingUtil.bind(view);
            NotificationCenter.getNotification().register(NotificationDef.UPDATA_USERINFO, this);
            NotificationCenter.getNotification().register(NotificationDef.LOGIN_OUT_CLICK, this);
            updateUserInfo();
        }

        return dataBinding.getRoot();

    }

    @Override
    public void notify(Notification notification) {
        Log.e("demo","notify home");
        if (notification.id==NotificationDef.UPDATA_USERINFO||notification.id==NotificationDef.LOGIN_OUT_CLICK){
            updateUserInfo();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    private void updateUserInfo() {
        MyUser userInfo = ToolUtils.getUser();
        if(userInfo != null){
            // 允许用户使用应用
            Log.e("demo","允许用户使用应用"+userInfo.toString());
            if (userInfo.getImgUrl()!=null){
                Log.e("demo"," home updateUserInfo"+userInfo.getImgUrl());
                Glide.with(this).load(userInfo.getImgUrl()).into(dataBinding.persionImg);
            }
            dataBinding.persionId.setText(userInfo.getUsername());
            if (userInfo.getNick()!=null){
                dataBinding.persionId.setText(userInfo.getNick());
            }
            dataBinding.homeIn.setVisibility(View.GONE);
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            Log.e("demo","缓存用户对象为空时");
            dataBinding.persionId.setText("您还没有的登录");
            dataBinding.homeIn.setVisibility(View.VISIBLE);
            dataBinding.persionImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_user));
        }
    }





    @Override
    public void upData(DataModel dataModel) {
        Toast.makeText(getContext(),dataModel.getContent(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorUi() {

    }

    @Override
    public void inRequest() {

    }

    @Override
    public String getName() {
        return "d";

    }

    @Override
    public String getPassword() {
        return  "s";
    }

}
