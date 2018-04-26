package com.example.a11059.udi.model;

/**
 * Created by 11059 on 2016/12/14.
 */
public class ChatUser {
    private String   message;
    private int flag;
    public ChatUser() {

    }

    public String getMessage() {
        return message;
    }

    public int getFlag() {
        return flag;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }



    public ChatUser(String meg, int i) {
        this.message=meg;
        this.flag=i;
    }
}
