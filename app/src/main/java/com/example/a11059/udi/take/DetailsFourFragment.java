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
public class DetailsFourFragment extends Fragment {
    private EditText editText;//联系方式
    private String contact;
 private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.user_detailsfour,null);
        editText= (EditText) view.findViewById(R.id.user_phone);
        init();
        return view;
    }
    @Override
    public void onPause() {
        super.onPause();
        setPhone();
    }

    private void setPhone() {
        contact=editText.getText().toString();
//        ((MyApplication)getActivity().getApplication()).setPhone(contact);
    }

    private void init() {

//        if (((MyApplication)getActivity().getApplication()).getPhone()!=null){
//            editText.setText(((MyApplication)getActivity().getApplication()).getPhone());
//        }
    }
}
