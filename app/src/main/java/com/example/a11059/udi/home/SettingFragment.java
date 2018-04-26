package com.example.a11059.udi.home;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a11059.udi.NativeUtil;
import com.example.a11059.udi.R;
import com.example.a11059.udi.ToolUtils;
import com.example.a11059.udi.bomb.MyUser;
import com.example.a11059.udi.databinding.SettingDataBinding;
import com.example.a11059.udi.notify.BaseNotify;
import com.example.a11059.udi.notify.Notification;
import com.example.a11059.udi.notify.NotificationCenter;
import com.example.a11059.udi.notify.NotificationDef;
import com.example.a11059.udi.utils.BaseFragment;
import com.bumptech.glide.Glide;
import com.example.a11059.udi.utils.EditorDialog;
import com.example.a11059.udi.utils.PicSelectDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
/**
 * Created by Administrator on 2018/4/23.
 */

public class SettingFragment extends BaseFragment implements BaseNotify{
    private View view;
    private SettingDataBinding dataBinding;
    private PicSelectDialog picDialog;
    private File image;
    private Uri mCutUri;
    private MyUser userInfo;
    private String imageUrl;
    private WeakReference<Bitmap> bitmap;
    private EditorDialog promptDialog;
    private String hintText;
    private int type;
    private String firstItem;
    private String secondItem;
    private int picOrsex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.change_act, container, false);
            dataBinding = DataBindingUtil.bind(view);
            NotificationCenter.getNotification().register(NotificationDef.UPDATA_USERINFO, this);
            NotificationCenter.getNotification().register(NotificationDef.LOGIN_OUT_CLICK, this);
            NotificationCenter.getNotification().register(NotificationDef.EDITOR_HEAR_CLICK, this);
            NotificationCenter.getNotification().register(NotificationDef.CAMERA_HEAR, this);
            NotificationCenter.getNotification().register(NotificationDef.SAVE_USERINFO, this);
            NotificationCenter.getNotification().register(NotificationDef.EDITOR_EMAIL_CLICK, this);
            NotificationCenter.getNotification().register(NotificationDef.EDITOR_PHONE_CLICK, this);
            NotificationCenter.getNotification().register(NotificationDef.EDITOR_ADDRESS_CLICK, this);
            NotificationCenter.getNotification().register(NotificationDef.EDITOR_NICK_CLICK, this);
            NotificationCenter.getNotification().register(NotificationDef.EDITOR_NAME_CLICK, this);
            NotificationCenter.getNotification().register(NotificationDef.EDITOR_SEX_CLICK, this);
            updateUserInfo();
        }
        return dataBinding.getRoot();
    }
    private void updateUserInfo() {
         userInfo= ToolUtils.getUser();
        if(userInfo != null){
            // 允许用户使用应用
            Log.e("demo","允许用户使用应用"+userInfo.toString());
            if (userInfo.getImgUrl()!=null){
                Log.e("demo"," setting updateUserInfo"+userInfo.getImgUrl());
                Glide.with(this).load(userInfo.getImgUrl()).into(dataBinding.hearIcon);
            }
            if (userInfo.getNick()!=null){
                dataBinding.nick.setText(userInfo.getNick());
            }
            if (userInfo.getName()!=null){
                dataBinding.name.setText(userInfo.getName());
            }
            if (userInfo.getAddress()!=null){
                dataBinding.address.setText(userInfo.getAddress());
            }
            if (userInfo.getPhone()!=null){
                dataBinding.phone.setText(userInfo.getPhone());
            }
            if (userInfo.getEmail()!=null){
                dataBinding.email.setText(userInfo.getEmail());
            }
            if (userInfo.getSex()!=null){
                dataBinding.sex.setText(userInfo.getSex());
            }
//            dataBinding.homeIn.setVisibility(View.GONE);
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            Log.e("demo","缓存用户对象为空时");
            dataBinding.hearIcon.setImageDrawable(getResources().getDrawable(R.mipmap.icon_user));
            dataBinding.nick.setText("");
            dataBinding.sex.setText("");
            dataBinding.name.setText("");
            dataBinding.address.setText("");
            dataBinding.phone.setText("");
            dataBinding.email.setText("");

        }
    }

    @Override
    public void notify(Notification notification) {
        if (notification.id==NotificationDef.UPDATA_USERINFO||notification.id==NotificationDef.LOGIN_OUT_CLICK){
            updateUserInfo();
        }
        if (notification.id==NotificationDef.EDITOR_HEAR_CLICK){
            showPicSelectDialog(notification);
        }
        if (notification.id==NotificationDef.CAMERA_HEAR){
            setHearImage(notification);
        }
        if (notification.id==NotificationDef.SAVE_USERINFO){
            updateServiceUserInfo();
        }
        if (notification.id==NotificationDef.EDITOR_NAME_CLICK){
            editorDialogShow(NotificationDef.EDITOR_NAME_CLICK);
        }
        if (notification.id==NotificationDef.EDITOR_NICK_CLICK){
            editorDialogShow(NotificationDef.EDITOR_NICK_CLICK);
        }
        if (notification.id==NotificationDef.EDITOR_SEX_CLICK){
            showPicSelectDialog(notification);
        }
        if (notification.id==NotificationDef.EDITOR_ADDRESS_CLICK){
            editorDialogShow(NotificationDef.EDITOR_ADDRESS_CLICK);
        }
        if (notification.id==NotificationDef.EDITOR_PHONE_CLICK){
            editorDialogShow(NotificationDef.EDITOR_PHONE_CLICK);
        }
        if (notification.id==NotificationDef.EDITOR_EMAIL_CLICK){
            editorDialogShow(NotificationDef.EDITOR_EMAIL_CLICK);
        }

    }
    private void editorDialogShow(int i) {
        type=i;
        if (i==NotificationDef.EDITOR_NAME_CLICK){
            hintText=getString(R.string.real_name);
        }
        if (i==NotificationDef.EDITOR_NICK_CLICK){
            hintText=getString(R.string.nick_tv);
        }
        if (i==NotificationDef.EDITOR_ADDRESS_CLICK){
            hintText=getString(R.string.address_tv);
        }
        if (i==NotificationDef.EDITOR_PHONE_CLICK){
            hintText=getString(R.string.phone_tv);
        }
        if (i==NotificationDef.EDITOR_EMAIL_CLICK){
            hintText=getString(R.string.email_tv);
        }

        try {
            promptDialog = new EditorDialog(getContext());
            promptDialog.setTitle(getString(R.string.prompt_tv));
            promptDialog.setMessage(hintText);
            promptDialog.setYesOnclickListener(getString(R.string.ok_tv), new EditorDialog.onYesOnclickListener() {
                @Override
                public void onYesClick(String message) {
                    promptDialog.dismiss();
                    if (type==NotificationDef.EDITOR_NAME_CLICK){
                        dataBinding.name.setText(message);
                        return;
                    }
                    if (type==NotificationDef.EDITOR_NICK_CLICK){
                        dataBinding.nick.setText(message);
                        return;
                    }
                    if (type==NotificationDef.EDITOR_ADDRESS_CLICK){
                        dataBinding.address.setText(message);
                        return;
                    }
                    if (type==NotificationDef.EDITOR_PHONE_CLICK){
                        dataBinding.phone.setText(message);
                        return;
                    }
                    if (type==NotificationDef.EDITOR_EMAIL_CLICK){
                        dataBinding.email.setText(message);
                        return;
                    }
                }
            });
            promptDialog.setNoOnclickListener(getString(R.string.cancel_tv), new EditorDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    promptDialog.dismiss();
                }
            });
            promptDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void setHearImage(Notification notification) {
        mCutUri = (Uri) notification.object;
        //获取裁剪后的图片，并显示出来
        try {
            bitmap =new WeakReference<Bitmap>(NativeUtil.comp(BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(mCutUri))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        dataBinding.hearIcon.setImageBitmap(bitmap.get());
    }

    private void updateServiceUserInfo() {
        if (bitmap==null){
             updateWebUserInfo();
            return;
        }
        NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.SHOW_LOADING));
        final WeakReference<BmobFile> bmobFile = new WeakReference<BmobFile>(new BmobFile(saveBitmap(bitmap.get())));
        bmobFile.get().uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    Log.e("demo","上传文件成功"+bmobFile.get().getFileUrl());
                    imageUrl=bmobFile.get().getFileUrl();
                    updateWebUserInfo();
                }else{
                    Log.e("demo","文件上传失败"+e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                Log.e("demo","返回的上传进度"+value);
            }
        });
    }

    public File saveBitmap(Bitmap bm) {
        Log.e("SaveBitmap", "保存图片");
        File f = new File(Environment.getExternalStorageDirectory(), "bitmap.jpg");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Log.i("SaveBitmap", "已经保存");
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return f;
    }
    private void updateWebUserInfo() {
        MyUser newUser = new MyUser();
        if (imageUrl!=null&&!imageUrl.isEmpty()){
            newUser.setImgUrl(imageUrl);
        }
        if (!dataBinding.name.getText().toString().isEmpty()){
            newUser.setName(dataBinding.name.getText().toString());
        }
        if (!dataBinding.nick.getText().toString().isEmpty()){
            newUser.setNick(dataBinding.nick.getText().toString());
        }
        if (!dataBinding.sex.getText().toString().isEmpty()){
            newUser.setSex(dataBinding.sex.getText().toString());
        }
        if (!dataBinding.address.getText().toString().isEmpty()){
            newUser.setAddress(dataBinding.address.getText().toString());
        }
        if (!dataBinding.phone.getText().toString().isEmpty()){
            newUser.setPhone(dataBinding.phone.getText().toString());
        }
        if (!dataBinding.email.getText().toString().isEmpty()){
            newUser.setGmail(dataBinding.email.getText().toString());
        }
        BmobUser bmobUser = BmobUser.getCurrentUser();
        newUser.update(bmobUser.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.e("demo","更新用户信息成功");
                    toast(getContext(),"更新用户信息成功");
                    imageUrl=null;
                    ToolUtils.updateUser(MyUser.getCurrentUser(MyUser.class));
                    NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.HIDEN_LOADING));
                    NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.UPDATA_USERINFO));
                }else{
                    Log.e("demo","更新用户信息失败"+e.getMessage());
                }
            }
        });
    }

    private void showPicSelectDialog(Notification notification) {
        picOrsex=notification.id;
        if (notification.id==NotificationDef.EDITOR_HEAR_CLICK){
            firstItem=getString(R.string.camera);
            secondItem=getString(R.string.album);
        }else if (notification.id==NotificationDef.EDITOR_SEX_CLICK) {
            firstItem=getString(R.string.man);
            secondItem=getString(R.string.women);
        }
        try {
            picDialog = new  PicSelectDialog(getContext(),firstItem,secondItem);
            picDialog.setOnalbumSelectListener(new PicSelectDialog.onAlbumSelectListener() {
                @Override
                public void onAlbumClick() {
                    if (picOrsex==NotificationDef.EDITOR_HEAR_CLICK){
                        NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.OPEN_GALLERY));
                    }else {
                        dataBinding.sex.setText(getString(R.string.women));
                    }
                    picDialog.dismiss();
                }
            });
            picDialog.setOncameraSelectListener(new PicSelectDialog.onCameraSelectListener() {
                @Override
                public void onCameraClick() {
                    if (picOrsex==NotificationDef.EDITOR_HEAR_CLICK){
                        NotificationCenter.getNotification().notify(Notification.obtain(NotificationDef.OPEN_CAMERA));
                    }else {
                        dataBinding.sex.setText(getString(R.string.man));
                    }

                    picDialog.dismiss();
                }
            });
            picDialog.setOncancelSelectListener(new PicSelectDialog.onCancelSelectListener() {
                @Override
                public void onCancelClick() {
                    picDialog.dismiss();
                }
            });
            picDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
