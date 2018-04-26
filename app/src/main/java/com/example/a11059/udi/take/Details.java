package com.example.a11059.udi.take;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.example.a11059.udi.R;

/**
 * Created by 11059 on 2016/11/10.
 */
public class Details extends AlertDialog{
 private View view;
    private EditText editText;//输入取件人的姓名
    private String name;

    public Details(Context context) {
        super(context);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);
        init();

    }


    //    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view=inflater.inflate(R.layout.user_details,null);
//        editText= (EditText) view.findViewById(R.id.user_name);
//        init();
//        return view;
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        setName();
//    }

//    private void setName() {
//        name=editText.getText().toString();
//        ((MyApplication)getActivity().getApplication()).setName(name);
//    }

    private void init() {
        editText= (EditText) findViewById(R.id.user_name);
//        editText.setText("ddd");
    }
}
