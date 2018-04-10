package home;

import android.app.Application;
import android.util.Log;

import com.example.a11059.udi.MainActivity;
import com.example.a11059.udi.MyApplication;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 11059 on 2016/11/10.
 */
public class OkHttpRequest extends Thread {
private String name;
    private String pwd;
    private List<HashMap> namePaswards;

    private  String get_order;
    private  String head;
    private  String major;
    private  String school_id;
    private  String signature;
    private  String set_order;
    private  String stu_nickname;
    private  String sex;
    private  String stu_name;
    private  String email;
    private   String stu_num;
    private  String stu_passwd;
    private String stu_phone;
    private Application mainActivity;
    String  url="https://channel.jd.com/shouji.html";

    OkHttpClient okHttpClient=new OkHttpClient();
    //创建一个Request
    Request request=new Request.Builder().url(url).build();

    Response response;

    public OkHttpRequest(String name, String pwd, Application application) {
        this.name=name;
        this.pwd=pwd;
        this.mainActivity=application;

    }

    @Override
    public void run() {
        super.run();
        OkHttpClient okHttpClient=new OkHttpClient();
        FormEncodingBuilder formEncodingBuilder=new FormEncodingBuilder();
        formEncodingBuilder.add("stu_passwd",pwd);
        formEncodingBuilder.add("stu_num",name);
        Request request=new Request.Builder().url("http://www.yangjing1007.cn/Upost4.0/user/login").
                post(formEncodingBuilder.build()).build();
        try {
        Response response=okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
            String  d=  response.body().string();
                if (d.equals("101")){
                    Log.i("kk","请求失败");
                }else {
                    System.out.println(d);
                    json(d);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void json(String d) {

        try {
            JSONObject jsonObject= new JSONObject(d);
          String credibility=  jsonObject.getString("credibility");
           email=  jsonObject.getString("email");
            ((MyApplication)mainActivity).setEmail(email);
             get_order=  jsonObject.getString("get_order");
            ((MyApplication)mainActivity).setGet_order(get_order);
             head=  jsonObject.getString("head");
            ((MyApplication)mainActivity).setHead(head);
             major=  jsonObject.getString("major");
            ((MyApplication)mainActivity).setMajor(major);
             school_id=  jsonObject.getString("school_id");
            ((MyApplication)mainActivity).setSchool_id(school_id);
             set_order=  jsonObject.getString("set_order");
            ((MyApplication)mainActivity).setSet_order(set_order);
             sex=  jsonObject.getString("sex");
            ((MyApplication)mainActivity).setSex(sex);
             signature=  jsonObject.getString("signature");
            ((MyApplication)mainActivity).setSignature(signature);
             stu_name=  jsonObject.getString("stu_name");
            ((MyApplication)mainActivity).setStu_name(stu_name);
             stu_nickname=  jsonObject.getString("stu_nickname");
            ((MyApplication)mainActivity).setStu_nickname(stu_nickname);
            stu_num=  jsonObject.getString("stu_num");
            ((MyApplication)mainActivity).setStu_num(stu_num);
            stu_passwd=  jsonObject.getString("stu_passwd");
            ((MyApplication)mainActivity).setStu_passwd(stu_passwd);
            stu_phone=  jsonObject.getString("stu_phone");
            ((MyApplication)mainActivity).setStu_phone(stu_phone);
            System.out.println(credibility+email+get_order+head+major+school_id+set_order+sex+signature+stu_name+stu_nickname+stu_num+stu_passwd+stu_phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
