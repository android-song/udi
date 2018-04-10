package user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a11059.udi.MyApplication;
import com.example.a11059.udi.R;
import com.example.a11059.udi.SuccessfulActivity;

import java.util.ArrayList;
import java.util.List;

import adapter.SetingAdapter;
import take.Details;
import take.LineBreakLayout;

/**
 * Created by 11059 on 2016/11/6.
 */
public class User extends Fragment implements OnItemClickListener,View.OnClickListener{
   private ListView listView;
    private  String [] list={"取件人","快递名称","取件时间","联系方式","要求送件时间"};
    private View view;
    private  FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private  Details classFragment;//取件人fragment
    private SetingAdapter arrayAdapter;
    private Button button;//提交信息
    private String name;//取件人的姓名
    private String phone;//取件人的电话
    private String courier;//取件人的快递名称
    private int phone_second;//要求取件时间   分钟
    private int phone_hour;//小时
    private int number_second;//要求送到时间  分钟
    private int number_hour;//时间
    private EditText editText;//取件人姓名
    private EditText courierName;//取件时间
     private EditText phone_edit;//联系方式
    private Spinner school;//学校

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=LayoutInflater.from(getContext()).inflate(R.layout.home,null);
//        listView= (ListView) view.findViewById(R.id.uer_listView);
        initUser();
        init();
        return view;
    }

    private void init() {
        school= (Spinner) view.findViewById(R.id.home_spinerone);
    }

    private void initUser() {
        LineBreakLayout lineBreakLayout= (LineBreakLayout) view.findViewById(R.id.user_linebreaklayout);
        Button button= (Button) view.findViewById(R.id.user_release);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog= new AlertDialog.Builder(getContext()).
                        setIcon(R.mipmap.ic_launcher).setMessage("是否发布此单？").setTitle("优你U递").setPositiveButton("发布",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("再考虑一下", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

            }
        });
        List<String> lable = new ArrayList<>();
        lable.add("易碎");
        lable.add( "加急");
        lable.add("大件");
        lable.add("赏金多");

//设置标签
        lineBreakLayout.setLables(lable,true);
    }


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:

                    break;
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("onStart");

    }
//
//    private void initUser() {
//        button= (Button) view.findViewById(R.id.user_release);
//        button.setOnClickListener(this);
//        editText= (EditText) view.findViewById(R.id.user_name);
//        courierName= (EditText) view.findViewById(R.id.user_courier);
//        phone_edit= (EditText) view.findViewById(R.id.user_phone);
//         arrayAdapter=new SetingAdapter((MainActivity) getContext(),list,listView);
//        listView.setAdapter(arrayAdapter);
//        listView.setOnItemClickListener(this);
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("onAttach");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("onDestroyView");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        fragmentManager=getFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        switch (position) {
            case 0:
//                fragmentManager.popBackStack();
//                arrayAdapter.notifyDataSetChanged();
                Details details=new Details(getContext());
                Window window=details.getWindow();//AlertDialog的布局
                window.setGravity(Gravity.BOTTOM);
                details.setTitle("取件人");
                details.getLayoutInflater();
                WindowManager.LayoutParams layoutParams=window.getAttributes();//获取当前对话框的参数
                layoutParams.x=0;
                layoutParams.y=-20;
                 layoutParams.width= LinearLayout.LayoutParams.MATCH_PARENT;
                details.setIcon(R.mipmap.ic_launcher);
                details.show();
//               WindowManager windowManager=window.getWindowManager();
//               Display display =windowManager.getDefaultDisplay();
//
                layoutParams.width= (int) layoutParams.MATCH_PARENT;//设置狂宽高
                details.getWindow().setAttributes(layoutParams);//把布局的宽高设置给自定义的AlertDialog
                break;
            case 1:
//                arrayAdapter.notifyDataSetChanged();
//                fragmentManager.popBackStack();
//                DetailsTwoFragemtn detailsTwoFragemtn=new DetailsTwoFragemtn();
//                fragmentTransaction.replace(R.id.user_fragment,detailsTwoFragemtn);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();

                break;
            case 2:
////                arrayAdapter.notifyDataSetChanged();
//                fragmentManager.popBackStack();
//                DetailsThreeFragment detailsThreeFragment=new DetailsThreeFragment();
//                fragmentTransaction.replace(R.id.user_fragment,detailsThreeFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
                break;

            case 3:
////                arrayAdapter.notifyDataSetChanged();
//                fragmentManager.popBackStack();
//                DetailsFourFragment detailsFourFragment=new DetailsFourFragment();
//                fragmentTransaction.replace(R.id.user_fragment,detailsFourFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
                break;
            case 4:
////                arrayAdapter.notifyDataSetChanged();
//                fragmentManager.popBackStack();
//                DetailsFiveFragment detailsFiveFragment=new DetailsFiveFragment();
//                fragmentTransaction.replace(R.id.user_fragment,detailsFiveFragment);
//                fragmentTransaction.addToBackStack(null);//给fragment添加回退栈
//                fragmentTransaction.commit();
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_release:
                getReleaseMessage();
                break;
        }
    }

    private void getReleaseMessage() {
        name=((MyApplication)getActivity().getApplication()).getName();
        courier=((MyApplication)getActivity().getApplication()).getCourier();
        phone=((MyApplication)getActivity().getApplication()).getPhone();
        number_hour=((MyApplication)getActivity().getApplication()).getNumber_hour();
        number_second=((MyApplication)getActivity().getApplication()).getNumber_second();
        phone_hour=((MyApplication)getActivity().getApplication()).getPhone_hour();
        phone_second=((MyApplication)getActivity().getApplication()).getPhone_second();
        if (("").equals(name)|("").equals(courier)|("").equals(phone)){
            System.out.println("请填写信息");
            Toast.makeText(getContext(),"请填写完整信息",Toast.LENGTH_SHORT).show();

        }else {
            clear();
        }


//        ((MyApplication)getActivity().getApplication()).setNumber_hour(0);
//        ((MyApplication)getActivity().getApplication()).setNumber_second(0);
//        ((MyApplication)getActivity().getApplication()).setPhone_hour(0);
//        ((MyApplication)getActivity().getApplication()).setPhone_second(0);
    }

    private void clear() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setIcon(R.mipmap.icon_cart);
        builder.setTitle("邮你U递");
        builder.setMessage("是否提交");
        builder.setNegativeButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("44444444444");
                ((MyApplication)getActivity().getApplication()).setName(null);
                ((MyApplication)getActivity().getApplication()).setCourier(null);
                ((MyApplication)getActivity().getApplication()).setPhone(null);
                Intent intent=new Intent(getActivity(), SuccessfulActivity.class);
                startActivity(intent);
            }
        });
        builder.setPositiveButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("3333333333");
            }
        });
        builder.show();
    }
}
