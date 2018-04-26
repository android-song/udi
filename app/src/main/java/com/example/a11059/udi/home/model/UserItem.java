package com.example.a11059.udi.home.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.a11059.udi.BR;
import com.example.a11059.udi.bomb.MyUser;
import com.example.a11059.udi.user.ReleaseOrderModel;


/**
 * Created by Administrator on 2018/4/3.
 */

public class UserItem extends BaseObservable {
    public static final int ISURGENT=1;
    String name;
    String addrress;
    String takeTime;
    int isUrgent;
    String size;
    String phone;
    String money;
    String imgUrl;
    String sex;

    public UserItem(ReleaseOrderModel orderModel) {
        this.releaseOrderModel=orderModel;
    }

    public ReleaseOrderModel getReleaseOrderModel() {
        return releaseOrderModel;
    }

    public void setReleaseOrderModel(ReleaseOrderModel releaseOrderModel) {
        this.releaseOrderModel = releaseOrderModel;
    }

    ReleaseOrderModel releaseOrderModel;
    public UserItem() {

    }

    @Bindable
    public String getAddrress() {
        return addrress;
    }

    public void setAddrress(String address) {
        this.addrress = address;
        notifyPropertyChanged(BR.addrress);
    }
    @Bindable
    public String getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
        notifyPropertyChanged(BR.takeTime);
    }
    @Bindable
    public int getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(int isUrgent) {
        this.isUrgent = isUrgent;
        notifyPropertyChanged(BR.isUrgent);
    }
    @Bindable
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
        notifyPropertyChanged(BR.size);
    }
    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }
    @Bindable
    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
        notifyPropertyChanged(BR.money);
    }
    @Bindable
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        notifyPropertyChanged(BR.imgUrl);
    }




    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);

    }

    public void setSex(String sex) {
        this.sex = sex;
        notifyPropertyChanged(BR.sex);
    }


   public UserItem(String name, String sex, String address, String time, String money, int urgent, String imaurl, String phone, String size){
        this.name=name;
        this.sex=sex;
        this.addrress=address;
        this.takeTime=time;
        this.money=money;
        this.isUrgent=urgent;
        this.imgUrl=imaurl;
        this.phone=phone;
        this.size=size;


    }
    @Bindable
    public String getName() {
        return name;
    }



    @Bindable
    public String getSex() {
        return sex;
    }


}
