package com.example.a11059.udi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.ChangeAdapter;
import home.DetailsHttpRequest;

/**
 * Created by 11059 on 2016/11/10.
 */
public class ChangeActivity extends AppCompatActivity {
   private RecyclerView recyclerView;
    private List<String> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.change_act);
        init();
    }

    private void init() {
list=new ArrayList<>();
        list.add("姓名");
        list.add("性别");
        list.add("学校");
        list.add("专业班级");
        list.add("签名");
        list.add("电话");
        list.add("社交账号");
        list.add("密码");


        recyclerView= (RecyclerView) findViewById(R.id.change_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ChangeAdapter changeAdapter=new ChangeAdapter(list,ChangeActivity.this);
        recyclerView.setAdapter(changeAdapter);
        DetailsHttpRequest detailsHttpRequest=new DetailsHttpRequest(4);
        detailsHttpRequest.start();
    }
}
