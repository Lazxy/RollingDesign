package com.practice.li.rollingdesign.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.practice.li.rollingdesign.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lazxy on 2017/3/15.
 */

public class TagFlowLayout extends ViewGroup {

    private List<String> mTagList;
    private List<Integer> mLineHeightList;
    private OnTagItemClickListener mListener;

    public TagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTagList=new ArrayList<>();
        mLineHeightList=new ArrayList<>();
        setSystemUiVisibility(INVISIBLE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        /*父布局参数，准备传回的值*/
        int maxLineWidth=0;
        int totalHeight=0;

        /*子控件决定的参数*/
        int currentLineWidth=0;
        int currentLineHeight=0;
        int childViewWidth;
        int childViewHeight;
        MarginLayoutParams layoutParams;

        int count=getChildCount();
        for(int i=0;i<count;i++){
            View child=getChildAt(i);
            if(child.getVisibility()!=GONE){
                measureChild(child,widthMeasureSpec,heightMeasureSpec);
                layoutParams=(MarginLayoutParams)child.getLayoutParams();
                childViewWidth=child.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;
                childViewHeight=child.getMeasuredHeight()+layoutParams.topMargin+layoutParams.bottomMargin;
                if(childViewWidth+currentLineWidth>widthSize){
                    /*另起一行*/
                    totalHeight+=childViewHeight;
                    /*由于上一行已满，长度可能超过最大长度，故进行判断*/
                    if(maxLineWidth<currentLineWidth) maxLineWidth=currentLineWidth;
                    /*将新一行的高和宽重置*/
                    currentLineWidth=childViewWidth;
                    currentLineHeight=childViewHeight;
                }else{
                    /*在原来的行中增加宽度*/
                    currentLineWidth+=childViewWidth;
                    /*初始化当前高度，理论上应该只会起效一次*/
                    if(currentLineHeight<childViewHeight) currentLineHeight=childViewHeight;
                }
            }

        }
         /*当布局的高和宽为定值时，采用其原来的高和宽，否则采用上面计算出来的高和宽，相当于wrap_content*/
        setMeasuredDimension(widthMode==MeasureSpec.EXACTLY?widthSize:maxLineWidth,
                heightMode==MeasureSpec.EXACTLY?heightSize:totalHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        /*行数*/
        int currentLines=1;
        int currentLineWidth=0;
        int maxLineHeight=0;
        int childViewHeight;
        int childViewWidth;
        int count=getChildCount();
        MarginLayoutParams layoutParams;
        for(int i=0;i<count;i++){
            int cl=0,ct=0,cr=0,cb=0;
            View child=getChildAt(i);
            if(child.getVisibility()!=GONE){
                layoutParams=(MarginLayoutParams)child.getLayoutParams();
                childViewHeight=child.getMeasuredHeight()+layoutParams.topMargin+layoutParams.bottomMargin;
                childViewWidth=child.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;
                if(childViewWidth+currentLineWidth>getWidth()){
                    /*另起一行,先将上一行的最大高度加入集合，再初始化一些参数*/
                    mLineHeightList.add(maxLineHeight);
                    currentLines++;
                    currentLineWidth=childViewWidth;
                    maxLineHeight=childViewHeight;
                    cl=layoutParams.leftMargin;
                }else{
                    cl=layoutParams.leftMargin+currentLineWidth;
                    currentLineWidth+=childViewWidth;
                    if(maxLineHeight<childViewHeight) maxLineHeight=childViewHeight;
                }
                /*如果此时行数大于1，则会先加上前面各行最大高度再加边距；否则直接等于边距*/
                for (int j = 0; j < currentLines - 1;j++) {
                    ct += mLineHeightList.get(j);
                }
                ct+=layoutParams.topMargin;
                cr=cl+child.getMeasuredWidth();
                cb=ct+child.getMeasuredHeight();
                child.layout(cl,ct,cr,cb);
            }

        }

    }
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public void setTag(List<String> tags){
        LayoutInflater inflater=LayoutInflater.from(getContext());
        mTagList.clear();
        mLineHeightList.clear();
        mTagList.addAll(tags);
        for(int i=0;i < mTagList.size(); i++){
            TextView tag=(TextView)inflater.inflate(R.layout.layout_tag_flow,this,false);
            tag.setText(mTagList.get(i));
            tag.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null)
                        mListener.onTagItemClick(v);
                }
            });
            addView(tag);

        }
    }

    public void setOnTagItemClickListener(OnTagItemClickListener listener){
        this.mListener=listener;
    }

    interface OnTagItemClickListener{
        void onTagItemClick(View v);
    }
}
