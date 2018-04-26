package com.example.a11059.udi.home;

import android.util.Log;
//
//import com.squareup.okhttp.FormEncodingBuilder;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 11059 on 2016/11/10.
 */
public class DetailsHttpRequest extends Thread {
//private String name;
//    private String pwd;
//    private List<HashMap> namePaswards;
//    String  url="https://channel.jd.com/shouji.html";
//    private int flag=0;
//    private    Request request;
//    private OkHttpClient okHttpClient;
//
////    OkHttpClient okHttpClient=new OkHttpClient();
////    //创建一个Request
////    Request request=new Request.Builder().url(url).build();
////
////    Response response;
//
//    public DetailsHttpRequest(String name, String pwd) {
//        this.name=name;
//        this.pwd=pwd;
//    }
//
//    public DetailsHttpRequest(int i) {
//        this.flag=i;
//    }
//
//
//    @Override
//    public void run() {
//        super.run();
//        okHttpClient =new OkHttpClient();
//        FormEncodingBuilder formEncodingBuilder=new FormEncodingBuilder();
//        switch (flag){
//            case 0:
//                break;
//            case 1:
//                request =new Request.Builder().url("http://www.yangjing1007.cn/Upost4.0/user/login").
//                        post(formEncodingBuilder.build()).build();
//                try {
//                    Response response=okHttpClient.newCall(request).execute();
//                    if (response.isSuccessful()){
//                     Log.i("kk",response.body().string());
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case 2:
////                request =new Request.Builder().url("http://www.yangjing1007.cn/Upost4.0/user/login").
////                        post(formEncodingBuilder.build()).build();
////                try {
////                    Response response=okHttpClient.newCall(request).execute();
////                    if (response.isSuccessful()){
////                        Log.i("kk",response.body().string());
////                    }
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//                break;
//            case 3:
////                request =new Request.Builder().url("http://www.yangjing1007.cn/Upost4.0/user/login").
////                        post(formEncodingBuilder.build()).build();
////                try {
////                    Response response=okHttpClient.newCall(request).execute();
////                    if (response.isSuccessful()){
////                        Log.i("kk",response.body().string());
////                    }
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//                break;
//            case 4:
//                formEncodingBuilder.add("stu_passwd","d");
//                request =new Request.Builder().url("http://www.yangjing1007.cn/Upost/infoComp/04142033").
//                        put(formEncodingBuilder.build()).build();
//                try {
//                    Response response=okHttpClient.newCall(request).execute();
//                    if (response.isSuccessful()){
//                        Log.i("kk",response.body().string());
//                    }else {
//                        Log.i("kk","失败");
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;
//
//        }
//
//
//
//    }
//
//    private void request() {
//        try {
//            Response response=okHttpClient.newCall(request).execute();
//            if (response.isSuccessful()){
//                Log.i("kk",response.body().string());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
