package home.parsenter;

import android.widget.Toast;

import home.Landing;
import home.model.DataModel;
import home.model.Model;
import home.ui.BaseView;

/**
 * Created by 11059 on 2017/3/13.
 */
public class MyListener implements Listener {
    private BaseView baseView;
    private Model model;
    private String name;
    private String password;
    public MyListener(BaseView landing) {
        this.baseView=landing;
          model=new Model();
    }


    public void request() {
       name= baseView.getName();
       password= baseView.getPassword();
        model.request(name,password,this);

    }

    @Override
    public void UpDate(String s) {
        DataModel dataModel=new DataModel();
        dataModel.setContent(s);
        baseView.upData(dataModel);

    }
}
