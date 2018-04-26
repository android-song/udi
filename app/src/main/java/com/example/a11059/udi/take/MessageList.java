package com.example.a11059.udi.take;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.a11059.udi.R;
import com.example.a11059.udi.adapter.MesAdapter;
import com.example.a11059.udi.model.MsgList;
import com.example.a11059.udi.model.MsgUser;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by 11059 on 2016/12/15.
 */
public class MessageList extends AppCompatActivity {
    private Intent intent;
    private Bundle bundle;
    private List<MsgUser> msgUsers;
    private Map<String,List<String>> stringListMap;
   private List<String> strings;
    private  List<MsgList>  username  =new ArrayList<>();;
    private RecyclerView recyclerView;
    private MesAdapter mesAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);
        setUserList();
        init();
        
    }

    private void init() {
        msgUsers=new ArrayList<>();
        stringListMap=new HashMap<>();
        intent=getIntent();
        bundle=intent.getBundleExtra("meg");
      msgUsers= (List<MsgUser>) bundle.get("per");

        for (MsgUser msgUser:msgUsers ){
            if(stringListMap.containsKey(msgUser.getForm())){
                System.out.println("有这个数据");
                strings.add(msgUser.getContent());
                stringListMap.put(msgUser.getForm(),strings);
            }else {
              strings=new ArrayList<>();
                strings.add(msgUser.getContent());
                stringListMap.put(msgUser.getForm(),strings);
//                new HashMap<String,String>().put(msgUser.getForm(),msgUser.getContent());
                System.out.println("mei有这个数据");
            }
        }
        //遍历map集合
        Set<Map.Entry<String, List<String>>> set=stringListMap.entrySet();
        for (Iterator<Map.Entry<String,List<String>>> it=set.iterator();it.hasNext();){
            Map.Entry<String,List<String>> entry=it.next();
            //得到每个会话人的未读消息
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(entry.getKey());
            int num= conversation.getUnreadMsgCount();
            System.out.println("key:"+entry.getKey()+"他未读消息的值为："+num);
            MsgList msgUser=new MsgList();
            msgUser.setName(entry.getKey());
            msgUser.setSize(num);
//            msgUser.setTime();
            username.add(msgUser);
        }
   mesAdapter.notifyDataSetChanged();
    }

    private void setUserList() {
        recyclerView= (RecyclerView) findViewById(R.id.meg_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mesAdapter=new MesAdapter(username, MessageList.this);
        recyclerView.setAdapter(mesAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
