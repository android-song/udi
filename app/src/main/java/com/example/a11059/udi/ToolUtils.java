package com.example.a11059.udi;

import android.content.res.Resources;

import com.example.a11059.udi.bomb.MyUser;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2018/4/23.
 */

public class ToolUtils {
    static MyUser myUser = null;

    public static int px2dip(int pxValue)
    {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static float dip2px(float dipValue)
    {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return  (dipValue * scale + 0.5f);
    }
    public static MyUser getUser(){
        if (myUser==null){
            myUser= BmobUser.getCurrentUser(MyUser.class);
        }
        return myUser;
    }

    public static void  updateUser(MyUser myUser){
            ToolUtils.myUser =myUser;

    }
    public static void clearUser(){
      myUser=null;
    }
}
