package com.gm.circley.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.gm.circley.R;
import com.gm.circley.base.BaseActivity;
import com.gm.circley.control.manager.ImageManager;
import com.gm.circley.widget.CircleIndicator;
import com.gm.circley.widget.ViewPagerFixed;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageActivity extends BaseActivity implements PhotoViewAttacher.OnPhotoTapListener{

    @Bind(R.id.view_pager)
    ViewPagerFixed viewPager;
    @Bind(R.id.circle_indicator)
    CircleIndicator circleIndicator;

    private String[] picUrls;
    private int index;
    private int size;

    @Override
    protected void requestWindowFeature() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_image;
    }

    @Override
    protected void initView() {}

    @Override
    protected void initData() {
        Intent intent = getIntent();
        index = intent.getIntExtra("index",0);
        picUrls = intent.getStringArrayExtra("picUrls");
        size = picUrls.length;

        circleIndicator.setIndicatorSize(size);

        viewPager.setAdapter(new CheckImageAdapter(this, picUrls));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(index);
        circleIndicator.notifySelectedPositionChanged(index);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                circleIndicator.notifySelectedPositionChanged(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onPhotoTap(View view, float x, float y) {
        onBackPressed();
    }

    private class CheckImageAdapter extends PagerAdapter{

        private Context context;
        private String[] picUrls;
        private ImageManager imageManager;

        public CheckImageAdapter(Context context,String[] picUrls){
            this.context = context;
            this.picUrls = picUrls;
            imageManager = new ImageManager(context);
        }
        @Override
        public int getCount() {
            return picUrls == null ? 0 : picUrls.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setOnPhotoTapListener(ImageActivity.this);
            imageManager.loadUrlImage(picUrls[position],photoView);
            container.addView(photoView);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
