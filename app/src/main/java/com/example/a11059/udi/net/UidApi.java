package com.example.a11059.udi.net;

import model.LoginModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import user.User;

/**
 * Created by 11059 on 2017/3/13.
 */
public interface UidApi {
    @FormUrlEncoded
    @POST("BlueCar/user/login")
    Observable<LoginModel> login(@Field("user_phone") String first, @Field("password") String last);
}
