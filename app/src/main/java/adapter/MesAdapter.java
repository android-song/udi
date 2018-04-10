package adapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a11059.udi.ChatActivity;
import com.example.a11059.udi.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;
import java.util.List;

import model.MsgList;
import take.MessageList;

/**
 * Created by 11059 on 2016/12/18.
 */
public class MesAdapter extends RecyclerView.Adapter<MesAdapter.MesViewHolder> {
    private List<MsgList> list;
    private MessageList messageList;
    public MesAdapter(List<MsgList> username, MessageList messageList) {
       this.list=username;
        this.messageList=messageList;
    }

    @Override
    public MesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MesViewHolder mesViewHolder=new MesViewHolder(LayoutInflater.from(messageList).
                inflate(R.layout.msg_item,parent,false));
        return mesViewHolder;
    }

    @Override
    public void onBindViewHolder(final MesViewHolder holder, final int position) {

             holder.textView.setText("与"+list.get(position).getName()+"聊天");
        if (list.get(position).getSize()>0){
            holder.number.setVisibility(View.VISIBLE);
            holder.number.setText(""+list.get(position).getSize());
        }else {
            holder.number.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(messageList,
                        ChatActivity.class);
                intent.putExtra("name",list.get(position).getName());
                intent.putExtra("flag",1);
                holder.number.setVisibility(View.INVISIBLE);
                EMConversation conversation = EMClient.getInstance().chatManager().
                        getConversation(list.get(position).getName());
                //所有未读消息数清零
                EMClient.getInstance().chatManager().markAllConversationsAsRead();
                messageList.startActivity(intent);
//                messageList.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MesViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        TextView number;
        public MesViewHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.mes_username);
            imageView= (ImageView) itemView.findViewById(R.id.mes_picture);
            number= (TextView) itemView.findViewById(R.id.mes_number);
        }
    }
}
