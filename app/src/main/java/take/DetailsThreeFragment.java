package take;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.example.a11059.udi.MyApplication;
import com.example.a11059.udi.R;

/**
 * Created by 11059 on 2016/11/10.
 */
public class DetailsThreeFragment extends Fragment {
 private View view;
    private TimePicker timePicker;
    private int hour;
    private int second;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.user_detailsthree,null);
        timePicker= (TimePicker) view.findViewById(R.id.user_threeTimePicker);
        setHour();
        return view;

    }

    private void setHour() {
        if ((((MyApplication)getActivity().getApplication()).getPhone_hour())!=0) {
            int h=(((MyApplication) getActivity().getApplication()).getPhone_hour());
            timePicker.setCurrentHour(h);
            int s=(((MyApplication) getActivity().getApplication()).getPhone_second());
            timePicker.setCurrentMinute(s);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        init();
    }

    private void init() {
        hour=   timePicker.getCurrentHour();
        ((MyApplication)getActivity().getApplication()).setPhone_hour(hour);
        second=  timePicker.getCurrentMinute();
        ((MyApplication)getActivity().getApplication()).setPhone_second(second);
        System.out.println("时间"+hour+"分钟"+second);
    }
}
