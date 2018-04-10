package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a11059.udi.R;
import com.example.a11059.udi.TypeViewHolder;

import org.w3c.dom.Text;


/**
 * Created by 11059 on 2016/10/26.
 */
public class PersionAdapter extends RecyclerView.Adapter<TypeViewHolder> {
    private  Context context;
    private String [] list;
    private int pos;

    public PersionAdapter(Context context, String[] list) {
        this.context=context;
        this.list=list;
    }


    @Override
    public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolderr myViewHolderr=new MyViewHolderr(LayoutInflater.from(context).inflate(R.layout.user,null));
        return myViewHolderr;
    }
    @Override
    public void onBindViewHolder(TypeViewHolder holder, int position) {
        this.pos=position;
    }



    @Override
    public int getItemCount() {

        return list.length;
    }
   public class MyViewHolderr extends TypeViewHolder {
       private TextView  textView;
       public MyViewHolderr(View itemView) {
           super(itemView);
           textView= (TextView) itemView.findViewById(R.id.user_text);
       }

       @Override
       public void bindHolder() {
           System.out.println("fffffff"+pos);
          textView.setText(list[pos]);
       }

   }
}
