package com.example.a11059.udi.user;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.a11059.udi.R;
import com.example.a11059.udi.ToolUtils;
import com.example.a11059.udi.bomb.MyUser;
import com.example.a11059.udi.databinding.ReleaseDataBinding;
import com.example.a11059.udi.notify.BaseNotify;
import com.example.a11059.udi.notify.Notification;
import com.example.a11059.udi.notify.NotificationCenter;
import com.example.a11059.udi.notify.NotificationDef;
import com.example.a11059.udi.utils.BaseFragment;
import com.example.a11059.udi.utils.CustomDatePicker;
import com.example.a11059.udi.utils.EditorDialog;
import com.example.a11059.udi.utils.PromptDialog;
import com.example.a11059.udi.utils.SelectDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by 11059 on 2016/11/6.
 */
public class User extends BaseFragment implements BaseNotify {
    private ListView listView;
    private String[] list = {"取件人", "快递名称", "取件时间", "联系方式", "要求送件时间"};
    private View view;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    ReleaseDataBinding dataBinding;
    private CustomDatePicker timePicker;
    private String time;
    PromptDialog promptDialog;
    SelectDialog selectDialog;
    OrderInformationModel orderInformationModel;
    private String promptText;
    private int promptType;
    private int type;
    private EditorDialog editorDialog;
    private String hintText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        Log.d("demo", "onCreateView" + this);
        if (view == null) {
            Log.d("demo", "onCreateView  view == null" + this);
            NotificationCenter.getNotification().register(NotificationDef.DATEPICKER_CLICK, this);
            NotificationCenter.getNotification().register(NotificationDef.ISURGENT_CLICK, this);
            NotificationCenter.getNotification().register(NotificationDef.SIZE_CLICK, this);
            NotificationCenter.getNotification().register(NotificationDef.LOGIN_OUT_CLICK, this);
            NotificationCenter.getNotification().register(NotificationDef.RELEASE, this);
            NotificationCenter.getNotification().register(NotificationDef.UPDATA_USERINFO, this);
            NotificationCenter.getNotification().register(NotificationDef.ADDRESS_CLICK, this);
            NotificationCenter.getNotification().register(NotificationDef.PHONE_CLICK, this);
            view = LayoutInflater.from(getContext()).inflate(R.layout.home, container, false);
            dataBinding = DataBindingUtil.bind(view);
            updateUserInfo();
            initPicker();
        }


        return dataBinding.getRoot();
    }


    private void initPicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        time = sdf.format(new Date());
        String date = time.split(" ")[0];
        //设置当前显示的日期
        dataBinding.takeTime.setText(date);
        //设置当前显示的时间
        dataBinding.takeTime.setText(time);
        dataBinding.isUrgent.setText("正常");
        dataBinding.size.setText("中件");
    }

    @Override

    public void notify(final Notification notification) {
        Log.d("demo", "notify  user" );
        if (notification.id == NotificationDef.DATEPICKER_CLICK) {
            timePickerShow();
        } else if (notification.id == NotificationDef.ISURGENT_CLICK) {
            promptDialogShow(notification.id);
        } else if (notification.id == NotificationDef.SIZE_CLICK) {
            selectDialogShow();
        }else if (notification.id==NotificationDef.UPDATA_USERINFO||notification.id==NotificationDef.LOGIN_OUT_CLICK){
            updateUserInfo();
        }else if (notification.id==NotificationDef.RELEASE){
            release(notification);
        }else if (notification.id==NotificationDef.ADDRESS_CLICK){
            editorDialogShow(notification.id);
        }
        else if (notification.id==NotificationDef.PHONE_CLICK){
            editorDialogShow(notification.id);
        }
    }
    private void editorDialogShow(int i) {
        type=i;
        if (i==NotificationDef.ADDRESS_CLICK){
            hintText=getString(R.string.address_tv);
        }
        if (i==NotificationDef.PHONE_CLICK){
            hintText=getString(R.string.phone_tv);
        }
        try {
            editorDialog = new EditorDialog(getContext());
            editorDialog.setTitle(getString(R.string.prompt_tv));
            editorDialog.setMessage(hintText);
            editorDialog.setYesOnclickListener(getString(R.string.ok_tv), new EditorDialog.onYesOnclickListener() {
                @Override
                public void onYesClick(String message) {
                    editorDialog.dismiss();
                    if (type==NotificationDef.ADDRESS_CLICK){
                        dataBinding.address.setText(message);
                        return;
                    }
                    if (type==NotificationDef.PHONE_CLICK){
                        dataBinding.userPhone.setText(message);
                        return;
                    }

                }
            });
            editorDialog.setNoOnclickListener(getString(R.string.cancel_tv), new EditorDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    editorDialog.dismiss();
                }
            });
            editorDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void release(Notification notification) {
        if (ToolUtils.getUser()==null){
            toast(getContext(),"请登录账号");
        }else  if (dataBinding.address.getText().toString().isEmpty()||dataBinding.takeTime.getText().toString().isEmpty()
                ||dataBinding.isUrgent.getText().toString().isEmpty() ||dataBinding.size.getText().toString().isEmpty()
                ||dataBinding.userPhone.getText().toString().isEmpty()||dataBinding.bounty.getText().toString().isEmpty()
                ||dataBinding.note.getText().toString().isEmpty()){
                toast(getContext(),"请补全发布信息！");
        }else {
            promptDialogShow(notification.id);
        }
    }

    private void updateUserInfo() {
        MyUser userInfo = ToolUtils.getUser();
        if(userInfo != null){
            // 允许用户使用应用
            if (userInfo.getAddress()!=null){
                dataBinding.address.setText(userInfo.getAddress().toString());
            }
            if (userInfo.getPhone()!=null){
                dataBinding.userPhone.setText(userInfo.getPhone());
            }
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            Log.e("demo","缓存用户对象为空时");
            dataBinding.address.setText("");
            dataBinding.userPhone.setText("");

        }
    }

    private void selectDialogShow() {
        try {
            selectDialog = new SelectDialog(getContext());
            selectDialog.setOnBigSelectListener(new SelectDialog.onBigSelectListener() {
                @Override
                public void onBigClick() {
                    dataBinding.size.setText(getString(R.string.release_big_size_tv));
                    selectDialog.dismiss();
                }
            });
            selectDialog.setOnMiddleSelectListener(new SelectDialog.onMiddleSelectListener() {
                @Override
                public void onMiddleClick() {
                    dataBinding.size.setText(getString(R.string.release_middle_tv));
                    selectDialog.dismiss();
                }
            });
            selectDialog.setOnSmallSelectListener(new SelectDialog.onSmallSelectListener() {
                @Override
                public void onSmallClick() {
                    dataBinding.size.setText(getString(R.string.release_small_tv));
                    selectDialog.dismiss();

                }
            });
            selectDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void promptDialogShow(int id) {
        promptType=id;
        if (id==NotificationDef.RELEASE){
            promptText=getString(R.string.isrelease_tv);
        }else {
            promptText=getString(R.string.urgent_prompt_tv);
        }
        try {
            promptDialog = new PromptDialog(getContext());
            promptDialog.setTitle(getString(R.string.prompt_tv));
            promptDialog.setMessage(promptText);
            promptDialog.setYesOnclickListener(getString(R.string.ok_tv), new PromptDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    promptDialog.dismiss();
                    if (promptType==NotificationDef.RELEASE){
                        NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.SHOW_LOADING));
                        releaseToService();
                    }else {
                        dataBinding.isUrgent.setText("加急");
                    }

                }
            });
            promptDialog.setNoOnclickListener(getString(R.string.cancel_tv), new PromptDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    promptDialog.dismiss();
                    if (promptType==NotificationDef.RELEASE){
                    }else {
                        dataBinding.isUrgent.setText("正常");
                    }

                }
            });
            promptDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void releaseToService() {
        ReleaseOrderModel releaseOrderModel = new ReleaseOrderModel();
        if (dataBinding.isUrgent.getText().toString().equals("加急")){
            releaseOrderModel.setIsUrgent(1);
        }else {
            releaseOrderModel.setIsUrgent(0);
        }
        releaseOrderModel.setMoney(dataBinding.bounty.getText().toString());
        releaseOrderModel.setMyUser(ToolUtils.getUser());
        releaseOrderModel.setNote(dataBinding.note.getText().toString());
        releaseOrderModel.setPhone(dataBinding.userPhone.getText().toString());
        releaseOrderModel.setSize(dataBinding.size.getText().toString());
        releaseOrderModel.setTakeTime(dataBinding.takeTime.getText().toString());
        releaseOrderModel.setAddress(dataBinding.address.getText().toString());
        if (!ToolUtils.getUser().getName().isEmpty()){
            releaseOrderModel.setName(ToolUtils.getUser().getName());
        }
        if (!ToolUtils.getUser().getSex().isEmpty()){
            releaseOrderModel.setSex(ToolUtils.getUser().getSex());
        }
        if (!ToolUtils.getUser().getImgUrl().isEmpty()){
            releaseOrderModel.setImgUrl(ToolUtils.getUser().getImgUrl());
        }
        if (!ToolUtils.getUser().getNick().isEmpty()){
            releaseOrderModel.setNick(ToolUtils.getUser().getNick());
        }
        if (!ToolUtils.getUser().getGmail().isEmpty()){
            releaseOrderModel.setGmail(ToolUtils.getUser().getGmail());

        }
        releaseOrderModel.setState(getString(R.string.state_release));
        releaseOrderModel.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.HIDEN_LOADING));
                if(e==null){
                    toast(getContext(),getString(R.string.release_success));
                    NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.UPDATE_RELEASE_LIST));
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    toast(getContext(),getString(R.string.release_fail));
                }
            }
        });
    }

    private void timePickerShow() {
        /**
         * 设置年月日
         */

            if (timePicker == null) {
                timePicker = new CustomDatePicker(getContext(), getString(R.string.select_time_tv), new CustomDatePicker.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        dataBinding.takeTime.setText(time);
                    }
                }, getString(R.string.time_format), getString(R.string.time_format_end));//"2027-12-31 23:59"
                timePicker.showSpecificTime(true);
                timePicker.setIsLoop(true);
            }
            timePicker.show(time);


    }



    }

