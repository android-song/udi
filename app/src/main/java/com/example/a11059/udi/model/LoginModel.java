package com.example.a11059.udi.model;

import android.util.Log;
import android.widget.Toast;

import com.example.a11059.udi.bomb.MyUser;
import com.example.a11059.udi.home.parsenter.LandingPresenter;
import com.example.a11059.udi.view_interface.OnItemClickInterface;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 11059 on 2017/3/13.
 */
public class LoginModel {

    LandingPresenter landingPresenter;
    public LoginModel(LandingPresenter landingPresenter) {
        this.landingPresenter=landingPresenter;
    }


    public void login(String number, String password) {
        if (number.isEmpty()||password.isEmpty()){
            landingPresenter.loginFail("账号或密码不能为空");
            return;
        }
        MyUser.loginByAccount(number, password, new LogInListener<MyUser>() {

            @Override
            public void done(MyUser user, BmobException e) {
                if(user!=null){
                    landingPresenter.loginSuccess(user);
                    Log.i("smile","用户登陆成功");
                }else {
                   landingPresenter.loginFail("账号或密码错误");
                    Log.i("smile","账号或密码错误");
                }
            }
        });
//        BmobUser.loginByAccount(number, password, new LogInListener<MyUser>() {
//            @Override
//            public void done(MyUser user, BmobException e) {
//
//            }
//        });
    }

    public void forgetPassword() {

    }

//    public void loginWays(String way) {
//        if (way.equals("phone")){
//
//        }
//        BmobSMS.requestSMSCode("11位手机号码","模板名称", new QueryListener<Integer>() {
//
//            @Override
//            public void done(Integer smsId,BmobException ex) {
//                if(ex==null){//验证码发送成功
//                    Log.i("smile", "短信id："+smsId);//用于后续的查询本次短信发送状态
//                }
//            }
//        });
//    }


    public void register(String number, String password) {
        MyUser bu = new MyUser();
        bu.setUsername(number);
        bu.setPassword(password);
        bu.setMobilePhoneNumber(number);
//        bu.setEmail("sendi@163.com");
//注意：不能用save方法进行注册
        bu.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser s, BmobException e) {
                if(e==null){
                    landingPresenter.registerSuccess(s);
                }else{
                   landingPresenter.registerFail("注册失败，已经存在账户");
                }
            }
        });
    }
}
