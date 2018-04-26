package com.example.a11059.udi.take;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.a11059.udi.MyApplication;
import com.example.a11059.udi.R;

/**
 * Created by 11059 on 2016/11/10.
 */
public class DetailsTwoFragemtn extends Fragment {
    private View view;
    private EditText editText;//输入的快递名称
    private  String courier;//快递名称
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.user_detailstwo,null);
        editText= (EditText) view.findViewById(R.id.user_courier);
        init();
        return view;
    }
    @Override
    public void onPause() {
        super.onPause();
        setName();
    }

    private void setName() {
        courier=editText.getText().toString();
//        ((MyApplication)getActivity().getApplication()).setCourier(courier);
    }

    private void init() {

//        if (((MyApplication)getActivity().getApplication()).getCourier()!=null){
//            editText.setText(((MyApplication)getActivity().getApplication()).getCourier());
//        }
    }
}
