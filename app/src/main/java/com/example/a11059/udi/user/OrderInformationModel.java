package com.example.a11059.udi.user;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.a11059.udi.BR;


/**
 * Created by Administrator on 2018/4/14.
 */

public class OrderInformationModel extends BaseObservable {
   private String address;
   private String takeTime;
   private String isUrgent;
   private String size;
   private String phone;
   private String money;

    public OrderInformationModel(String address, String takeTime, String isUrgent, String size, String phone, String money) {
        this.address=address;
        this.takeTime=takeTime;
        this.isUrgent=isUrgent;
        this.size=size;
        this.phone=phone;
        this.money=money;
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }
    @Bindable
    public String getTakeTime() {
        return takeTime;
    }
   @Bindable
    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
        notifyPropertyChanged(BR.takeTime);
    }
    @Bindable
    public String getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(String isUrgent) {
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


}
