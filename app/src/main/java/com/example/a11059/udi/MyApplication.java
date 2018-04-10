package com.example.a11059.udi;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import java.util.Iterator;
import java.util.List;

/**
 * Created by 11059 on 2016/10/16.
 */
public class MyApplication extends Application {
    private String name;//取件人的姓名
    private String phone;//取件人的电话
    private String courier;//取件人的快递名称
    private int phone_second;//要求取件时间   分钟
    private int phone_hour;//小时
    private int number_second;//要求送到时间  分钟
    private int number_hour;//时间
    private int stu_id;
    private String stu_num;
    private String stu_name;
    private String sex;
    private String stu_phone;
    private String email;
    private String stu_passwd;
    private String school_id;
    private String major;
    private String stu_nickname;
    private String head;
    private String signature;
    private String credibility;
    private String set_order;
    private String get_order;


    public void setStu_id(int stu_id) {
        this.stu_id = stu_id;
    }

    public String getStu_num() {
        return stu_num;
    }

    public void setStu_num(String stu_num) {
        this.stu_num = stu_num;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStu_phone() {
        return stu_phone;
    }

    public void setStu_phone(String stu_phone) {
        this.stu_phone = stu_phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStu_passwd() {
        return stu_passwd;
    }

    public void setStu_passwd(String stu_passwd) {
        this.stu_passwd = stu_passwd;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStu_nickname() {
        return stu_nickname;
    }

    public void setStu_nickname(String stu_nickname) {
        this.stu_nickname = stu_nickname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCredibility() {
        return credibility;
    }

    public void setCredibility(String credibility) {
        this.credibility = credibility;
    }

    public String getSet_order() {
        return set_order;
    }

    public void setSet_order(String set_order) {
        this.set_order = set_order;
    }

    public String getGet_order() {
        return get_order;
    }

    public void setGet_order(String get_order) {
        this.get_order = get_order;
    }






    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        init();
    }


    public int getStu_id() {
        return stu_id;
    }

    public String getEmail() {
        return email;
    }

    public String getSex() {
        return sex;
    }

    public String getSchool_id() {
        return school_id;
    }


    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public void setNumber_hour(int number_hour) {
        this.number_hour = number_hour;
    }


    public void setNumber_second(int number_second) {
        this.number_second = number_second;
    }

    public void setPhone_hour(int phone_hour) {
        this.phone_hour = phone_hour;
    }

    public void setPhone_second(int phone_second) {
        this.phone_second = phone_second;
    }

    public int getNumber_hour() {
        return number_hour;
    }

    public int getNumber_second() {
        return number_second;
    }

    public int getPhone_hour() {
        return phone_hour;
    }

    public int getPhone_second() {
        return phone_second;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    private void init() {
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(this.getPackageName())) {
            Log.e("Tag", "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
//初始化
        EMClient.getInstance().init(this, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

    }


    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
