package com.example.a11059.udi.view_interface;

import com.example.a11059.udi.bomb.MyUser;

/**
 * Created by Administrator on 2018/4/19.
 */

public interface LoginCallback {
    void loginSuccess(MyUser myUser);
    void loginFail(String toast);
    void registerFail(String toast);
    void registerSuccess(MyUser myUser);
}
