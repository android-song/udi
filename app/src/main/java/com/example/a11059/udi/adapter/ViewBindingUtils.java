package com.example.a11059.udi.adapter;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a11059.udi.R;
import com.example.a11059.udi.ToolUtils;
import com.example.a11059.udi.bomb.MyUser;
import com.example.a11059.udi.home.model.UserItem;
import com.example.a11059.udi.notify.Notification;
import com.example.a11059.udi.notify.NotificationCenter;
import com.example.a11059.udi.notify.NotificationDef;
import com.example.a11059.udi.utils.CircleImageView;
import com.example.a11059.udi.utils.CustomDatePicker;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2018/4/8.
 */

public class ViewBindingUtils {
    @BindingAdapter({"click"})
    public static void click(final LinearLayout view, final String name) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.isEmpty()){
                    switch (name){
                        case "1":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.SETTINGFRAGMENT_SHOW));
                            break;
                        case "2":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.HISTORYFRAGMENT_SHOW));
                            break;
                        case "3":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.RELEASEFRAGMENT_SHOW));
                            break;
                        case "4":

                            break;
                        case "5":
                            break;


                    }
                }
            }
        });
    }
    @BindingAdapter({"click"})
    public static void click(final LinearLayout view, final UserItem name) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo",""+name);
                NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.GO_DETAIL,name));
            }
        });
    }
    @BindingAdapter({"backClick"})
    public static void backClick(final Button view, final String name) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo",""+name);
                NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.LOGIN_OUT_CLICK));
            }
        });
    }
    @BindingAdapter({"clickText"})
    public static void clickText(final TextView view, final String name) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo",""+name);
                if (!name.isEmpty()){
                    switch (name){
                        case "1":

                            break;
                        case "4":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.DATEPICKER_CLICK,view));
                            break;
                        case "6":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.LOGIN_CLICK));
                            break;
                        case "7":
                            MyUser.logOut();   //清除缓存用户对象
                            ToolUtils.clearUser();
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.LOGIN_OUT_CLICK));
                            break;
                        case "order":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.ORDER_CLICK));
                            break;
                        case "orderState":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.ORDER_CLICK));
                            break;
                    }
                }
            }
        });
    }

    @BindingAdapter({"OrderClick"})
    public static void orderClick(final TextView view, final UserItem name) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo",""+name);
//                if ("已接单".equals(name.getReleaseOrderModel().getState())){
                    NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.HAVE_ORDER_STATE,name));
//                }
//                if ("订单完成".equals(name.getReleaseOrderModel().getState())){
//                    NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.COMPLETE_STATE,name));
//                }
                }
        });
    }
    @BindingAdapter({"click"})
    public static void click(final ImageView view, final String name) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo",""+name);
                if (!name.isEmpty()){
                    switch (name){
                        case "1":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.ADDRESS_CLICK));
                            break;
                        case "4":
                            break;
                        case "6":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.RELEASE));
                            break;
                        case "editorImage":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.SAVE_USERINFO));
                            break;
                    }
                }

            }
        });
    }
    @BindingAdapter({"loadImage"})
    public static void loadImage(final ImageView view, final String name) {
        Glide.with(view.getContext()).load(name).error(R.drawable.login_icon_account).into(view);
    }
    @BindingAdapter({"releaseClick"})
    public static void releaseClick(final LinearLayout view, final String name) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo",""+name);
                if (!name.isEmpty()){
                    switch (name){
                        case "1":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.ISURGENT_CLICK));
                            break;
                        case "2":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.SIZE_CLICK));
                            break;
                        case "3":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.PHONE_CLICK));
                            break;
                        case "4":
                            Log.d("demo",""+name);

                            break;
                        case "login":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.LOGIN_CLICK));
                            break;
                    }
                }
            }
        });
    }
    @BindingAdapter({"editorClick"})
    public static void editorClick(final LinearLayout view, final String name) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo",""+name);
                if (!name.isEmpty()){
                    switch (name){
                        case "1":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.EDITOR_HEAR_CLICK));
                            break;
                        case "2":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.EDITOR_NAME_CLICK));
                            break;
                        case "3":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.EDITOR_NICK_CLICK));
                            break;
                        case "4":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.EDITOR_SEX_CLICK));
                            break;
                        case "5":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.EDITOR_ADDRESS_CLICK));
                            break;
                        case "6":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.EDITOR_PHONE_CLICK));
                            break;
                        case "7":
                            NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.EDITOR_EMAIL_CLICK));
                            break;
                    }
                }
            }
        });
    }

    @BindingAdapter(value = { "imageUrl", "error" }, requireAll = false)
    public static void loadImage(CircleImageView view, String url, Drawable error) {
        Glide.with(view.getContext()).load(url).into(view);
    }

    @BindingAdapter({"setAdapter"})
    public static <Adapter extends DataBaseAdapter> void setAdapter(PullToRefreshListView lv, Adapter mAdapter){
        lv.setAdapter(mAdapter);
    }
    @BindingAdapter({"setListViewAdapter"})
    public static <Adapter extends DataBaseAdapter> void setListViewAdapter(ListView lv, Adapter mAdapter){
        lv.setAdapter(mAdapter);
    }

}
