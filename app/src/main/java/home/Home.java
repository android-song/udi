package home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a11059.udi.ChangeActivity;
import com.example.a11059.udi.HistoryActivity;
import com.example.a11059.udi.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import home.model.DataModel;
import home.parsenter.MyListener;
import home.ui.BaseView;

/**
 * Created by 11059 on 2016/11/6.
 */
public class Home extends Fragment implements View.OnClickListener,BaseView{
  private TextView textView;
    private View view;
    private  TextView logeout;
    private View item1;//更改信息
    private View item2;//取件历史
    private View item3;//我发布的
    private View item4;//系统通知
    private View item5;//邀请朋友一起使用U递
    private Thread runOnUiThread;
    private MyListener myListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.persion,null);
        init();
        return view;

    }

    private void init() {
        textView= (TextView) view.findViewById(R.id.home_in);
        logeout= (TextView) view.findViewById(R.id.logeout);
        item1=view.findViewById(R.id.persion_item1);
        item2=view.findViewById(R.id.persion_item2);
        item3=view.findViewById(R.id.persion_item3);
        item4=view.findViewById(R.id.persion_item4);
        item5=view.findViewById(R.id.persion_item5);
        logeout.setOnClickListener(this);
        item1.setOnClickListener(this);
        item2.setOnClickListener(this);
        item3.setOnClickListener(this);
        item4.setOnClickListener(this);
        item5.setOnClickListener(this);
        textView.setOnClickListener(this);
        myListener=new MyListener(this);
    }

    @Override
    public void onClick(View v) {
           switch (v.getId()){
               case R.id.home_in:
                   System.out.println("....................");
                   Intent intent=new Intent(getActivity(),Landing.class);
                startActivity(intent);
                   break;
               case R.id.persion_item1:
                   Intent intent1=new Intent(getActivity(),ChangeActivity.class);
                   startActivity(intent1);
                   break;
               case R.id.persion_item2:
                   Intent intent2=new Intent(getActivity(),HistoryActivity.class);
                   intent2.putExtra("flag",1);
                   startActivity(intent2);
                   break;
               case R.id.persion_item3:
                   Intent intent3=new Intent(getActivity(),HistoryActivity.class);
                   intent3.putExtra("flag",2);
                   startActivity(intent3);
                   break;
               case R.id.persion_item4:
                   Intent intent4=new Intent(getActivity(),HistoryActivity.class);
                   intent4.putExtra("flag",3);
                   startActivity(intent4);
                   break;
               case R.id.persion_item5:
                   Log.i("ddd","6");
                   myListener.request();
                   break;
               case R.id.logeout:
                   // 重新显示登陆页面
                   System.out.println("正在..");
                   logout();
                   break;
           }
    }


    private void logout() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        String st = "正在退出登录..";
        System.out.println(st);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        EMClient.getInstance().logout(false,new EMCallBack() {

            @Override
            public void onSuccess() {
           new Thread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        // 重新显示登陆页面
                        System.out.println("正在跳转..");
                        startActivity(new Intent(getActivity(), Landing.class));
                      getActivity().finish();


                    }
                }).start();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Toast.makeText(getActivity(), "unbind devicetokens failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void upData(DataModel dataModel) {
        Toast.makeText(getContext(),dataModel.getContent(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorUi() {

    }

    @Override
    public void inRequest() {

    }

    @Override
    public String getName() {
        return "d";

    }

    @Override
    public String getPassword() {
        return  "s";
    }
}
