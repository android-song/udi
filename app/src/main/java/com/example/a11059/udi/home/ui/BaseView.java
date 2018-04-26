package com.example.a11059.udi.home.ui;

import com.example.a11059.udi.home.model.DataModel;

/**
 * Created by 11059 on 2017/3/13.
 */
public interface BaseView {
    void upData(DataModel dataModel);
    void errorUi();
    void  inRequest();
    String getName();
    String getPassword();
}
