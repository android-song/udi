package home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a11059.udi.R;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by 11059 on 2016/12/14.
 */
public class MessageActivity extends AppCompatActivity implements View.OnClickListener{
    private Button custom_btn;
    private Button next;
    private String phone_number;
    private String custom_text;
    private EditText phone;
    private EditText custon_number;
    private TextView now;
    private int time = 60;
    private boolean flag = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_message);
        //1.初始化sdk     APPKEY：是在mob.com官网上注册的appkey
        SMSSDK.initSDK(this,"19549c0ba37f4","a2007714211699f5586d3071306907e6");//也是所注册的APPSECRETE
        //2.到清单文件中配置信息 （添加网络相关权限以及一个activity信息）
        EventHandler eh=new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {

                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }

        };
        SMSSDK.registerEventHandler(eh);
        init();
    }
    Handler handlerText =new Handler(){
        public void handleMessage(Message msg) {
            if(msg.what==1){
                if(time>0){
                    now.setText("验证码已发送"+time+"秒");
                    time--;
                    handlerText.sendEmptyMessageDelayed(1, 1000);
                }else{
                    now.setText("提示信息");
                    time = 60;
                    now.setVisibility(View.GONE);
//                    getCord.setVisibility(View.VISIBLE);
                }
            }else{
                custon_number.setText("");
                now.setText("提示信息");
                time = 60;
                now.setVisibility(View.GONE);
//                getCord.setVisibility(View.VISIBLE);
            }
        };
    };
    Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event="+event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功,验证通过
                    Toast.makeText(getApplicationContext(), "验证码校验成功", Toast.LENGTH_SHORT).show();
                    handlerText.sendEmptyMessage(2);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){//服务器验证码发送成功
                    reminderText();
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//返回支持发送验证码的国家列表
                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(flag){
                    custom_btn.setVisibility(View.VISIBLE);
                    Toast.makeText(MessageActivity.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                    phone.requestFocus();
                }else{
                    ((Throwable) data).printStackTrace();
//                    int resId = getStringRes(MessageActivity.this, "smssdk_network_error");
                    Toast.makeText(MessageActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    custon_number.selectAll();
//                    if (resId > 0) {
//                        Toast.makeText(MessageActivity.this, resId, Toast.LENGTH_SHORT).show();
//                    }
                }

            }

        }

    };


    private void init() {
        custom_btn= (Button) findViewById(R.id.custom_btn);
        next= (Button) findViewById(R.id.custom_next);
        phone= (EditText) findViewById(R.id.custom_phone);
        now= (TextView) findViewById(R.id.now);
        custon_number= (EditText) findViewById(R.id.custom_number);
        custom_btn.setOnClickListener(this);
        next.setOnClickListener(this);
    }
    //验证码送成功后提示文字
    private void reminderText() {
        now.setVisibility(View.VISIBLE);
        handlerText.sendEmptyMessageDelayed(1, 1000);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.custom_btn:
                phone_number = phone.getText().toString().trim();
                System.out.println("88888888888"+phone_number);
                SMSSDK.getVerificationCode("86",phone_number);
                custon_number.requestFocus();
                next.setVisibility(View.GONE);
                break;
            case R.id.custom_next:
                custom_text = custon_number.getText().toString().trim();
                SMSSDK.submitVerificationCode("86", phone_number, custom_text);
                flag = false;
                break;
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
