package home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a11059.udi.MainActivity;
import com.example.a11059.udi.R;

/**
 * Created by 11059 on 2016/11/7.
 */
public class Registered extends AppCompatActivity implements View.OnClickListener{
    private Button button;
    private EditText number;//学号
    private EditText nickname;//昵称
    private EditText phone;//手机号
    private EditText passward;//密码
    private EditText passward_two;//确认密码
    private EditText stu_name_edit;
    private TextView sex;//性别

    private String sex_text;//性别
   private  String stu_name;//姓名
    private String number_text;//学号
    private String nick_text;//昵称
    private String phone_text;//电话号
    private String pwd_text;//密码
    private String pwd_two_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.registered);
        init();
    }

    private void init() {
//        button= (Button) findViewById(R.id.persion_registed);
//        button.setOnClickListener(this);
//        stu_name_edit= (EditText) findViewById(R.id.stu_name);//姓名
//       number= (EditText) findViewById(R.id.register_number);//学号
//        nickname= (EditText) findViewById(R.id.register_nickname);//昵称
//        phone= (EditText) findViewById(R.id.register_phone);//电话号
//        passward= (EditText) findViewById(R.id.register_passward);//密码
//        passward_two= (EditText) findViewById(R.id.register_pass);//确认密码
//        sex= (TextView) findViewById(R.id.register_sex);//性别

    }

    //post请求注册
    private void request() {
        number_text=number.getText().toString();
        nick_text=nickname.getText().toString();
        phone_text=phone.getText().toString();
        pwd_text=passward.getText().toString();
        pwd_two_text=passward_two.getText().toString();
        stu_name=stu_name_edit.getText().toString();
        sex_text=sex.getText().toString();
       if (("").equals(number_text)|("").equals(nick_text)|("").equals(phone_text)|("").
               equals(pwd_text)|("").equals(pwd_two_text)|("").equals(sex_text)){
           Toast.makeText(Registered.this,"请补充完整信息",Toast.LENGTH_SHORT).show();
       }else {
           RegistedRequest registedThread=new RegistedRequest(stu_name,  number_text,  nick_text,  phone_text,  pwd_text,  sex_text);
           registedThread.start();
       }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.persion_registed:
                MainActivity.CommonUtils commonUtils=new MainActivity.CommonUtils();
                System.out.println("请求。。。。");
                if (commonUtils.isFastDoubleClick())
                {
                    request();
                }

                break;
        }

    }

}
