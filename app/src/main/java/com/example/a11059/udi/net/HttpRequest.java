package com.example.a11059.udi.net;

import java.util.concurrent.TimeUnit;

import model.LoginModel;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 11059 on 2017/3/13.
 */
public class HttpRequest {
  private static HttpRequest httpRequest=new HttpRequest();
private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private  String BASE_URL="http://www.yangjing1007.cn/";
    private UidApi uidApi;
    private HttpRequest() {

                    okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).build();
                    retrofit = new Retrofit.Builder()
                            .client(okHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .baseUrl(BASE_URL)
                            .build();
                    uidApi = retrofit.create(UidApi.class);

    }
    public static HttpRequest getHttpRequest(){
        return httpRequest;
    }

    public void getLogin(Subscriber<LoginModel> subscriber, String id,String password) {
        Observable observable = uidApi.login(id,password);
        toSubscribe(observable, subscriber);
    }


    private void toSubscribe(Observable o, Subscriber s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
}
