package com.example.a11059.udi.home;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

//import com.squareup.okhttp.FormEncodingBuilder;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by 11059 on 2016/11/14.
 */
//public class HttpAsyncTask extends AsyncTask {
//    private String pwd;
//    private String name;
//private String result;
//    private Landing landing;
//    public HttpAsyncTask(String pwd, String name, Application application, Landing landing) {
//        this.pwd=pwd;
//        this.name=name;
//        this.landing=landing;
//    }
//
//    @Override
//    protected String  doInBackground(Object[] params) {
//        OkHttpClient okHttpClient=new OkHttpClient();
//        FormEncodingBuilder formEncodingBuilder=new FormEncodingBuilder();
//        formEncodingBuilder.add("stu_passwd",pwd);
//        formEncodingBuilder.add("stu_num",name);
//        Request request=new Request.Builder().url("http://www.yangjing1007.cn/Upost4.0/user/login").
//                post(formEncodingBuilder.build()).build();
//        try {
//            Response response=okHttpClient.newCall(request).execute();
//            if (response.isSuccessful()){
//              result=  response.body().string();
//                if (result.equals("101")){
//                    Log.i("kk","请求失败");
//                }else {
//                    System.out.println(result);
////                                    json(d);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//
//    }
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//    @Override
//    protected void onProgressUpdate(Object[] values) {
//        super.onProgressUpdate(values);
//    }
//
//    @Override
//    protected void onPostExecute(Object o) {
//        if (result.length()>1){
//             landing.finish();
//        }
//        super.onPostExecute(o);
//    }
//}
