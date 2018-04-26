package com.example.a11059.udi.notify;

/**
 * Created by Administrator on 2018/4/11.
 */

public class Notification {

    public int id;
    public Object object;

    public Notification(int notificationID, Object object) {
        this.id=notificationID;
        this.object=object;
    }

    public Notification(int notificationID) {
        this.id=notificationID;
    }


    public static Notification obtain(int NotificationID,Object object){
        return new Notification(NotificationID,object);
    }
    public static Notification obtain(int NotificationID){
        return new Notification(NotificationID);
    }

}
