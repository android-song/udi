package com.example.a11059.udi.home.model;

import com.example.a11059.udi.home.parsenter.Listener;

/**
 * Created by 11059 on 2017/3/13.
 */
public interface StrogeModel {
    void showUi(Listener listener);
    void  request(String name, String password, Listener listener);
}
