package com.gm.circley.ui.activity.explore;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.framework.dialog.ToastTip;
import com.gm.circley.R;
import com.gm.circley.base.BaseActivity;
import com.gm.circley.control.PageControl;
import com.gm.circley.control.manager.DBManager;
import com.gm.circley.db.DBPhoto;
import com.gm.circley.util.PreferenceUtil;
import com.gm.circley.widget.ViewPagerFixed;
import com.mingle.widget.LoadingView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoExploreActivity extends BaseActivity<PageControl> implements PhotoViewAttacher.OnPhotoTapListener, ViewPager.OnPageChangeListener {

    private static final String TAG = PhotoExploreActivity.class.getName();
    @Bind(R.id.tv_status)
    TextView tvStatus;
    @Bind(R.id.view_pager)
    ViewPagerFixed viewPager;
    @Bind(R.id.loadingView)
    LoadingView loadingView;
    @Bind(R.id.tv_photo_count)
    TextView tvPhotoCount;
    @Bind(R.id.iv_photo_select)
    ImageView ivPhotoSelect;
    @Bind(R.id.iv_photo_save)
    ImageView ivPhotoSave;
    private List<DBPhoto> mData;
    private DBManager dbManager;
    private List<PhotoView> viewList;
    private int mPosition;
    private String saveImageUrl;

    @Override
    protected void requestWindowFeature() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_photo_explore;
    }

    @Override
    protected void initView() {
        loadData();
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        viewList = new ArrayList<>();

        mControl.getPhotoDataFromDB(dbManager);
        viewPager.setOffscreenPageLimit(1);
    }

    public void getDataSuccess() {
        loadingView.setVisibility(View.GONE);
        mData = mModel.getList(1);
        if (mData != null) {
            for (int i = 0; i < mData.size(); i++) {
                PhotoView photoView = new PhotoView(mContext);
                photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                photoView.setOnPhotoTapListener(this);
                viewList.add(photoView);
            }

            tvPhotoCount.setText(1 + " /  " + mData.size());
            viewPager.setAdapter(new ImagePagerAdapter(mData, viewList));
            viewPager.addOnPageChangeListener(this);
        }
    }

    public void getDataEmpty() {
        loadingView.setVisibility(View.GONE);
        tvStatus.setVisibility(View.VISIBLE);
        tvStatus.setText(R.string.no_data);
    }

    public void getDataFailed() {
        loadingView.setVisibility(View.GONE);
        tvStatus.setVisibility(View.VISIBLE);
        tvStatus.setText(R.string.load_data_failed);
    }

    private void loadData() {
        if (dbManager == null) {
            dbManager = DBManager.getInstance(mContext);
        }

        boolean isAdded = PreferenceUtil.getBoolean(mContext, "isAdded");
        if (!isAdded) {
            boolean isSuccess = mControl.savePhotoList(mContext, dbManager);
            isAdded = isSuccess;
        }
    }

    @Override
    public void onPhotoTap(View view, float x, float y) {
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        tvPhotoCount.setText((position + 1) + " /  " + mData.size());
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.iv_photo_save, R.id.iv_photo_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_photo_save:
                savePhotoToMediaStore();
                break;
            case R.id.iv_photo_select:
                selectPhotoPos();
                break;
        }
    }

    private void selectPhotoPos() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext);
        builder.title("选择图片位置");
        builder.inputType(InputType.TYPE_CLASS_NUMBER);
        builder.input("请输入1 - "+mData.size()+"的之间的数字","", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        try {
                            if (!TextUtils.isEmpty(input)) {
                                int i = Integer.parseInt(input.toString());
                                if (i == -1 || i > mData.size()){
                                    ToastTip.show("请按提示输入跳转位置!");
                                    return;
                                }else{
                                    viewPager.setCurrentItem(i - 1);
                                }
                            }
                        } catch (NumberFormatException e) {
                            ToastTip.show("请输入数字!");
                        }
                    }
                }).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void savePhotoToMediaStore() {
        int photoPos = PreferenceUtil.getInt(mContext, "photo_pos");
        Log.d("-----MM","photoPos:"+photoPos + ":::"+mPosition );
        if (photoPos == mPosition) {
            ToastTip.show("该图片已保存过!");
            return;
        } else {
            DBPhoto dbPhoto = mData.get(mPosition);
            if (dbPhoto != null) {
                saveImageUrl = dbPhoto.getImageUrl();
                if (!TextUtils.isEmpty(saveImageUrl)) {
                    mControl.savePhotoToMediaStore(mContext, saveImageUrl);
                }
            }

        }
    }

    public void savePhotoSuccess() {
        ToastTip.show("保存成功");
        PreferenceUtil.putInt(mContext, "photo_pos", mPosition);
    }

    public void savePhotoFail() {
        ToastTip.show("保存失败");
    }

    static class ImagePagerAdapter extends PagerAdapter {

        private List<DBPhoto> data;
        private List<PhotoView> viewList;

        public ImagePagerAdapter(List<DBPhoto> data, List<PhotoView> viewList) {
            this.data = data;
            this.viewList = viewList;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            PhotoView photoView = viewList.get(position);
            Picasso.with(container.getContext())
                    .load(data.get(position).getImageUrl())
                    .placeholder(R.color.font_black_6)
                    .error(R.color.font_black_6)
                    .into(photoView);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

}
