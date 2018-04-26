package com.example.a11059.udi.adapter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a11059.udi.DetailsActivity;
import com.example.a11059.udi.MainActivity;
import com.example.a11059.udi.R;
import com.example.a11059.udi.TypeViewHolder;
import com.example.a11059.udi.model.MsgUser;
import com.example.a11059.udi.model.TakeUser;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by 11059 on 2016/11/7.
 */
public class TakeAdapter {
//    private List<TakeUser> list;
//    private int pos;
//    private int flag;
//
//    private MainActivity mainActivity;
//    private  MyItemListener myItemListener;
//    private List<MsgUser> msgUsers =new ArrayList<>();
//
//    public interface MyItemListener {
//        public void OnItemClick(View view, int position);
//    }
//
//    public void setMyItemListener(MyItemListener myItemListener) {
//        this.myItemListener = myItemListener;
//    }
//    public TakeAdapter(List<TakeUser> strings, FragmentActivity mainActivityClass) {
//        this.list=strings;
//        this.mainActivity=(MainActivity) mainActivityClass;
////        this.msgUsers=msgUsers;
//        ;
//    }
//
//    @Override
//    public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        MyTakeViewHolder myTakeViewHolder=new MyTakeViewHolder(LayoutInflater.
//                from(mainActivity).inflate(R.layout.take_item,parent,false));
//        return myTakeViewHolder;
//
//    }
//
//    @Override
//    public void onBindViewHolder(TypeViewHolder holder, int position) {
//               pos=position;
//        holder.bindHolder();
//
//    }
//
//    private void replace() {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//class    MyTakeViewHolder extends  TypeViewHolder {
// private    TextView textView;
//    private TextView name;
//    private TextView takeBut;
//    public MyTakeViewHolder(View itemView) {
//        super(itemView);
//        takeBut= (TextView) itemView.findViewById(R.id.take_but);
//       textView= (TextView) itemView.findViewById(R.id.student_id);
//        name= (TextView) itemView.findViewById(R.id.take_name);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("11111111111",name.getText().toString());
//                Intent intent=new Intent(mainActivity, DetailsActivity.class);
////                Bundle bundle=intent.getExtras();
//
//                String nm=name.getText().toString();
//                intent.putExtra("name",nm);
//                mainActivity.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public void bindHolder() {
//             name.setText(list.get(pos).getName());
//             textView.setText(list.get(pos).getHome());
//    }
//}
}
