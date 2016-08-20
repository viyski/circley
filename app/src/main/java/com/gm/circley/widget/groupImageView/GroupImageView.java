package com.gm.circley.widget.groupImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gm.circley.R;
import com.gm.circley.control.manager.ImageManager;
import com.gm.circley.ui.activity.ImageActivity;
import com.gm.circley.util.DisplayUtil;
import com.gm.circley.util.GlobalParams;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by lgm on 16/7/18.
 */
public class GroupImageView extends ViewGroup implements View.OnClickListener {

    public static final String TAG = GroupImageView.class.getSimpleName();
    private Context mContext;

    private int mWidth;
    private int gap;

    private List<String> picUrls;
    private Rect[] picRects;
    private ImageManager imageManager;

    private static Rect[] smallRectArr = null;

    private PhotoViewAttacher mAttacher;
    private int position;
    private Activity actvity;

    public GroupImageView(Context context) {
        super(context);
        init(context);
    }

    public GroupImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GroupImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        imageManager = new ImageManager(context);
        gap = getResources().getDimensionPixelSize(R.dimen.gap_pics);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (picRects == null)
            return;

        for (int i = 0; i < getChildCount(); i++) {
            if (i < picRects.length) {
                Rect imgRect = picRects[i];
                ImageView childView = (ImageView) getChildAt(i);
                childView.layout(imgRect.left, imgRect.top, imgRect.right, imgRect.bottom);
            } else { //隐藏多余的View
                break;
            }
        }
    }

    public void setPics(Activity activity, int position, List<String> picUrls) {
        this.picUrls = picUrls;
        this.position = position;
        this.actvity = activity;

        if (picUrls == null || picUrls.size() == 0) {
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);
            setPadding(0, DisplayUtil.dip2px(mContext, 5), 0, 0);
            displayGroupImageView();
        }
    }

    private void displayGroupImageView() {
        int maxWidth = GlobalParams.screenWidth - DisplayUtil.dip2px(mContext, 10 * 2);
        mWidth = Math.round(maxWidth * 1.0f);
        picRects = null;
        int size = picUrls.size();

        int imgW = Math.round((mWidth - 2 * gap) * 1.0f / 3.0f);
        int imgH = imgW;
        LinearLayout.LayoutParams layoutParams = null;

        //4个特殊情况，上2个下2个
        if (size == 4) {
            layoutParams = new LinearLayout.LayoutParams(mWidth, imgH * 2 + gap);
            picRects = new Rect[4];
            picRects[0] = new Rect(0, 0, imgW, imgH);
            picRects[1] = new Rect(imgW + gap, 0, imgW * 2 + gap, imgH);
            picRects[2] = new Rect(0, imgH + gap, imgW, imgH * 2 + gap);
            picRects[3] = new Rect(imgW + gap, imgH + gap, imgW * 2 + gap, imgH * 2 + gap);
        } else {
            int oneWidth = maxWidth / 3;
            int height = 0;
            switch (size) {
                case 1:
                    oneWidth = maxWidth;
                    height = maxWidth / 2;
                    break;
                case 2:
                case 3:
                    height = imgH;
                    break;
                case 5:
                case 6:
                    height = imgH * 2 + gap;
                    break;
                case 7:
                case 8:
                case 9:
                    height = imgH * 3 + gap * 2;
                    break;
            }
            layoutParams = new LinearLayout.LayoutParams(mWidth, height);

            //当只有一个图片的时候，特殊处理
            if (size == 1) {
                Rect oneRect = new Rect();
                oneRect.left = 0;
                oneRect.top = 0;
                oneRect.right = oneWidth;
                oneRect.bottom = height;
                picRects = new Rect[]{oneRect};
            } else {
                picRects = new Rect[size];
                for (int i = 0; i < size; i++) {
                    picRects[i] = getSmallRectArr()[i];
                }
            }
        }

        setLayoutParams(layoutParams);
        displayPics();
        requestLayout(); //重新绘制
    }

    private Rect[] getSmallRectArr() {
        if (smallRectArr != null) {
            return smallRectArr;
        }

        int imgW = Math.round((mWidth - 2 * gap) * 1.0f / 3.0f);
        int imgH = imgW;

        Rect[] tempRects = new Rect[9];

        tempRects[0] = new Rect(0, 0, imgW, imgH);
        tempRects[1] = new Rect(imgW + gap, 0, imgW * 2 + gap, imgH);
        tempRects[2] = new Rect(mWidth - imgW, 0, mWidth, imgH);

        tempRects[3] = new Rect(0, imgH + gap, imgW, imgH * 2 + gap);
        tempRects[4] = new Rect(imgW + gap, imgH + gap, imgW * 2 + gap, imgH * 2 + gap);
        tempRects[5] = new Rect(mWidth - imgW, imgH + gap, mWidth, imgH * 2 + gap);

        tempRects[6] = new Rect(0, imgH * 2 + gap * 2, imgW, imgH * 3 + gap * 2);
        tempRects[7] = new Rect(imgW + gap, imgH * 2 + gap * 2, imgW * 2 + gap, imgH * 3 + gap * 2);
        tempRects[8] = new Rect(mWidth - imgW, imgH * 2 + gap * 2, mWidth, imgH * 3 + gap * 2);

        smallRectArr = tempRects;
        return smallRectArr;
    }

    public void displayPics() {
        if (picRects == null || picUrls == null || picUrls.size() == 0)
            return;

        for (int i = 0; i < getChildCount(); i++) {
            ImageView imgView = (ImageView) getChildAt(i);

            if (i >= picRects.length) {
                // 隐藏多余的View
                getChildAt(i).setVisibility(View.GONE);
            } else {
                imgView.setOnClickListener(this);
                imgView.setTag(R.id.tag_index, i);
                imgView.setTag(R.id.tag_pic_urls, picUrls);
                imgView.setTag(R.id.tag_position, position);

                Rect imgRect = picRects[i];
                imgView.setVisibility(View.VISIBLE);
                imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imgView.setLayoutParams(new LayoutParams(imgRect.right - imgRect.left, imgRect.bottom - imgRect.top));
                imageManager.loadUrlImage(picUrls.get(i), imgView);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int index = (int) v.getTag(R.id.tag_index);
        int position = (int) v.getTag(R.id.tag_position);
        List<String> picUrls = (List<String>) v.getTag(R.id.tag_pic_urls);

        gotoImageActivity(actvity, v, index, picUrls);
    }

    private void gotoImageActivity(Activity activity, View view, int index, List<String> picUrls) {
        Intent intent = new Intent(mContext, ImageActivity.class);
        intent.putExtra("index", index);
        intent.putExtra("picUrls", picUrls.toArray(new String[picUrls.size()]));
        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        activity.startActivity(intent);
    }

}
