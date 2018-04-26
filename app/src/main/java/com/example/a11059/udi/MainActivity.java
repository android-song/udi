package com.example.a11059.udi;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a11059.udi.adapter.MyFragmentAdapter;
import com.example.a11059.udi.bomb.MyUser;
import com.example.a11059.udi.home.Home;
import com.example.a11059.udi.home.Landing;
import com.example.a11059.udi.home.SettingFragment;
import com.example.a11059.udi.home.model.Persion;
import com.example.a11059.udi.model.HttpRequestModel;
import com.example.a11059.udi.notify.BaseNotify;
import com.example.a11059.udi.notify.Notification;
import com.example.a11059.udi.notify.NotificationCenter;
import com.example.a11059.udi.notify.NotificationDef;
import com.example.a11059.udi.take.Take;
import com.example.a11059.udi.user.User;
import com.example.a11059.udi.utils.FragmentTabHost;
import com.example.a11059.udi.utils.Info;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.smssdk.SMSSDK;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener ,BaseNotify{
    private FragmentTabHost fragmentTabHost;
    private ViewPager viewPager;
    private View view;
    private View view1;
    private List<Fragment> fragments;
    private String[] name = {"代取", "发布", "我的"};
    private int[] v = {R.drawable.select_item, R.drawable.select_item1,
            R.drawable.select_item2};
    private HttpRequestModel httpRequestModel;
    private FrameLayout frameLayout;
    private FragmentManager fm;
    private FragmentTransaction tx;
    private long firstClick;
    private boolean isMainPageShow=true;
    private Landing landingFragment;
    private SettingFragment settingFragment;
    private File image;
    private Uri mCutUri;
    private CommonStatusLayout commonStatusLayout;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotificationCenter.getNotification().register(NotificationDef.LOGIN_CLICK, this);
        NotificationCenter.getNotification().register(NotificationDef.LOGIN_SUCCESS_CLICK, this);
        NotificationCenter.getNotification().register(NotificationDef.ADDRESS_CLICK, this);
        NotificationCenter.getNotification().register(NotificationDef.PHONE_CLICK, this);
        NotificationCenter.getNotification().register(NotificationDef.LOGIN_OUT_CLICK, this);
        NotificationCenter.getNotification().register(NotificationDef.OPEN_CAMERA, this);
        NotificationCenter.getNotification().register(NotificationDef.OPEN_GALLERY, this);
        NotificationCenter.getNotification().register(NotificationDef.SHOW_LOADING, this);
        NotificationCenter.getNotification().register(NotificationDef.HIDEN_LOADING, this);
        init();
        initBottom();
    }
    @Override
    public void notify(Notification notification   )    {
        image =new File(Environment.getExternalStorageDirectory(), Info.PHOTO_NAME);
        if (notification.id==NotificationDef.OPEN_GALLERY){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
            intent.setType("image/*");
            startActivityForResult(intent, Info.OPEN_GALLERY);
            return;
            }
        if (notification.id==NotificationDef.OPEN_CAMERA){
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
            startActivityForResult(intent, Info.OPEN_CAMERA);
            return;
        }
        if (notification.id==NotificationDef.SHOW_LOADING){
            commonStatusLayout.showLoading();
            return;
        }
        if (notification.id==NotificationDef.HIDEN_LOADING){
            commonStatusLayout.hideLoading();
            return;
        }
        setDefaultFragment(notification.id);

    }
     @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("demo",resultCode+"...."+requestCode);
         if (resultCode == RESULT_OK){
             switch (requestCode){
                 case Info.OPEN_GALLERY: //从相册图片后返回的uri
                     //启动裁剪
                     startActivityForResult(CutForPhoto(data.getData()),Info.CROP_PHOTO);
                     break;
                 case Info.OPEN_CAMERA: //相机返回的 uri
                     //启动裁剪
                     String path = this.getExternalCacheDir().getPath();
                     String name = "output.png";
                     startActivityForResult(CutForCamera(path,name),Info.CROP_PHOTO);
                     break;
                 case Info.CROP_PHOTO:
                         NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.CAMERA_HEAR,mCutUri));

                     break;
             }
        }
    }
    /**
     * 拍照之后，启动裁剪
     * @param camerapath 路径
     * @param imgname img 的名字
     * @return
     */
    @NonNull
    private Intent CutForCamera(String camerapath,String imgname) {
        image =new File(Environment.getExternalStorageDirectory(),Info.PHOTO_NAME);
        File cutfile = new File(Environment.getExternalStorageDirectory().getPath(),
                //设置裁剪之后的图片路径文件
                "cutcamera.png"); //随便命名一个

        if (cutfile.exists()) { //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
            cutfile.delete();
        }
        try {
            cutfile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //初始化 uri
            Uri imageUri = null; //返回来的 uri
            Uri outputUri = null; //真实的 uri
            Intent intent = new Intent("com.android.camera.action.CROP");
            //拍照留下的图片
//            File camerafile = new File(camerapath, imgname);
//            if (Build.VERSION.SDK_INT >= 24) {
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                imageUri = FileProvider.getUriForFile(this,
//                        "com.example.a11059.udi",
//                        camerafile);
//            } else {
//                imageUri = Uri.fromFile(camerafile);
//            }
            imageUri=Uri.fromFile(image);
            outputUri = Uri.fromFile(cutfile);
            //把这个 uri 提供出去，就可以解析成 bitmap了
            mCutUri = outputUri;
        return picCrop(intent,imageUri,outputUri);
    }
    /**
     * 图片裁剪
     * @param uri
     * @return
     */
    @NonNull
    private Intent CutForPhoto(Uri uri) {
        //直接裁剪
        Intent intent = new Intent("com.android.camera.action.CROP");
        //设置裁剪之后的图片路径文件
        File cutfile = new File(Environment.getExternalStorageDirectory().getPath(),
                "cutcamera.png"); //随便命名一个
        if (cutfile.exists()){ //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
            cutfile.delete();
        }
        try {
            cutfile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri imageUri = uri; //返回来的 uri
        Uri outputUri = null; //真实的 uri
        Log.d("demo", "CutForPhoto: "+cutfile);
        outputUri = Uri.fromFile(cutfile);
        mCutUri = outputUri;
        return picCrop(intent,imageUri,outputUri);
    }

    private Intent picCrop(Intent intent,Uri imageUri, Uri outputUri) {        //初始化 uri

        Log.d("demo", "mCameraUri: "+mCutUri);
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop",true);
        // aspectX,aspectY 是宽高的比例，这里设置正方形
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //设置要裁剪的宽高
        intent.putExtra("outputX", ToolUtils.dip2px(200)); //200dp
        intent.putExtra("outputY",ToolUtils.dip2px(200));
        intent.putExtra("scale",true);
        //如果图片过大，会导致oom，这里设置为false
        intent.putExtra("return-data",false);
        if (imageUri != null) {
            intent.setDataAndType(imageUri, "image/*");
        }
        if (outputUri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        }
        intent.putExtra("noFaceDetection", true);
        //压缩图片
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        return intent;
    }

    private void setDefaultFragment(int id) {
        fm = getSupportFragmentManager();
        tx = fm.beginTransaction();
        if (frameLayout==null) {
            frameLayout = (FrameLayout) findViewById(R.id.ll);

        }
        if (id==NotificationDef.LOGIN_CLICK) {
            frameLayout.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
            fragmentTabHost.setVisibility(View.GONE);
            isMainPageShow=false;
            if (landingFragment==null) {
                landingFragment = new Landing();
                tx.replace(R.id.ll, landingFragment);
                tx.addToBackStack(null);
                tx.commit();
            }else{
                tx.replace(R.id.ll, landingFragment);
                tx.addToBackStack(null);
                tx.commit();
            }
        }
        if (id==NotificationDef.LOGIN_SUCCESS_CLICK||id==NotificationDef.LOGIN_OUT_CLICK) {
            frameLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            fragmentTabHost.setVisibility(View.VISIBLE);
            isMainPageShow=true;
        }

        if (id==NotificationDef.ADDRESS_CLICK||id==NotificationDef.PHONE_CLICK) {
            frameLayout.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
            fragmentTabHost.setVisibility(View.GONE);
            isMainPageShow=false;
            if (settingFragment==null) {
                settingFragment = new SettingFragment();
                tx.replace(R.id.ll, settingFragment);
                tx.addToBackStack(null);
                tx.commit();
            }else {
                tx.replace(R.id.ll, settingFragment);
                tx.addToBackStack(null);
                tx.commit();
            }
        }



    }

    private void initBottom() {
        for (int i = 0; i < 3; i++) {
            fragmentTabHost.addTab(fragmentTabHost.newTabSpec(name[i]).setIndicator(getView(i)), fragments.get(i), null);
            //给每一个Tab添加点击监听事件，TabOnClickListener是自己定义的OnclickListener
            fragmentTabHost.getTabWidget().getChildTabViewAt(i).setOnClickListener(new TabOnClickListener(fragmentTabHost, viewPager, i));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy");
        fragments.clear();
        System.exit(0);

    }

    //    private void TakeInit() {
//
//            List<TakeUser> strings=new ArrayList<>();
//            recyclerView= (RecyclerView) findViewById(R.id.take_recycler);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
//            recyclerView.setLayoutManager(linearLayoutManager);
//        TakeAdapter takeAdapter=new TakeAdapter(strings,MainActivity.this);
//        recyclerView.setAdapter(takeAdapter);
//    }

    //实现下拉刷新功能


//    public android.os.Handler d = new android.os.Handler() {
//        @Override
//        public void handleMessage(s msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 1:
//                    homeDateTypes = (List<HomeDateType>) msg.obj;
//                    for (int i = 0; i < homeDateTypes.size(); i++) {
////                        System.out.println("545555555555555" + homeDateTypes.get(i).getText());
//                    }
//                    homeFragment.setDate(homeDateTypes);
//                    break;
//
//                case 4:
//                    List<Bitmap> bitmaps  = (List<Bitmap>) msg.obj;;
//                    homeFragment.setDate1(bitmaps);
//                    break;
//                case 2:
//                    List<String> strings= (List<String>) msg.obj;
//
//                    homeFragment.setDate2(strings);
//                    break;
//                case 3:
//                    List<String>  strings1= (List<String>) msg.obj;
//
//                    homeFragment.setDate3(strings1);
//                    break;
//                case 5:   List<String> strings2= (List<String>) msg.obj;
//                    System.out.println(strings2);
//                    homeFragment.setDate4(strings2);
//                    break;
//                default:
//                    break;
//            }
//        };
//    };

//    private void datelsit() {
//        String [] s={"1","d","d","h"};
//        MyHomeAdapter myHomeAdapter=new MyHomeAdapter(this,s);
//        RecyclerView recyclerView= (RecyclerView) LayoutInflater.from(this).inflate(R.layout.home_fragment,null).findViewById(R.id.home_recycler);
//        //这个是必须设置的，设置recyclerview的布局管理器为2列，垂直排布
////        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(myHomeAdapter);
//    }


    private View getView(int i) {//给每一个菜单设置图片和文字
        view = LayoutInflater.from(this).inflate(R.layout.tab, null);
        TextView textView;
        textView = (TextView) view.findViewById(R.id.text_shouye);
        textView.setText(name[i]);
        ImageView imageView;
        imageView = (ImageView) view.findViewById(R.id.button);
        imageView.setBackgroundResource(v[i]);//给每一个菜单设置点击效果
        return view;
    }

    //给viewpager添加fragment，添加适配器
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        Bmob.initialize(this, "ddec04c200a15e49df942696b32df615");
        //设置调整软件盘，隐藏背景，不进行压缩
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        SMSSDK.initSDK(this,"19549c0ba37f4","a2007714211699f5586d3071306907e6");//也是所注册的APPSECRETE
        setContentView(R.layout.activity_main);
        Window window = this.getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.tt_title_bar_color));
        viewPager = (ViewPager) findViewById(R.id.pager);
        commonStatusLayout= (CommonStatusLayout) findViewById(R.id.load);
        viewPager.addOnPageChangeListener((ViewPager.OnPageChangeListener) this);
        viewPager.setOffscreenPageLimit(3);
//        view= LayoutInflater.from(this).inflate(R.layout.home_fragment,null);
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.pager);
        fragments = new ArrayList<Fragment>();
        Home home = new Home();
        Take take = new Take();
        User user = new User();
        fragments.add(take);
        fragments.add(user);
        fragments.add(home);
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), fragments));
        fragmentTabHost.getTabWidget().setDividerDrawable(null);//取消分割线
        if (httpRequestModel==null){
            httpRequestModel=new HttpRequestModel();
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode== KeyEvent.KEYCODE_BACK){

            if (!isMainPageShow){
                frameLayout.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
                fragmentTabHost.setVisibility(View.VISIBLE);
                isMainPageShow=true;
                return false;
            }

            if(System.currentTimeMillis()-firstClick>2000){
                firstClick=System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();;
            }else{
                System.exit(0);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }


    @Override
    public void onPageSelected(int position) {//position表示当前的选中的位置，这事件是页面跳转完毕的时候调用的
        TabWidget tabWidget = fragmentTabHost.getTabWidget();
        int oldFoucsability = tabWidget.getDescendantFocusability();
        tabWidget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);//设置view覆盖子类控件而获得焦点
        fragmentTabHost.setCurrentTab(position);//根据位置position设置当前的tab
        tabWidget.setDescendantFocusability(oldFoucsability);//设置取消分割线
        if (position > 4) {
            viewPager.setCurrentItem(0, false);//false不显示调转画面
            position = 1;
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        int position = fragmentTabHost.getCurrentTab();
        viewPager.setCurrentItem(position);//把选中的Tab位置赋给适配器，让他控制页面改变

    }


    public static class CommonUtils {
        private static long lastClickTime;

        public static boolean isFastDoubleClick() {
            long time = System.currentTimeMillis();
            long timeD = time - lastClickTime;
            if (0 < timeD && timeD < 800) {
                return true;
            }
            lastClickTime = time;
            return false;
        }
    }
}
