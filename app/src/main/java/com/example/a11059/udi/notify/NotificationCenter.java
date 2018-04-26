package com.example.a11059.udi.notify;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class NotificationCenter {
    private String TAG="NotificationCenter";
    private static int CLICK=0;
   private static NotificationCenter notificationCenter;
   private static  List<WeakReference<BaseNotify>>[] array;

    public static NotificationCenter getNotification(){
        NotificationCenter notificationCenter=obtion(CLICK);

        return  notificationCenter;
    }

    private static NotificationCenter obtion(int click) {

        if (notificationCenter==null){
            notificationCenter=new NotificationCenter();
            array=new ArrayList[NotificationDef.getNotificationDefCount()];
            int idCount=NotificationDef.getNotificationDefCount();
            for (int i = 0; i < idCount; i++) {
                array[i] = new ArrayList<WeakReference<BaseNotify>>();
            }
        }
        return notificationCenter;
    }

    public void   register(int NotificationID,BaseNotify baseNotify){
            registerInner(NotificationID,baseNotify);
   }

    private void registerInner(int notificationID, BaseNotify baseNotify) {
        if (array==null){
            return;
        }
        int i=array[notificationID].size();
//        if (i>0){
//            return;
//        }
        for (int j=0;j<i;j++){
            if (array[notificationID].get(j)==null){
                continue;
            }
            if (array[notificationID].get(j)!=null&&array[notificationID].get(j)==baseNotify){
                Log.d(TAG,"this is already has");
                return;
            }

        }
        array[notificationID].add(new WeakReference<BaseNotify>(baseNotify));
    }

    public void notify(Notification notification){
        int size=array[notification.id].size();
        for (int j=0;j<size;j++){
            Log.d(TAG,"notify size"+size);
            WeakReference<BaseNotify> baseNotify=array[notification.id].get(j);
            BaseNotify myNotify=baseNotify.get();
            if (myNotify!=null) {
                myNotify.notify(notification);
            }
        }
    }



}
