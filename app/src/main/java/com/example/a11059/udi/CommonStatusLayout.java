package com.example.a11059.udi;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;


/**
 * Created by chenrenzhan on 2017/11/30.
 */

public class CommonStatusLayout extends FrameLayout {
    private LoadingStatusLayout mLoadingLayout;


    private View.OnClickListener mListener;

    public CommonStatusLayout(Context context) {
        super(context);
        createView(context);
    }

    public CommonStatusLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        createView(context);
    }

    public CommonStatusLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        createView(context);
    }

    private void createView(Context context) {
        setClickable(false);
    }

    public void setOnStatusClickListener(@Nullable View.OnClickListener l) {
        mListener = l;
    }

    public void showLoading() {
        if (mLoadingLayout == null) {
            mLoadingLayout = new LoadingStatusLayout(getContext());
        }
        show(mLoadingLayout);
    }

    public void showLoading(String tips, boolean isShowIcon) {
        if (mLoadingLayout == null) {
            mLoadingLayout = new LoadingStatusLayout(getContext());
        }
        showLoading();
        mLoadingLayout.setLoadingIconShow(isShowIcon);
        mLoadingLayout.setLoadingText(tips, true);
    }

    public void showLoadingWithBG(int bgColor) {
        showLoadingWithBG(bgColor,true);
    }

    public void showLoadingWithBG(int bgColor,boolean isshowpro) {
        if (mLoadingLayout == null) {
            mLoadingLayout = new LoadingStatusLayout(getContext());
        }
        mLoadingLayout.setBackgroundColor(bgColor);

        mLoadingLayout.setLoadingProShow(isshowpro);
        showLoading();
    }

    public void hideLoading() {
        hide(mLoadingLayout);
    }











    public void hideAllStatus() {
        hide(mLoadingLayout);

}

    private void hide(final View view) {
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    private void show(View view) {
        if (view != null) {
            hideAllStatus();
            if(view.getParent() != null){
                removeView(view);
            }
            addStatusLayout(view);
            view.setVisibility(View.VISIBLE);
        }
    }

    private void addStatusLayout(View view) {
        if(view == null){
            return;
        }
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
        if(lp == null){
            lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        } else {
            lp.width = FrameLayout.LayoutParams.MATCH_PARENT;
            lp.height = FrameLayout.LayoutParams.MATCH_PARENT;
        }
        this.addView(view, getChildCount(), lp);
    }
}
