package com.example.a11059.udi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a11059.udi.ChatActivity;
import com.example.a11059.udi.R;
import com.example.a11059.udi.model.ChatUser;

import java.util.List;


/**
 * Created by 11059 on 2016/12/14.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private ChatActivity chatActivity;
    private List<ChatUser> chatUsers;
    public ChatAdapter(ChatActivity chatActivity, List<ChatUser> date) {
        this.chatActivity=chatActivity;
        this.chatUsers=date;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType==1){
//            MyViewHolder viewhead= new MyViewHolder(LayoutInflater.from(chatActivity).
//                    inflate(R.layout.chat_item,null));
//            return viewhead;
//        }
            MyViewHolder view= new MyViewHolder(LayoutInflater.from(chatActivity).
                    inflate(R.layout.chat_item,null));

        return view;
    }

    @Override
    public int getItemViewType(int position) {
//        if (position==0){
//            return 1;
//        }else
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(MyViewHolder  holder, int position) {
                   if (chatUsers.get(position).getFlag()!=1){
                      holder.right.setVisibility(View.GONE);
                       holder.right_img.setVisibility(View.GONE);
                       holder.left_img.setVisibility(View.VISIBLE);
                       holder.left.setVisibility(View.VISIBLE);
                       holder.left.setText(chatUsers.get(position).getMessage());
                   }else {
                       holder.left.setVisibility(View.GONE);
                       holder.left_img.setVisibility(View.GONE);
                       holder.right_img.setVisibility(View.VISIBLE);
                       holder.right.setVisibility(View.VISIBLE);
                       holder.right.setText(chatUsers.get(position).getMessage());
                   }
    }

    @Override
    public int getItemCount() {
        return chatUsers.size();
    }
    public class  MyViewHolder extends RecyclerView.ViewHolder{
       TextView left;
        TextView right;
        ImageView left_img;
        ImageView right_img;
        public MyViewHolder(View itemView) {
            super(itemView);
            left= (TextView) itemView.findViewById(R.id.left_msg);
            right= (TextView) itemView.findViewById(R.id.right_msg);
            left_img= (ImageView) itemView.findViewById(R.id.chat_picture);
            right_img= (ImageView) itemView.findViewById(R.id.chat_right);
        }
    }
}
