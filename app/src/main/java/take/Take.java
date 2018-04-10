package take;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a11059.udi.MainActivity;
import com.example.a11059.udi.R;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import adapter.TakeAdapter;
import model.MsgList;
import model.MsgUser;
import model.TakeUser;

/**
 * Created by 11059 on 2016/11/6.
 */
public class Take extends Fragment implements TabLayout.OnTabSelectedListener, EMMessageListener {
    private  View view;
    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private MainActivity mainActivity;
    private Handler handler;
    private Button getMessage;
    private List<MsgUser> msgUsers =new ArrayList<>();
    private  int count;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.take,null);
         initBut();
        setTab();



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("lllonstart");
        //得到所有消息
        getAllMessage();
    }

    private void getAllMessage() {

        //获取所有会话
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        //遍历map集合
        Set<Map.Entry<String,EMConversation>> set=conversations.entrySet();
        for (Iterator<Map.Entry<String,EMConversation>> it = set.iterator(); it.hasNext();){
            Map.Entry<String,EMConversation> entry=it.next();
            //得到每个会话人的未读消息
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(entry.getKey());
           int num= conversation.getUnreadMsgCount();
            System.out.println("key:"+entry.getKey()+"他未读消息的值为："+num);
            MsgUser msgUser=new MsgUser(entry.getKey(),num);
            if (count<99){
                count+=num;
            }else {
                count=99;
            }
            msgUsers.add(msgUser);
//            MsgList msgUser=new MsgList();
//            msgUser.setName(entry.getKey());
//            msgUser.setSize(entry.getValue().size());
////            msgUser.setTime();
//            username.add(msgUser);
        }
   if(msgUsers.size()>0){
       Message message=new Message();
       message.what=2;
       message.obj=count;
       handler.sendMessage(message);
   }
    }

    private void initBut() {

        getMessage= (Button) view.findViewById(R.id.btu_message);
        getMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入聊天列表
                Intent intent = new Intent(getActivity(),
                        MessageList.class);
                getMessage.setBackgroundResource(R.drawable.icon_message_normal);
                getMessage.setText("");
                    Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("per",(ArrayList<? extends Parcelable>)msgUsers);
//                bundle.putString("per","sdsds");
                intent.putExtra("meg",bundle);
//                intent.putExtra(Contacts.Intents.UI.LIST_ALL_CONTACTS_ACTION, mSelectList);
//                intent.putExtra("name",msgUsers);
                startActivity(intent);

            }
        });
    }

    private void setTab() {



       tabLayout= (TabLayout) view.findViewById(R.id.take_tab);
         tabLayout.setTabMode(TabLayout.MODE_FIXED);
         tabLayout.setOnTabSelectedListener(this);
        //获取图片资源
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.mipmap.icon_cart);


//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
//        SpannableString spannableString = new SpannableString(" ");
//        spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);




        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ImageSpan imageSpan=new ImageSpan(drawable,ImageSpan.ALIGN_BOTTOM);
        SpannableString spannableString=new SpannableString("D");
        spannableString.setSpan(imageSpan,0,1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tabLayout.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.winte)));
        tabLayout.setScrollBarSize(20);
        tabLayout.addTab(tabLayout.newTab().setText("首页"));
        tabLayout.addTab(tabLayout.newTab().setText("大小排序"));
        tabLayout.addTab(tabLayout.newTab().setText("价格排序"));
        tabLayout.addTab(tabLayout.newTab().setText("加急物件"));
//        actionBar.setNavigationMode();

    }




    private void init(List<TakeUser> strings) {

        recyclerView= (RecyclerView) view.findViewById(R.id.take_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final TakeAdapter takeAdapter=new TakeAdapter(strings,getActivity());
        recyclerView.setAdapter(takeAdapter);

        //消息接收
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        EMMessage emMessage= (EMMessage) msg.obj;
                        String cont = ((EMTextMessageBody) emMessage.getBody()).getMessage();
                        EMConversation conversation = EMClient.getInstance().chatManager().
                                getConversation(emMessage.getFrom());
//获取此会话在本地的所有的消息数量
                        conversation.getAllMsgCount();
//如果只是获取当前在内存的消息数量，调用
                        emMessage.getFrom();
                        conversation.getAllMessages().size();
                         getMessage.setBackgroundResource(R.drawable.icon_message_press);

                        //发消息人的名称
                        Object o=new Object();

                        MsgUser msgUser=new MsgUser(emMessage.getFrom(),
                                ((EMTextMessageBody) emMessage.getBody()).getMessage(),
                                conversation.getAllMessages().size());
                        msgUsers.add(msgUser);
                        System.out.println(emMessage.getFrom()+"1eeee"+emMessage.toString()+  conversation.getAllMessages().size());
//                        chat.setText(msg.obj.toString());
                        //把一条消息置为已读
//                        conversation.markMessageAsRead(emMessage.getMsgId());

                        break;
                    case 2:
                        if (count>0) {
                            getMessage.setBackgroundResource(R.drawable.icon_message_press);
                            getMessage.setText("" + count);
                        }
                        break;
                }
            }
        };
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int f=tab.getPosition();

        switch (f){
            case 0:
                List<TakeUser> strings=new ArrayList<>();
                TakeUser takeUser=new TakeUser();
                takeUser.setHome("西邮东门-》西邮西门\ndsds");
                takeUser.setName("jipeng");
                strings.add(takeUser);

                TakeUser takeUser1=new TakeUser();
                takeUser1.setHome("西邮东门-》西邮西门\ndsds");
                takeUser1.setName("song");
                strings.add(takeUser1);
                System.out.println("数据大小"+strings.size());
                init(strings);

            break;
            case 1:
                List<TakeUser> string1=new ArrayList<>();
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
        EMClient.getInstance().chatManager().addMessageListener(this);
        System.out.println("aaaaaaaaaaaonResume");
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        for (final EMMessage emMessage : list) {
            Log.i("222222222", ((EMTextMessageBody) emMessage.getBody()).getMessage());

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