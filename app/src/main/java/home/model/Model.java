package home.model;

import android.util.Log;

import com.example.a11059.udi.net.HttpRequest;
import com.example.a11059.udi.net.UidApi;

import home.parsenter.Listener;
import model.LoginModel;
import rx.Subscriber;

/**
 * Created by 11059 on 2017/3/13.
 */
public class Model implements StrogeModel {
    @Override
    public void showUi(Listener listener) {
        String s="请求获取后的数据";
        listener.UpDate(s);
    }

    @Override
    public void request(String name, String password, final Listener listener) {
        Subscriber subscriber = new Subscriber<LoginModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("log","error");

            }

            @Override
            public void onNext(LoginModel ds) {
                String s="请求获取后的数据";
                listener.UpDate(ds.getMessage()+ds.getResult_code());
            }
        };

        HttpRequest.getHttpRequest().getLogin(subscriber,name,password);

    }


}
