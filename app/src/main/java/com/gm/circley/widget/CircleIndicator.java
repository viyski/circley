package com.gm.circley.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.gm.circley.R;
import com.gm.circley.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgm on 2016/7/30.
 */
public class CircleIndicator extends LinearLayout {

    private LayoutParams lp;
    private Context context;
    private List<View> viewList;

    public CircleIndicator(Context context) {
        this(context,null);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        int dip = DisplayUtil.dip2px(context,6);
        lp = new LayoutParams(dip,dip);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        lp.setMargins(5,0,5,0);
        viewList = new ArrayList<>();
    }

    public void setIndicatorSize(int size){
        for (int i = 0; i < size; i++) {
            View view = new View(context);
            view.setBackgroundResource(R.drawable.selector_circle_indicator);
            view.setLayoutParams(lp);

            if (i == 0)
                view.setSelected(true);
            else
                view.setSelected(false);
            if (size == 1)
                view.setVisibility(View.INVISIBLE);
            viewList.add(view);
            addView(view);
        }
    }

    public void notifySelectedPositionChanged(int index){
        for (int i = 0; i < viewList.size(); i++) {
            if (i == index){
                viewList.get(i).setSelected(true);
            }else{
                viewList.get(i).setSelected(false);
            }
        }
    }

}
