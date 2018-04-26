package com.example.a11059.udi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a11059.udi.take.ItemViewDemo;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;



/**
 * Created by 11059 on 2016/11/10.
 */
public class DetailsActivity extends AppCompatActivity  implements View.OnClickListener{
    private RecyclerView listView;
    private Button chat;
    private String [] list={"快递名称:","取件时间:","取件地址:","取件姓名:","联系电话:","详细信息:"};
    private Button button;//确认代取
    private List<String>  lists;//详细信息
    private Intent intent;//用户名
    private SharedPreferences sp;
    private String persion;
    private int flag;
    private List<String> msgUsers =new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_act);

//        listView= (RecyclerView) findViewById(R.id.details_listview);
//        listView.setLayoutManager(new GridLayoutManager(this,5));
//        DetailsAdapter detailsAdapter=new DetailsAdapter(DetailsActivity.this,list,listView,lists);
//        listView.setAdapter(detailsAdapter);


//        LineBreakLayout lineBreakLayout= (LineBreakLayout) findViewById(R.id.lineBreakLayout);

        ItemViewDemo lineBreakLayout= (ItemViewDemo) findViewById(R.id.lineBreakLayout);
        List<String> lable = new ArrayList<>();
        lable.add("易碎");
        lable.add( "加急");
        lable.add("超大");
        lable.add("赏金多");
      init();
     getAllMessage();

//设置标签
        lineBreakLayout.setlables(lable,true);
//        lineBreakLayout.setLables(lable, true);
//获取选中的标签
//        List<String> selectedLables = lineBreakLayout.getSelectedLables();

    }

    private void init() {
        intent=getIntent();
        persion=intent.getStringExtra("name");
//        flag=intent.getIntExtra("flag",0);
        button= (Button) findViewById(R.id.details_neg);
        chat= (Button) findViewById(R.id.btu_chat);
        chat.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case  R.id.details_neg:
                AlertDialog alertDialog= new AlertDialog.Builder(DetailsActivity.this).
                        setIcon(R.mipmap.ic_launcher).setMessage("是否抢单？").setTitle("优你U递").setPositiveButton("是",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("再考虑一下", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            case R.id.btu_chat:
//                chatWith();
                // 进入主页面
                Intent intent = new Intent(DetailsActivity.this,
                        ChatActivity.class);
//                    Bundle bundle=intent.getExtras();
                intent.putExtra("name",persion);
                if (msgUsers.contains(persion)) {
                    intent.putExtra("flag", 1);
                }else {
                    intent.putExtra("flag", 2);
                }
                startActivity(intent);
                finish();
                break;

        }

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
            String msgUser=entry.getKey();
            msgUsers.add(msgUser);
        }

    }

    private void chatWith() {

        sp= getSharedPreferences("activity", Context.MODE_PRIVATE);
        String name= sp.getString("name","空");
        String pwd=sp.getString("passward","空");
        System.out.println("2222222222222shrea"+name);
            EMClient.getInstance().login(name, pwd, new EMCallBack() {
                @Override
                public void onSuccess() {
                    Log.d("tag", "login:2222222222222222222 onSuccess");

//                        Toast.makeText(MainActivity.this,"登录",Toast.LENGTH_SHORT).show();
//                        if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
//                            pd.dismiss();
//                        }

                    // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                    // ** manually load all local groups and
//                        EMClient.getInstance().groupManager().loadAllGroups();
//                        EMClient.getInstance().chatManager().loadAllConversations();
//                        getFriends();

                    // 进入主页面
                    Intent intent = new Intent(DetailsActivity.this,
                            ChatActivity.class);
//                    Bundle bundle=intent.getExtras();
                    intent.putExtra("name",persion);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onProgress(int progress, String status) {
                    Log.d("tag", "login: onProgress");
                }

                @Override
                public void onError(final int code, final String message) {
                    Log.d("tag", "login:1111111111111111111 onError: " + code);
//                        Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_SHORT).show();
//                        if (!progressShow) {
//                            return;
//                        }
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), "登录失败" + message,
                                        Toast.LENGTH_SHORT).show();
//                            }
//                        });
                }
            });
        }


}
