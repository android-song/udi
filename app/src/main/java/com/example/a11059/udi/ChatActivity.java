package com.example.a11059.udi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.ArrayList;
import java.util.List;

import adapter.ChatAdapter;
import model.ChatUser;

/**
 * Created by 11059 on 2016/12/14.
 */
public class ChatActivity extends AppCompatActivity implements EMMessageListener {
    private Button send;
    private EditText message;
    private RecyclerView chat_recyclerView;
    private String meg;
    private Handler handler;
    private List<ChatUser>  date;
    private ChatAdapter chatAdapter;
    private SharedPreferences sp;
    private Intent intent;
    private String persion;
    private SharedPreferences getSp;
    private  EMConversation conversation;
    private  int flag;
    private String msgId = null;

    private String mesg_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        init();
        //设置
        if (flag==1) {
            getHistory();
//            getSp=getSharedPreferences("activity", Context.MODE_PRIVATE);
//            getAllMessage();
//            setList();
        }
    }

    private void setList() {

        List<EMMessage> message2 = conversation.getAllMessages();

        for (EMMessage emMessage:message2){
            ChatUser chatUser=new ChatUser();
            chatUser.setMessage(((EMTextMessageBody) emMessage.getBody()).getMessage());
            if (emMessage.getFrom().equals(getSp.getString("name","null")))
            {
                chatUser.setFlag(1);
            }else {
                chatUser.setFlag(2);
            }
            date.add(chatUser);
        }
        chatAdapter.notifyDataSetChanged();
    }

    protected void getAllMessage() {
        // 获取当前conversation对象
        if (!persion.equals(getSp.getString("name","null"))) {
            conversation = EMClient.getInstance().chatManager().getConversation(persion);
            // 把此会话的未读数置为0
            conversation.markAllMessagesAsRead();
            // 初始化db时，每个conversation加载数目是getChatOptions().getNumberOfMessagesLoaded
            // 这个数目如果比用户期望进入会话界面时显示的个数不一样，就多加载一些
            final List<EMMessage> msgs = conversation.getAllMessages();
            int msgCount = msgs != null ? msgs.size() : 0;
            if (msgCount < conversation.getAllMsgCount() && msgCount < 20) {
                String msgId = null;
                if (msgs != null && msgs.size() > 0) {
                    msgId = msgs.get(0).getMsgId();
                }
                conversation.loadMoreMsgFromDB(msgId, 20 - msgCount);

            }
        }

    }

    private void getHistory() {

        // 获取当前conversation对象


//获取此会话的所有消息

        getSp=getSharedPreferences("activity", Context.MODE_PRIVATE);
//SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
//获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
//        List<EMMessage> mes = conversation.loadMoreMsgFromDB(messages.get(2).getMsgId(), 20);

        conversation = EMClient.getInstance().chatManager().getConversation(persion);
        conversation.markAllMessagesAsRead();
//获取此会话的所有消息
        if (!persion.equals(getSp.getString("name","null"))) {
            List<EMMessage> messages = conversation.getAllMessages();
//            if (mesg_id.equals("no")) {
            if (messages.size()>0)
                  mesg_id = messages.get(0).getMsgId();
            //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
//获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
            List<EMMessage> messages1 = conversation.loadMoreMsgFromDB(mesg_id, 19);
            List<EMMessage> message2 = conversation.getAllMessages();
//            conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
            if (message2.size()>0) {
                for (EMMessage emMessage : message2) {
                    ChatUser chatUser = new ChatUser();
                    chatUser.setMessage(((EMTextMessageBody) emMessage.getBody()).getMessage());
                    if (emMessage.getFrom().equals(getSp.getString("name", "null"))) {
                        chatUser.setFlag(1);
                    } else {
                        chatUser.setFlag(2);
                    }
                    date.add(chatUser);
                }

                chatAdapter.notifyDataSetChanged();
            }
        }




    }

    private void init() {


       intent=getIntent();
        persion=intent.getStringExtra("name");
        flag=intent.getIntExtra("flag",0);
        date=new ArrayList<>();
        send= (Button) findViewById(R.id.send_message);
        message= (EditText) findViewById(R.id.message);
        chat_recyclerView= (RecyclerView) findViewById(R.id.chat_recyclerView);
        chat_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerview自动跳转到底部
//        chat_recyclerView.smoothScrollToPosition(date.size() - 1);
        chatAdapter=new ChatAdapter(this,date);
        chat_recyclerView.setAdapter(chatAdapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meg=message.getText().toString().trim();
                if (!meg.equals("")) {
                    chatMessage(meg);
                    message.setText("");
                }

            }
        });


        //消息接收
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        EMMessage emMessage= (EMMessage) msg.obj;
                        String cont = ((EMTextMessageBody) emMessage.getBody()).getMessage();
                        ChatUser chatUser=new ChatUser();
                        chatUser.setFlag(2);
                        System.out.println("fff"+cont);
                        chatUser.setMessage(cont);
                        date.add(chatUser);
                        chatAdapter.notifyDataSetChanged();
//                        chat.setText(msg.obj.toString());


                        break;
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void chatMessage(String meg) {
//创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(meg,persion);
        ChatUser chatUser=new ChatUser();
        chatUser.setFlag(1);
        System.out.println("fff"+meg);
        chatUser.setMessage(meg);
        date.add(chatUser);
        chatAdapter.notifyDataSetChanged();
//        chat.setText(stringBuilder.toString());
//如果是群聊，设置chattype，默认是单聊
//                if (chatType == CHATTYPE_GROUP)
//                    message.setChatType(EMMessage.ChatType.GroupChat);
//发送消息
        EMClient.getInstance().chatManager().sendMessage(message);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(this);
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        for (final EMMessage emMessage : list) {

            Message message = new Message();
            message.what = 1;
            message.obj = emMessage;
            handler.sendMessage(message);
            //把一条消息置为已读
            conversation.markMessageAsRead(emMessage.getMsgId());
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

    @Override
    protected void onRestart() {
        super.onRestart();
    }

}
