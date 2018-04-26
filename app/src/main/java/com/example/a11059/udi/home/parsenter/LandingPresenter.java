package com.example.a11059.udi.home.parsenter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.util.Log;

import com.example.a11059.udi.bomb.MyUser;
import com.example.a11059.udi.home.Landing;
import com.example.a11059.udi.model.LoginModel;
import com.example.a11059.udi.view_interface.LoginCallback;
import com.example.a11059.udi.view_interface.OnItemClickInterface;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Administrator on 2018/4/19.
 */

public class LandingPresenter  implements LoginCallback{
      LoginModel loginModel;
      Landing landing;
    public LandingPresenter(Landing landing) {
        if (loginModel==null) {
            loginModel = new LoginModel(this);
        }
        this.landing=landing;
    }


    public void login(String number, String password) {
        loginModel.login(number,password);
    }

    public void register(String number, String password) {
        loginModel.register(number,password);
    }

    public void forgetPassword() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void loginWays(String way) {
          if (way.equals("phone")){
              landing.showPhoneRegister();
          } else if (way.equals("password")) {
              landing.showPasswordRegister();
          }
    }


    @Override
    public void loginSuccess(MyUser myUser) {
        landing.loginSuccess(myUser);
    }

    @Override
    public void loginFail(String toast) {
            landing.loginFail(toast);
    }

    @Override
    public void registerFail(String toast) {
            landing.registerFail(toast);
    }

    @Override
    public void registerSuccess(MyUser myUser) {
            landing.registerSuccess(myUser);
    }

    public void getVerification(String text) {
        BmobSMS.requestSMSCode(text,"模板名称", new QueryListener<Integer>() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void done(Integer smsId,BmobException ex) {
                if(ex==null){//验证码发送成功
                    Log.i("smile", "短信id："+smsId);//用于后续的查询本次短信发送状态
                    landing.VerificationSuccess("验证码发送成功");
                }
            }
        });
    }

    public void verificationLogin(String text, String text1) {

        MyUser.loginBySMSCode(text, text1, new LogInListener<MyUser>() {

            @Override
            public void done(MyUser user, BmobException e) {
                if(user!=null){
                    Log.i("smile","用户登陆成功");
                    landing.loginSuccess(user);
                }else {
                    Log.i("smile","用户登陆"+e.toString());
                }
            }
        });
    }


}
