package com.example.a11059.udi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.a11059.udi.adapter.HistoryAdapter;
import com.example.a11059.udi.home.DetailsHttpRequest;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by 11059 on 2016/11/11.
 */
public class HistoryActivity extends AppCompatActivity {
    private List<String> list;
    private RecyclerView recyclerView;
    private int i=0;
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.history_atc);
        Intent intent=getIntent();
       Bundle bundle= intent.getExtras();
        i=  bundle.getInt("flag");
        System.out.println(i+"kkkkkkkkkkkkkkkkkkkkkkkkkk");
        init();
    }
    private void init() {
        list=new ArrayList<>();
        System.out.println(i+"kkkkkkkkkkkkkkkkkkkkkkkkkk");
       textView= (TextView) findViewById(R.id.history_text);
        switch (i){
            case 1:
                textView.setText("取件历史");
//                DetailsHttpRequest detailsHttpRequest=new DetailsHttpRequest(1);
//                detailsHttpRequest.start();
                break;
            case 2:
                textView.setText("我发布的");
//                DetailsHttpRequest detailsHttpRequest1=new DetailsHttpRequest(2);
//                detailsHttpRequest1.start();
                break;
            case 3:
                textView.setText("系统通知");
//                DetailsHttpRequest detailsHttpRequest2=new DetailsHttpRequest(1);
//                detailsHttpRequest2.start();
                break;


        }
        recyclerView= (RecyclerView) findViewById(R.id.history_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HistoryAdapter changeAdapter=new HistoryAdapter(list,HistoryActivity.this);
        recyclerView.setAdapter(changeAdapter);


    }
}
