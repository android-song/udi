package com.example.a11059.udi.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a11059.udi.R;

/**
 * Created by Administrator on 2018/4/16.
 */

public class SelectDialog extends Dialog {


    private TextView big;
    private TextView middle;
    private TextView small;
    private String titleStr;//从外界设置的title文本
    private String messageStr;//从外界设置的消息文本
    //确定文本和取消文本的显示内容
    private String yesStr, noStr;

    private onBigSelectListener onBigSelectListener;//取消按钮被点击了的监听器
    private onMiddleSelectListener onMiddleSelectListener;
    private onSmallSelectListener onSmallSelectListener;

    /**
     * 设置取消按钮的显示内容和监听
     *

     * @param onNoOnclickListener
     */
    public void setOnBigSelectListener(onBigSelectListener onNoOnclickListener) {

        this.onBigSelectListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param onYesOnclickListener
     */
    public void setOnMiddleSelectListener( onMiddleSelectListener onYesOnclickListener) {

        this.onMiddleSelectListener = onYesOnclickListener;
    }

    public void setOnSmallSelectListener( onSmallSelectListener onYesOnclickListener) {

        this.onSmallSelectListener = onYesOnclickListener;
    }

    public SelectDialog(Context context)throws Exception {
        super(context, R.style.MySelectDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_dialog_layout);
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
        big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( onBigSelectListener!= null) {
                    onBigSelectListener.onBigClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMiddleSelectListener != null) {
                    onMiddleSelectListener.onMiddleClick();
                }
            }
        });
        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSmallSelectListener != null) {
                    onSmallSelectListener.onSmallClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
//            if (titleStr != null) {
//                titleTv.setText(titleStr);
//            }
//            if (messageStr != null) {
//                messageTv.setText(messageStr);
//            }
//            //如果设置按钮的文字
//            if (yesStr != null) {
//                yes.setText(yesStr);
//            }
//            if (noStr != null) {
//                no.setText(noStr);
//            }
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        big = (TextView) findViewById(R.id.big_size);
        middle = (TextView) findViewById(R.id.middle_size);
        small = (TextView) findViewById(R.id.small_size);
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
    public interface onBigSelectListener {
        public void onBigClick();
    }

    public interface onMiddleSelectListener {
        public void onMiddleClick();
    }

    public interface onSmallSelectListener {
        public void onSmallClick();
    }
}
