package com.example.a11059.udi.take;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a11059.udi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11059 on 2016/12/13.
 */

//自定义viewGroup需要重写onMeasure和onLayout方法
public class ItemViewDemo extends ViewGroup {

    private List<String> lables;
    public ItemViewDemo(Context context) {
        super(context);
    }

    public ItemViewDemo(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public ItemViewDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
   measureChildren(widthMeasureSpec,heightMeasureSpec);
        //得到宽高
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int height;
        int width=MeasureSpec.getSize(widthMeasureSpec);

        if (heightMode==MeasureSpec.EXACTLY){
            height=MeasureSpec.getSize(heightMeasureSpec);
        }else {
            if (getChildCount()<=0)
            {

                height=0;//没有标签，设高度为0
            }else {
                int row=1;//设为1行
                int widthSpec=width;//剩余的宽度
                for(int i=0;i<getChildCount();i++){
                 View view=getChildAt(i);
                  int childw=view.getMeasuredWidth();
                    if (widthSpec>childw){
                        widthSpec-=childw;//设置剩余的宽度
                    }else {
                        row++;
                        widthSpec=width-childw;//如果是下一行，需要重新计算宽度
                    }
                    widthSpec-=10;//减去左边距
                }
                //得到测量的高度
               int  Childheight=getChildAt(0).getMeasuredHeight();//子控件的高度
               //总的高度=行数*每个view的高度+（行数-1）view之间的间隙
                height=(row*Childheight)+(row-1)*10;
                System.out.println("onmeasure"+height+"wwww"+width+"rrrr"+row+"chilad"+Childheight);

            }
        }
        //设置viewGroup的高度

                setMeasuredDimension(width,height+20);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//onLayout的主要功能：设置每一个子view的位置，就是x，y
         int right=0;
         int bootom=0;
        int row=0;
          for(int i=0;i<getChildCount();i++){
             View childView= getChildAt(i);
             int width=childView.getMeasuredWidth();
              int height=childView.getMeasuredHeight();
//              int height=20;
//              int width=30;
              //设置每一个view的右边和底部坐标
              right+=width;
              //底部的坐标=行数*（view的高度+10）+view高度
              bootom=row*(height+10)+height;
              if (right>r+10){
                  row++;
                  right=width;
                  bootom=row*(height+10)+height;
              }
             System.out.println("zzzzzzzzzz"+right+"bbbbbbbbbb"+bootom+"hhhhhh"+height);
              childView.layout(right-width,bootom-height,right,height);
              right+=10;
          }


    }


    //setlables的主要作用： 给自定义的viewGroup添加子view，并且设置每一个子view的状态和颜色
    public  void setlables(List<String> lables,boolean add){
        //对lables进行初始化
        if (this.lables==null){
            this.lables=new ArrayList<>();
        }
        //判断是否添加，不是添加的话就全部清除，然后赋值
        if (add){
            this.lables.addAll(lables);
        }else {
            this.lables.clear();
            this.lables=lables;
        }


        System.out.println("7777777size"+lables.size());
        if (lables!=null&&lables.size()>0){
            for (final String text:lables){
                final TextView textView= (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_lable,null);
                textView.setText(text);
                textView.setTextColor(getResources().getColor(R.color.blue));
                textView.setSelected(true);
                addView(textView);
            }

        }
    }
}
