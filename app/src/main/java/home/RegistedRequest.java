package home;

import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 11059 on 2016/11/10.
 */
public class RegistedRequest extends Thread {

    private String number_text;
    private String nick_text;
    private String phone_text;
    private String pwd_text;
    private String sex_text;
    private String stu_name;
    OkHttpClient okHttpClient=new OkHttpClient();
    //创建一个Request

    Response response;
//number_text,nick_text,phone_text,pwd_text,sex_text
    public RegistedRequest(String stu_name, String number_text, String nick_text, String phone_text, String pwd_text, String sex_text) {
        this.number_text=number_text;
        this.nick_text=nick_text;
        this.phone_text=phone_text;
        this.pwd_text=pwd_text;
        this.sex_text=sex_text;
        this.stu_name=stu_name;
    }



    @Override
    public void run() {
        super.run();
        OkHttpClient okHttpClient=new OkHttpClient();
        FormEncodingBuilder formEncodingBuilder=new FormEncodingBuilder();
        formEncodingBuilder.add("stu_num", number_text);
        formEncodingBuilder.add("stu_nickname", nick_text);
        formEncodingBuilder.add("stu_phone", phone_text);
        formEncodingBuilder.add("stu_passwd", pwd_text);
        formEncodingBuilder.add("sex", sex_text);
        formEncodingBuilder.add("stu_name", stu_name);
        Request request=new Request.Builder().url("http://www.yangjing1007.cn/Upost4.0/user/regist").
                post(formEncodingBuilder.build()).build();
        try {
        Response response=okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
                Log.i("dddd",response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
