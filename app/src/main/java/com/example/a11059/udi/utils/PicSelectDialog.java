package com.example.a11059.udi.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a11059.udi.R;

/**
 * Created by Administrator on 2018/4/16.
 */

public class PicSelectDialog extends Dialog {


    private TextView camera;
    private TextView album;
    private TextView cancel;
    private String titleStr;//从外界设置的title文本
    private String messageStr;//从外界设置的消息文本
    //确定文本和取消文本的显示内容
    private String yesStr, noStr;
    String firstItem;
    String secondItem;
    private onCameraSelectListener oncameraSelectListener;//取消按钮被点击了的监听器
    private onAlbumSelectListener onalbumSelectListener;
    private onCancelSelectListener oncancelSelectListener;

    /**
     * 设置取消按钮的显示内容和监听
     *

     * @param onNoOnclickListener
     */
    public void setOncameraSelectListener(onCameraSelectListener onNoOnclickListener) {

        this.oncameraSelectListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param onYesOnclickListener
     */
    public void setOnalbumSelectListener( onAlbumSelectListener onYesOnclickListener) {

        this.onalbumSelectListener = onYesOnclickListener;
    }

    public void setOncancelSelectListener( onCancelSelectListener onYesOnclickListener) {

        this.oncancelSelectListener = onYesOnclickListener;
    }

    public PicSelectDialog(Context context, String firstItem, String secondItem)throws Exception {
        super(context, R.style.MySelectDialog);
        this.firstItem=firstItem;
        this.secondItem=secondItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_select_dialog_layout);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( oncameraSelectListener!= null) {
                    oncameraSelectListener.onCameraClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onalbumSelectListener != null) {
                    onalbumSelectListener.onAlbumClick();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oncancelSelectListener != null) {
                    oncancelSelectListener.onCancelClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
        if (firstItem!=null&&secondItem!=null){
            camera.setText(firstItem);
            album.setText(secondItem);
        }
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        camera = (TextView) findViewById(R.id.camera);
        album = (TextView) findViewById(R.id.album);
        cancel = (TextView) findViewById(R.id.cancel);
    }

    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        titleStr = title;
    }

    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message
     */
    public void setMessage(String message) {
        messageStr = message;
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onCameraSelectListener {
        public void onCameraClick();
    }

    public interface onAlbumSelectListener {
        public void onAlbumClick();
    }

    public interface onCancelSelectListener {
        public void onCancelClick();
    }
}
