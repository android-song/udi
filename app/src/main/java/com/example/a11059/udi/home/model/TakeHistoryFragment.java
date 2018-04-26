package com.example.a11059.udi.home.model;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a11059.udi.R;
import com.example.a11059.udi.ToolUtils;
import com.example.a11059.udi.adapter.DataBaseAdapter;
import com.example.a11059.udi.adapter.HistoryOrderAdapter;
import com.example.a11059.udi.databinding.HistoryDataBinding;
import com.example.a11059.udi.notify.BaseNotify;
import com.example.a11059.udi.notify.Notification;
import com.example.a11059.udi.notify.NotificationCenter;
import com.example.a11059.udi.notify.NotificationDef;
import com.example.a11059.udi.user.ReleaseOrderModel;
import com.example.a11059.udi.utils.BaseFragment;
import com.example.a11059.udi.utils.PromptDialog;

import org.jsoup.Connection;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2018/4/26.
 */

public class TakeHistoryFragment extends BaseFragment implements BaseNotify {
    private View view;
    private  HistoryDataBinding dataBinding;
    private List<UserItem> userItems=new ArrayList<>();
    private HistoryOrderAdapter dataBaseAdapter;
    private PromptDialog promptDialog;
    private Notification notificatio;
    private String type;
    private int fragmentType;

    @Override
    public void notify(Notification notification) {
        if (notification.id==NotificationDef.UPDATE_RELEASE_LIST){
            requestReleaseData();
        }
        if (notification.id==NotificationDef.HAVE_ORDER_STATE){
            this.notificatio=notification;
            promptDialogShow();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.take_history_layout, container, false);
            dataBinding = DataBindingUtil.bind(view);
            dataBaseAdapter= new HistoryOrderAdapter(userItems, getContext());
            dataBinding.listView.setAdapter(dataBaseAdapter);
            NotificationCenter.getNotification().register(NotificationDef.UPDATA_USERINFO, this);
            NotificationCenter.getNotification().register(NotificationDef.LOGIN_OUT_CLICK, this);
            NotificationCenter.getNotification().register(NotificationDef.UPDATE_RELEASE_LIST, this);
            NotificationCenter.getNotification().register(NotificationDef.HAVE_ORDER_STATE, this);
            requestReleaseData();
        }
        return dataBinding.getRoot() ;
    }

    private void promptDialogShow() {
        try {
            promptDialog = new PromptDialog(getContext());
            promptDialog.setTitle(getString(R.string.prompt_tv));
            UserItem userItem= (UserItem) notificatio.object;
            if ("已接单".equals(userItem.getReleaseOrderModel().getState())&&fragmentType==NotificationDef.HISTORYFRAGMENT_SHOW){
             type="更改订单状态为`已送达`？";
            }else if ("已送达".equals(userItem.getReleaseOrderModel().getState())&&fragmentType==NotificationDef.RELEASEFRAGMENT_SHOW){
                type="确认接收";
            }else {
                return;
            }
            promptDialog.setMessage(type);
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
        UserItem userItem= (UserItem) notificatio.object;
        if ("已接单".equals(userItem.getReleaseOrderModel().getState())){
            orderModel.setState("已送达");
        }else {
            orderModel.setState("订单完成");
        }
        orderModel.setType("already");
        if (!userItem.getReleaseOrderModel().getObjectId().isEmpty()){
            orderModel.update(userItem.getReleaseOrderModel().getObjectId(), new UpdateListener() {

                @Override
                public void done(BmobException e) {
                    if(e==null){
                        toast(getContext(),"状态更改完成");
                        Log.i("bmob","更新成功");
                        NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.UPDATE_RELEASE_LIST));
                    }else{
                        Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        }

    }
    private void requestReleaseData() {
        BmobQuery<ReleaseOrderModel> query = new BmobQuery<ReleaseOrderModel>();
        if (ToolUtils.getUser()==null){
            return;
        }
        if (fragmentType==NotificationDef.HISTORYFRAGMENT_SHOW){
            query.addWhereEqualTo("orders", ToolUtils.getUser().getObjectId());
        }
        if (fragmentType==NotificationDef.RELEASEFRAGMENT_SHOW){
            query.addWhereEqualTo("myUser", ToolUtils.getUser().getObjectId());
        }
        query.setLimit(50);
        query.findObjects(new FindListener<ReleaseOrderModel>() {
            @Override
            public void done(List<ReleaseOrderModel> object, BmobException e) {
                if(e==null){
                    userItems.clear();
                    for (ReleaseOrderModel orderModel : object) {
                        userItems.add(new UserItem(orderModel));
                    }
                    Log.e("demo","查询成功：共"+object.size()+"条数据。"+"userItems size"+userItems.size());
                    dataBaseAdapter.addData(userItems);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    public void setNotification(int historyfragmentShow) {
        fragmentType=historyfragmentShow;


    }
}
