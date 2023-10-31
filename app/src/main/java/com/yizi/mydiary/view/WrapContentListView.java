package com.yizi.mydiary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 *@Author: create by ziyi
 *@Function: 重写listview，让listview能够和scrollview一起滚动
*/
public class WrapContentListView extends ListView {
    public WrapContentListView(Context context) {
        super(context);
    }

    public WrapContentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapContentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
