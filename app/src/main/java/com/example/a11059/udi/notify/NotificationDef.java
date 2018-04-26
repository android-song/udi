package com.example.a11059.udi.notify;

/**
 * Created by Administrator on 2018/4/11.
 */

public class NotificationDef {
    private static int NotificationID=0;
    public static int OPEN_DEMOTWO_ACTIVITY=getNotificationID();
    public static int DATEPICKER_CLICK=getNotificationID();
    public static int ISURGENT_CLICK=getNotificationID();
    public static int SIZE_CLICK=getNotificationID();
    public static int LOGIN_CLICK=getNotificationID();
    public static int LOGIN_SUCCESS_CLICK=getNotificationID();
    public static int UPDATA_USERINFO=getNotificationID();
    public static int ADDRESS_CLICK=getNotificationID();
    public static int PHONE_CLICK=getNotificationID();
    public static int LOGIN_OUT_CLICK=getNotificationID();
    public static int EDITOR_HEAR_CLICK=getNotificationID();
    public static int EDITOR_NAME_CLICK=getNotificationID();
    public static int EDITOR_NICK_CLICK=getNotificationID();
    public static int EDITOR_SEX_CLICK=getNotificationID();
    public static int EDITOR_ADDRESS_CLICK=getNotificationID();
    public static int EDITOR_PHONE_CLICK=getNotificationID();
    public static int OPEN_GALLERY=getNotificationID();
    public static int OPEN_CAMERA=getNotificationID();
    public static int EDITOR_EMAIL_CLICK=getNotificationID();
    public static int CAMERA_HEAR=getNotificationID();
    public static int SAVE_USERINFO=getNotificationID();
    public static int SHOW_LOADING=getNotificationID();
    public static int HIDEN_LOADING=getNotificationID();
    public static int RELEASE=getNotificationID();
    private static int getNotificationID() {
        return NotificationID++;
    }
     static int getNotificationDefCount() {
        return NotificationID+100;
    }

}
