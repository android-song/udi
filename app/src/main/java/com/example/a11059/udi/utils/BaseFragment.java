package com.example.a11059.udi.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a11059.udi.MyApplication;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Administrator on 2018/4/18.
 */

public abstract class BaseFragment extends Fragment {
    public String TAG="BaseFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    public void toast(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
    public  void log(String s){
        Log.d(TAG,""+s);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("BaseFragment", "onDestroy!");
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
