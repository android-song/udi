package adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a11059.udi.ChangeActivity;
import com.example.a11059.udi.DetailsActivity;
import com.example.a11059.udi.R;
import com.example.a11059.udi.TypeViewHolder;

import java.util.List;

/**
 * Created by 11059 on 2016/11/7.
 */
public class ChangeAdapter extends RecyclerView.Adapter<TypeViewHolder> {
    private List<String> list;
    private int pos;

    private ChangeActivity mainActivity;
    private  MyItemListener myItemListener;

    public ChangeAdapter(List<String> list, ChangeActivity mainActivityClass) {
        this.mainActivity=mainActivityClass;
        this.list=list;
    }


    public interface MyItemListener {
        public void OnItemClick(View view, int position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0)
            return 1;

        return super.getItemViewType(position);
    }

    public void setMyItemListener(MyItemListener myItemListener) {
        this.myItemListener = myItemListener;
    }


    @Override
    public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==1) {
            MyTakeViewHolder myTakeViewHolder = new MyTakeViewHolder(LayoutInflater.from(mainActivity).inflate(R.layout.change_item, parent, false));
            return myTakeViewHolder;
        }
       ChangeItem changeItem=new ChangeItem(LayoutInflater.from(mainActivity).inflate(R.layout.change_item1,parent,false));

        return changeItem;
    }

    @Override
    public void onBindViewHolder(TypeViewHolder holder, int position) {
               pos=position;
        holder.bindHolder();

    }

    private void replace() {

    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }



class    MyTakeViewHolder extends  TypeViewHolder {
 private ImageView imageView;
    private Button takeBut;
    public MyTakeViewHolder(View itemView) {
        super(itemView);
        takeBut= (Button) itemView.findViewById(R.id.changebut_);
       imageView= (ImageView) itemView.findViewById(R.id.change_img);
        takeBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("11111111111","655555555555555555555");
                Intent intent=new Intent(mainActivity, DetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("dd","1");

            }
        });
    }

    @Override
    public void bindHolder() {

    }
}

       class ChangeItem extends TypeViewHolder {
           private ImageView imageView;
           private EditText editText;
           private TextView textView;

           public ChangeItem(View itemView) {
               super(itemView);

               imageView= (ImageView) itemView.findViewById(R.id.change_img);
               editText= (EditText) itemView.findViewById(R.id.change_content);
               textView= (TextView) itemView.findViewById(R.id.change_text);
           }

           @Override
           public void bindHolder() {
               textView.setText(list.get(pos-1));
//                  imageView.setImageResource(list.get(pos-1));
           }
       }
}
