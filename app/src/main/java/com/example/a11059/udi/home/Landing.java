package com.example.a11059.udi.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a11059.udi.MainActivity;
import com.example.a11059.udi.R;
import com.example.a11059.udi.bomb.MyUser;
import com.example.a11059.udi.home.model.DataModel;
import com.example.a11059.udi.home.parsenter.LandingPresenter;
import com.example.a11059.udi.home.parsenter.MyListener;
import com.example.a11059.udi.home.ui.BaseView;
import com.example.a11059.udi.notify.Notification;
import com.example.a11059.udi.notify.NotificationCenter;
import com.example.a11059.udi.notify.NotificationDef;
import com.example.a11059.udi.utils.BaseFragment;
import com.example.a11059.udi.view_interface.LoginCallback;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * Created by 11059 on 2016/11/7.
 */
public class Landing extends BaseFragment implements View.OnClickListener,BaseView,LoginCallback {
    private Button but_go;//登录
    private EditText user_name;//输入账号
    private EditText passward;//输入密码
    private String name;//用户的账号
    private  String pwd;//用户的密码
    private TextView phone_number;//手机验证
    private TextView forget;//忘记密码
    private  String check="";
    private TextView name_prompt;
    private TextView pwd_prompt;
    private MainActivity.CommonUtils commonUtils;
    private List<HashMap> namePaswards;
    private      AsyncTask asyncTask;
    private SharedPreferences sp;
    private int Sys_mesg;
    private MyListener myListener;
    private View view;
    private LandingPresenter landingPresenter;
    private View register_btn;
    private View psLoginView;
    private View passwardLogin;
    private View verificationLogin;
    private View verificationBtn;
    private EditText phone;
    private EditText verification;
    private Button back;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.persion_listview, container, false);
            landingPresenter=new LandingPresenter(this);
        }
        init();

        return view;
    }


    private void init() {
        back=(Button) view.findViewById(R.id.btn_back);
        user_name= (EditText)view.findViewById(R.id.persion_name);
        passward= (EditText)view.findViewById(R.id.persion_passward);
        forget= (TextView)view.findViewById(R.id.forget);
        phone_number= (TextView)view.findViewById(R.id.phone_number);
        psLoginView=view.findViewById(R.id.password_login_view);
        register_btn=view.findViewById(R.id.register_btn);
        passwardLogin=view.findViewById(R.id.password_login);
        verificationBtn=view.findViewById(R.id.verification_btn);
        verificationLogin=view.findViewById(R.id.verification_login_btn);
        phone= (EditText) view.findViewById(R.id.phone);
        verification=(EditText) view.findViewById(R.id.verification);
        verificationBtn.setOnClickListener(this);
        verificationLogin.setOnClickListener(this);
        passwardLogin.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        phone_number.setOnClickListener(this);
        forget.setOnClickListener(this);
        but_go= (Button)view.findViewById(R.id.persion_go);
        but_go.setOnClickListener(this);
        back.setOnClickListener(this);

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.persion_go:
                landingPresenter.login(user_name.getText().toString(),passward.getText().toString());
                break;
            case R.id.phone_number:
               landingPresenter.loginWays("phone");
                break;
            case R.id.forget:

//                Intent intent=new Intent(Landing.this, MessageActivity.class);
//                startActivity(intent);
                break;
            case R.id.register_btn:
                landingPresenter.register(user_name.getText().toString(),passward.getText().toString());
                break;
            case R.id.password_login:
                landingPresenter.loginWays("password");
                break;
            case R.id.verification_btn:
                landingPresenter.getVerification(phone.getText().toString());
                break;
            case R.id.verification_login_btn:
                landingPresenter.verificationLogin(phone.getText().toString(),verification.getText().toString());
                break;
            case R.id.btn_back:
                NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.LOGIN_OUT_CLICK));
                break;

        }
    }







    @Override
    public void upData(DataModel dataModel) {
//        Toast.makeText(this,dataModel.getContent(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorUi() {

    }

    @Override
    public void inRequest() {

    }

    @Override
    public String getName() {
        name=user_name.getText().toString();

        return name;
    }

    @Override
    public String getPassword() {
        pwd=passward.getText().toString();
        return pwd;
    }

    @Override
    public void loginSuccess(MyUser myUser) {
        toast(getContext(),"用户登陆成功");
        hiddenKey();
        NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.LOGIN_SUCCESS_CLICK));
        NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.UPDATA_USERINFO));
    }

    private void hiddenKey() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //隐藏软键盘 //
        imm.hideSoftInputFromWindow(user_name.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passward.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(phone.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(verification.getWindowToken(), 0);
    }

    @Override
    public void loginFail(String toast) {
        toast(getContext(),toast);
    }

    @Override
    public void registerFail(String toast) {
        toast(getContext(),toast);
    }

    @Override
    public void registerSuccess(MyUser myUser) {
        toast(getContext(),"注册成功");
        landingPresenter.login(user_name.getText().toString(),passward.getText().toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void showPhoneRegister() {
            psLoginView.setVisibility(View.GONE);
            passwardLogin.setBackground(getResources().getDrawable(R.drawable.shap));
            phone_number.setBackground(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void showPasswordRegister() {
        psLoginView.setVisibility(View.VISIBLE);
        phone_number.setBackground(getResources().getDrawable(R.drawable.shap));
        passwardLogin.setBackground(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void VerificationSuccess(String ver) {
        toast(getContext(),ver);
        verificationBtn.setEnabled(false);
        verificationBtn.setBackground(getResources().getDrawable(R.color.gray));
    }
}
