package com.example.a11059.udi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * Created by chenrenzhan on 2017/11/30.
 */

public class LoadingStatusLayout extends FrameLayout {
    private ProgressBar mProgressBar; // 默认显示
    private ImageView mLoadingIcon; // 默认隐藏
    private TextView mLoadingText; // 默认隐藏
    private TextView mName;

    public LoadingStatusLayout(Context context) {
        super(context);
        createView(context);
    }

    public LoadingStatusLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        createView(context);
    }

    public LoadingStatusLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        createView(context);
    }

    private void createView(Context context) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_loading_status, this);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_progress);
        mLoadingIcon = (ImageView) findViewById(R.id.loading_icon);
        mLoadingText = (TextView) findViewById(R.id.loading_text);
        final int iconHeight = 50;
        final int iconLeftMargin =5;
        final int iconRightMargin =5;
//        LinearLayout nameContainer = new LinearLayout(context);
//        RelativeLayout.LayoutParams nameContainerLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        nameContainerLayoutParams.leftMargin = 5;
//        nameContainerLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
//
//        nameContainer.setLayoutParams(nameContainerLayoutParams);
//        nameContainer.setOrientation(LinearLayout.VERTICAL);
//
//        mName = new TextView(context);
//        mName.setTextColor(getResources().getColor(R.color.colorPrimary));
//        mName.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
//        mName.setGravity(Gravity.LEFT);
//        setNameMaxWidth(200);
////        mName.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
//        mName.setSingleLine();
//        mName.setEllipsize(TextUtils.TruncateAt.END);
////        nameContainer.addView(mName);
//
//        mName.setSingleLine();
//        mName.setText("dsdasddsadasd");
//
//
//        mName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        addView(mName);
    }
    public void setNameMaxWidth(int maxWidth) {
        if (mName != null) {
            mName.setMaxWidth(maxWidth);
        }
    }
    public void setLoadingIconShow(boolean isShow) {
        if (mLoadingIcon != null) {
            mLoadingIcon.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    public void setLoadingText(String text, boolean isShow) {
        if (mLoadingText != null && text != null) {
            mLoadingText.setVisibility(isShow ? View.VISIBLE : View.GONE);
            mLoadingText.setText(text);
        }
    }

    public void setLoadingProShow(boolean isShow) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }


}
