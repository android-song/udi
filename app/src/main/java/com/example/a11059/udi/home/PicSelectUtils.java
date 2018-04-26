package com.example.a11059.udi.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by Administrator on 2018/4/23.
 */

public class PicSelectUtils {

    /**
     * 打开相册
     */
    private void cameraPic(Activity mActivity) {
//        //创建一个file，用来存储拍照后的照片
//        File outputfile = new File(mActivity.getExternalCacheDir(),"output.png");
//        try {
//            if (outputfile.exists()){
//                outputfile.delete();//删除
//            }
//            outputfile.createNewFile();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Uri imageuri ;
//        if (Build.VERSION.SDK_INT >= 24){
//            imageuri = FileProvider.getUriForFile(mActivity,
//                    "com.rachel.studyapp.fileprovider", //可以是任意字符串
//                    outputfile);
//        }else{
//            imageuri = Uri.fromFile(outputfile);
//        }
//        //启动相机程序
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
//        startActivityForResult(intent,ToolUtils.PHONE_CAMERA);
    }
}
