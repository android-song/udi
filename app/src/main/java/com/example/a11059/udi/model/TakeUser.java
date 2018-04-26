package com.example.a11059.udi.model;

/**
 * Created by 11059 on 2016/12/15.
 */
public class TakeUser {

    public String getImg() {
        return img;
    }

    public String getHome() {
        return home;
    }

    public String getName() {
        return name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public String getMoney() {
        return money;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String name;
    private String phone_num;
    private String money;
    private String home;
    private String img;
}
