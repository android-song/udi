package com.example.a11059.udi.take;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.example.a11059.udi.MyApplication;
import com.example.a11059.udi.NativeUtil;
import com.example.a11059.udi.R;
import com.example.a11059.udi.ToolUtils;
import com.example.a11059.udi.bomb.MyUser;
import com.example.a11059.udi.databinding.DetailDataBinding;
import com.example.a11059.udi.home.model.UserItem;
import com.example.a11059.udi.notify.BaseNotify;
import com.example.a11059.udi.notify.Notification;
import com.example.a11059.udi.notify.NotificationCenter;
import com.example.a11059.udi.notify.NotificationDef;
import com.example.a11059.udi.user.OrderInformationModel;
import com.example.a11059.udi.user.ReleaseOrderModel;
import com.example.a11059.udi.utils.BaseFragment;
import com.example.a11059.udi.utils.PromptDialog;
import com.google.repacked.antlr.v4.runtime.atn.SemanticContext;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 11059 on 2016/11/10.
 */
@SuppressLint("ValidFragment")
public class DetailsFragment extends BaseFragment implements BaseNotify{

    private View view;
    private DetailDataBinding dataBinding;
    Notification notification;
    private PromptDialog promptDialog;

    @SuppressLint("ValidFragment")
    public DetailsFragment(Notification notification) {
        this.notification=notification;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.details_act, container, false);
            dataBinding = DataBindingUtil.bind(view);
            NotificationCenter.getNotification().register(NotificationDef.UPDATE_DETAIL, this);
            NotificationCenter.getNotification().register(NotificationDef.ORDER_CLICK, this);

            updateDetail(notification);
        }
        return dataBinding.getRoot();
    }

    @Override
    public void notify(Notification notification) {
        if (notification.id==NotificationDef.UPDATE_DETAIL){
            updateDetail(notification);
        }
        if (notification.id==NotificationDef.ORDER_CLICK){
            promptDialogShow();
        }
    }

    private void promptDialogShow() {
        try {
            promptDialog = new PromptDialog(getContext());
            promptDialog.setTitle(getString(R.string.prompt_tv));
            promptDialog.setMessage("确认接单？");
            promptDialog.setYesOnclickListener(getString(R.string.ok_tv), new PromptDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    promptDialog.dismiss();
                    updateWebOrderInfo();
                }
            });
            promptDialog.setNoOnclickListener(getString(R.string.cancel_tv), new PromptDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    promptDialog.dismiss();
                }
            });
            promptDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void updateWebOrderInfo() {
        ReleaseOrderModel orderModel = new ReleaseOrderModel();
        orderModel.setType("already");
        orderModel.setState("已接单");
        if (!ToolUtils.getUser().getObjectId().isEmpty()){
            orderModel.setOrders(ToolUtils.getUser().getObjectId());
        }
        UserItem userItem= (UserItem) notification.object;
        if (!userItem.getReleaseOrderModel().getObjectId().isEmpty()){
            orderModel.update(userItem.getReleaseOrderModel().getObjectId(), new UpdateListener() {

                @Override
                public void done(BmobException e) {
                    if(e==null){
                        toast(getContext(),"接单成功");
                        Log.i("bmob","更新成功");
                        NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.UPDATE_RELEASE_LIST));
                    }else{
                        Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        }

    }
    private void updateDetail(Notification notification) {
        if (notification==null){
            return;
        }
        this.notification=notification;
        UserItem userItem= (UserItem) notification.object;
        Log.d("demo","DetailsFragment  userItem"+userItem.toString());
        if (!userItem.getReleaseOrderModel().getImgUrl().isEmpty()){
            Glide.with(view.getContext()).load(userItem.getReleaseOrderModel().getImgUrl()).error(R.drawable.login_icon_account).into(dataBinding.hearIcon);
        }
        if (!userItem.getReleaseOrderModel().getAddress().isEmpty()){
            dataBinding.detailAddressTv.setText(userItem.getReleaseOrderModel().getAddress());
        }
        if (!userItem.getReleaseOrderModel().getGmail().isEmpty()){
            dataBinding.detailGmailTv.setText(userItem.getReleaseOrderModel().getGmail());
        }
        if (!userItem.getReleaseOrderModel().getPhone().isEmpty()){
            dataBinding.detailPhoneTv.setText(userItem.getReleaseOrderModel().getPhone());
        }
        if (!userItem.getReleaseOrderModel().getNote().isEmpty()){
            dataBinding.detailNoteTv.setText(userItem.getReleaseOrderModel().getNote());
        }
        if (!userItem.getReleaseOrderModel().getName().isEmpty()){
            dataBinding.detailName.setText(userItem.getReleaseOrderModel().getName());
        }
        if (!userItem.getReleaseOrderModel().getSex().isEmpty()){
            dataBinding.detailSexTv.setText("|  "+userItem.getReleaseOrderModel().getSex());
        }
        if (!userItem.getReleaseOrderModel().getMoney().isEmpty()){
            dataBinding.detailMoneyTv.setText("悬赏："+userItem.getReleaseOrderModel().getMoney());
        }

        if (!userItem.getReleaseOrderModel().getNick().isEmpty()){
            dataBinding.nick.setText(userItem.getReleaseOrderModel().getNick());
        }
        if (userItem.getReleaseOrderModel().getIsUrgent()==1){
            dataBinding.isUrgentImg.setVisibility(View.VISIBLE);
        }else {
            dataBinding.isUrgentImg.setVisibility(View.GONE);
        }
    }
}
