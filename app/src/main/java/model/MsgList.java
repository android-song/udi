package model;

/**
 * Created by 11059 on 2016/12/19.
 */
public class MsgList {
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private int size;
    private String name;
    private String time;
}
