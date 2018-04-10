package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 11059 on 2016/12/15.
 */
public class MsgUser  implements Parcelable {
    private  String form;
    private int number;
    private String content;
    public MsgUser(Parcel in) {
        form = in.readString();
        number = in.readInt();
        content = in.readString();
    }

    public static final Creator<MsgUser> CREATOR = new Creator<MsgUser>() {
        @Override
        public MsgUser createFromParcel(Parcel in) {
            return new MsgUser(in);
        }

        @Override
        public MsgUser[] newArray(int size) {
            return new MsgUser[size];
        }
    };

    public MsgUser(String from, String message, int size) {
        this.setForm(from);
        this.setContent(message);
        this.setNumber(size);
    }

    public MsgUser(String key, int num) {
        this.setForm(key);
        this.setNumber(num);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }



    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(form);
        dest.writeInt(number);
        dest.writeString(content);
    }
}
