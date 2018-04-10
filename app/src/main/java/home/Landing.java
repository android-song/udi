package home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a11059.udi.MainActivity;
import com.example.a11059.udi.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import home.model.DataModel;
import home.parsenter.MyListener;
import home.ui.BaseView;

/**
 * Created by 11059 on 2016/11/7.
 */
public class Landing extends AppCompatActivity implements View.OnClickListener,BaseView{
    private Button button;//注册
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persion_listview);
        //1.初始化sdk     APPKEY：是在mob.com官网上注册的appkey
        SMSSDK.initSDK(this,"19549c0ba37f4","a2007714211699f5586d3071306907e6");//也是所注册的APPSECRETE
        //2.到清单文件中配置信息 （添加网络相关权限以及一个activity信息）
        init();
    }

    private void init() {
        sp=getSharedPreferences("activity",Context.MODE_PRIVATE);
        user_name= (EditText) findViewById(R.id.persion_name);
        passward= (EditText)findViewById(R.id.persion_passward);
        button= (Button) findViewById(R.id.home_registered);
        forget= (TextView) findViewById(R.id.forget);
        phone_number= (TextView) findViewById(R.id.phone_number);
        phone_number.setOnClickListener(this);
        button.setOnClickListener(this);
        forget.setOnClickListener(this);
        but_go= (Button) findViewById(R.id.persion_go);
        but_go.setOnClickListener(this);
         commonUtils=new MainActivity.CommonUtils();
        myListener=new MyListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_registered:

//改成MVP请求改成retrofit
                phoneValidation(1);

                break;
            case R.id.persion_go:
                System.out.println("请求2222");
//                if (commonUtils.isFastDoubleClick())
//                {
//                check();
                myListener.request();

//                }
                break;
            case R.id.phone_number:
                phoneValidation(2);
                break;
            case R.id.forget:
                Intent intent=new Intent(Landing.this,MessageActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }


    private void phoneValidation(final int registr) {
        //注册手机号
        RegisterPage registerPage=new RegisterPage();

        //注册回调事件
        registerPage.setRegisterCallback(new EventHandler(){
            //事件完成后
            @Override

            public void afterEvent(int event, int result, Object data) {
//判断结果是否已经完成
                if(result== SMSSDK.RESULT_COMPLETE){//解析完成 //获取数据data
                    HashMap<String,Object> maps= (HashMap<String, Object>) data;//数据强转
                    String country= (String) maps.get("country");
//手机号码
                    String phone= (String) maps.get("phone");
                    submitUserInfo(country,phone); //调用提交数据方ZZ
                   if (registr==1){
                       jump();
                   }else {
                       jump_phone();
                   }


                }}
        });
//显示注册界用下载的inde.xml文档中的show()方法
        registerPage.show(Landing.this);
    }

    private void jump_phone() {
        finish();
    }

    private void jump() {
        Intent intent=new Intent(Landing.this,Registered.class);
        System.out.println("请求111。。。");
        startActivity(intent);
        finish();
    }

    public void submitUserInfo(String country,String phone){

        Random r=new Random();//获得一个随机数
        String uid=Math.abs(r.nextInt())+"";
        String nickName="MyApp";
        SMSSDK.submitUserInfo(uid,nickName,null,country,phone);
    }

    private void check() {
        name=user_name.getText().toString();
        pwd=passward.getText().toString();
        System.out.println("ddddddddddd"+name+pwd);
        if (("").equals(name)){
            Toast.makeText(this,"账号不能为空！",Toast.LENGTH_SHORT).show();
        }else  if (name.length()>15){
                Toast.makeText(this,"名称不得超过15个字！",Toast.LENGTH_SHORT).show();
        } else {
            if (check.equals(pwd)) {
                Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            } else {
//                OkHttpRequest okHttpRequest = new OkHttpRequest(name, pwd, getApplication());
//                okHttpRequest.start();
//            reqiuest();
                //sharePreference存储账号密码
                SharedPreferences.Editor editor= sp.edit();
                editor.putString("passward",pwd);
                editor.putString("name",name);
                editor.commit();
                chatWith();
//                if (Sys_mesg==1) {
                    Toast.makeText(Landing.this,"登录成功"+Sys_mesg, Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(Landing.this,"登录失败"+Sys_mesg, Toast.LENGTH_SHORT).show();
//                }
                Intent intent=new Intent(Landing.this,MainActivity.class);
                startActivity(intent);
                finish();
//            HttpAsyncTask httpAsyncTask=new HttpAsyncTask(pwd,name,getApplication(),Landing.this);
//                httpAsyncTask.execute();
            }
        }





    }
    private void chatWith() {

        sp= getSharedPreferences("activity", Context.MODE_PRIVATE);
        String name= sp.getString("name","空");
        String pwd=sp.getString("passward","空");

        EMClient.getInstance().login(name, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                System.out.println("2222222222222shrea成功");
                Sys_mesg=1;

            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d("tag", "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                System.out.println("2222222222222shrea失败");
                Sys_mesg=2;

            }
        });
    }


    @Override
    public void upData(DataModel dataModel) {
        Toast.makeText(this,dataModel.getContent(),Toast.LENGTH_SHORT).show();
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
}
