package com.example.a11059.udi.take;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.a11059.udi.MainActivity;
import com.example.a11059.udi.R;
import com.example.a11059.udi.ToolUtils;
import com.example.a11059.udi.adapter.DataBaseAdapter;
import com.example.a11059.udi.bomb.MyUser;
import com.example.a11059.udi.databinding.DataBinding;
import com.example.a11059.udi.home.model.UserItem;
import com.example.a11059.udi.model.MsgUser;
import com.example.a11059.udi.model.TakeUser;
import com.example.a11059.udi.notify.BaseNotify;
import com.example.a11059.udi.notify.Notification;
import com.example.a11059.udi.notify.NotificationCenter;
import com.example.a11059.udi.notify.NotificationDef;
import com.example.a11059.udi.user.OrderInformationModel;
import com.example.a11059.udi.user.ReleaseOrderModel;
import com.example.a11059.udi.user.User;
import com.example.a11059.udi.utils.BaseFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by 11059 on 2016/11/6.
 */
public class Take extends BaseFragment implements TabLayout.OnTabSelectedListener, EMMessageListener,BaseNotify {
    private View view;

    private RecyclerView recyclerView;
    private MainActivity mainActivity;
    private Handler handler;
    private Button getMessage;
    private List<MsgUser> msgUsers = new ArrayList<>();
    private int count;    List<UserItem> userItems=new ArrayList<>() ;
    DataBinding dataBinding;
    private DataBaseAdapter dataBaseAdapter;

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("demo", "onCreateView" );
        if (view==null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.take, container, false);
            dataBinding = DataBindingUtil.bind(view);
            setTab();
            dataBaseAdapter = new DataBaseAdapter(userItems, getContext());
            dataBinding.listView.setAdapter(dataBaseAdapter);
            NotificationCenter.getNotification().register(NotificationDef.UPDATA_USERINFO, this);
            NotificationCenter.getNotification().register(NotificationDef.LOGIN_OUT_CLICK, this);
            NotificationCenter.getNotification().register(NotificationDef.UPDATE_RELEASE_LIST, this);
            bindRefresh();
            updateUserInfo();
            requestReleaseData();
        }

        return dataBinding.getRoot();
    }

    private void bindRefresh() {
        dataBinding.listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                requestReleaseData();
            }
        });
    }

    private void requestReleaseData() {
        BmobQuery<ReleaseOrderModel> query = new BmobQuery<ReleaseOrderModel>();
        query.addWhereEqualTo("type", "OrderInformationModel");
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
                    dataBinding.listView.onRefreshComplete();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //得到所有消息
//        getAllMessage();
        Log.d("demo", "onStart" );

    }

    @Override
    public void notify(Notification notification) {
        Log.d("demo", "take notify" );
        if (notification.id==NotificationDef.UPDATA_USERINFO||notification.id==NotificationDef.LOGIN_OUT_CLICK){
                updateUserInfo();
            }
        if (notification.id==NotificationDef.UPDATE_RELEASE_LIST){
            requestReleaseData();
            }
    }

    private void updateUserInfo() {
        Log.e("demo","vupdateUserInfo");
        MyUser userInfo =ToolUtils.getUser();
        if(userInfo != null){
            // 允许用户使用应用
            Log.e("demo","允许用户使用应用"+userInfo.toString());
            dataBinding.unloginTv.setVisibility(View.GONE);
            if (userInfo.getImgUrl()!=null){
                Log.e("demo"," take updateUserInfo"+userInfo.getImgUrl());
                Glide.with(this).load(userInfo.getImgUrl()).into(dataBinding.userHeaderImg);
            }
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            Log.e("demo","缓存用户对象为空时");
            dataBinding.unloginTv.setVisibility(View.VISIBLE);
            dataBinding.userHeaderImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_user));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("demo","onPause");
    }

    private void getAllMessage() {

        //获取所有会话
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        //遍历map集合
        Set<Map.Entry<String, EMConversation>> set = conversations.entrySet();
        for (Iterator<Map.Entry<String, EMConversation>> it = set.iterator(); it.hasNext(); ) {
            Map.Entry<String, EMConversation> entry = it.next();
            //得到每个会话人的未读消息
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(entry.getKey());
            int num = conversation.getUnreadMsgCount();
            System.out.println("key:" + entry.getKey() + "他未读消息的值为：" + num);
            MsgUser msgUser = new MsgUser(entry.getKey(), num);
            if (count < 99) {
                count += num;
            } else {
                count = 99;
            }
            msgUsers.add(msgUser);
        }
        if (msgUsers.size() > 0) {
            Message message = new Message();
            message.what = 2;
            message.obj = count;
            handler.sendMessage(message);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(this);
        Log.d("demo","take onDestroy"+this);
    }

    private void initBut() {

//
//        dataBinding.btuMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 进入聊天列表
//                Intent intent = new Intent(getActivity(),
//                        MessageList.class);
//                getMessage.setBackgroundResource(R.drawable.icon_message_normal);
//                getMessage.setText("");
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("per", (ArrayList<? extends Parcelable>) msgUsers);
////                bundle.putString("per","sdsds");
//                intent.putExtra("meg", bundle);
////                intent.putExtra(Contacts.Intents.UI.LIST_ALL_CONTACTS_ACTION, mSelectList);
////                intent.putExtra("name",msgUsers);
//                startActivity(intent);
//
//            }
//        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setTab() {
//        tabLayout = (TabLayout) view.findViewById(R.id.take_tab);
        dataBinding.takeTab.setTabMode(TabLayout.MODE_FIXED);
        dataBinding.takeTab.setOnTabSelectedListener(this);
        //获取图片资源
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.mipmap.icon_cart);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
        SpannableString spannableString = new SpannableString("D");
        spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        dataBinding.takeTab.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.tv_color)));
        dataBinding.takeTab.setScrollBarSize(16);
        dataBinding.takeTab.addTab( dataBinding.takeTab.newTab().setText("首页"));
        dataBinding.takeTab.addTab( dataBinding.takeTab.newTab().setText("大小"));
        dataBinding.takeTab.addTab( dataBinding.takeTab.newTab().setText("价格"));
        dataBinding.takeTab.addTab( dataBinding.takeTab.newTab().setText("加急"));
    }


    private void init(List<TakeUser> strings) {

    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int f = tab.getPosition();

        switch (f) {
            case 0:
                List<TakeUser> strings = new ArrayList<>();
                TakeUser takeUser = new TakeUser();
                takeUser.setHome("西邮东门-》西邮西门\ndsds");
                takeUser.setName("jipeng");
                strings.add(takeUser);

                TakeUser takeUser1 = new TakeUser();
                takeUser1.setHome("西邮东门-》西邮西门\ndsds");
                takeUser1.setName("song");
                strings.add(takeUser1);
                System.out.println("数据大小" + strings.size());
                init(strings);

                break;
            case 1:
                List<TakeUser> string1 = new ArrayList<>();
                init(string1);
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("demo","onResume");
        EMClient.getInstance().chatManager().addMessageListener(this);
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        for (final EMMessage emMessage : list) {
            Message message = new Message();
            message.what = 1;
            message.obj = emMessage;
            handler.sendMessage(message);
        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

}