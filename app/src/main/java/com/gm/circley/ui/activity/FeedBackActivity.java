package com.gm.circley.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.framework.dialog.ToastTip;
import com.gm.circley.BaseApplication;
import com.gm.circley.R;
import com.gm.circley.base.BaseActivity;
import com.gm.circley.control.UIHelper;
import com.gm.circley.interf.ConstantsParams;
import com.gm.circley.model.BlogEntity;
import com.gm.circley.model.UserEntity;
import com.gm.circley.util.DateUtil;
import com.gm.circley.util.DisplayUtil;
import com.gm.circley.util.SelectorUtil;
import com.gm.circley.util.image.GetPathFromUri4kitkat;
import com.gm.circley.widget.ActionSheet;
import com.gm.circley.widget.UploadImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * Created by lgm on 2016/9/1.
 */
public class FeedBackActivity extends BaseActivity{

    @Bind(R.id.et_describe)
    EditText etDescribe;
    @Bind(R.id.cv_describe)
    CardView cvDescribe;
    @Bind(R.id.ll_container)
    LinearLayout llContainer;
    @Bind(R.id.hs_images)
    HorizontalScrollView hsImages;
    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.tv_commit)
    TextView tvCommit;
    @Bind(R.id.rl_bottom_layout)
    RelativeLayout rlBottomLayout;

    private String takePicturePath;
    private BlogEntity mBlogEntity;
    private boolean isShareDes = true;
    private UserEntity mUser;
    private List<UploadImageView> uploadImageViews;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        setStatusBarTintColor(R.color.darkpurple);
        showHomeBack(true,"反馈");
        mToolbar.setBackgroundResource(R.color.darkpurple);
        mBlogEntity = new BlogEntity();
        mUser = BmobUser.getCurrentUser(FeedBackActivity.this,UserEntity.class);
    }

    @Override
    protected void initData() {
        setRectShapeViewBackground(rlBottomLayout);
        setRoundShapeViewBackground(ivImage);
        setRoundShapeViewBackground(tvCommit);
    }

    private void setRoundShapeViewBackground(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(SelectorUtil.createRoundRectShapeSelector(getResources().getColor(R.color.darkpurple)));
        } else {
            view.setBackgroundDrawable(SelectorUtil.createRoundRectShapeSelector(getResources().getColor(R.color.darkpurple)));
        }
    }

    private void setRectShapeViewBackground(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(SelectorUtil.createRectShapeSelector(getResources().getColor(R.color.darkpurple)));
        } else {
            view.setBackgroundDrawable(SelectorUtil.createRectShapeSelector(getResources().getColor(R.color.darkpurple)));
        }
    }

    @OnClick({R.id.iv_image, R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_image:
                if (mBlogEntity.getImageList() != null && mBlogEntity.getImageList().size() >= 9) {
                    ToastTip.show("最多上传9张图片噢!");
                } else {
                    DisplayUtil.hideKeyboard(this);
                    showSelectImageDialog();
                }
                break;
            case R.id.tv_commit:
                uploadNewBlog();
                break;
        }
    }

    private void uploadNewBlog() {

        String describe = etDescribe.getText().toString();
        if (TextUtils.isEmpty(describe)) {
            ToastTip.show(getString(R.string.hint_input_describe));
            return;
        }


        loadingDialog.show();
        SystemClock.sleep(1000);
        loadingDialog.dismiss();
    }

    private void showSelectImageDialog() {
        ActionSheet.createBuilder(mContext, getSupportFragmentManager())
                .setCancelButtonTitle(R.string.str_cancel)
                .setOtherButtonTitles("拍照", "相册")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        switch (index) {
                            case 0:
                                File dirFile = new File(BaseApplication.APP_CACHE_DIR);
                                if (!dirFile.exists()){
                                    dirFile.mkdirs();
                                }
                                takePicturePath = dirFile.getAbsolutePath() + "/" + DateUtil.getCurrentMillis() + ".jpg";
                                UIHelper.gotoTakePicture(FeedBackActivity.this, takePicturePath);
                                break;
                            case 1:
                                UIHelper.gotoChoosePicture(FeedBackActivity.this);
                                break;
                        }

                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case ConstantsParams.TAKE_PICTURE_REQUEST_CODE:
                    setImageViewWithPath(Environment.getExternalStorageDirectory() + takePicturePath);
                    break;
                case ConstantsParams.CHOOSE_PICTURE_REQUEST_CODE:
                    Uri uri = data.getData();
                    String imagePath = GetPathFromUri4kitkat.getPath(this, data.getData());
                    if (TextUtils.isEmpty(imagePath)){
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                            cursor.close();
                        }
                    }

                    setImageViewWithPath(imagePath);
                    break;
            }
        }

    }

    private void setImageViewWithPath(String imagePath) {
        //mControl.getCompressImagePath(this,imagePath); // 异步压缩图片
    }

    public void getCompressImagePathCallBack(){
        String imagePath = mModel.get(1);
        if (TextUtils.isEmpty(imagePath)){
            ToastTip.show("请重新选择图片");
        }else{
            handlerCompressImageViewWithPath(imagePath);
        }
    }

    private void handlerCompressImageViewWithPath(String imagePath) {
        hsImages.setVisibility(View.VISIBLE);
        List<String> list = mBlogEntity.getImageList();
        if (list == null || list.size() == 0){
            list = new ArrayList<>();
        }
        list.add(imagePath);
        mBlogEntity.setImageList(list);
        updateImageLayout();
    }

    private void updateImageLayout() {
        llContainer.removeAllViews();
        uploadImageViews = new ArrayList<>();
        List<String> imageList = mBlogEntity.getImageList();
        int size = imageList.size();

        for (int i = 0; i < size; i++) {
            final int position = i;
            View view = LayoutInflater.from(this).inflate(R.layout.item_image_pick_layout, null);
            UploadImageView ivSelectedImage = ButterKnife.findById(view,R.id.iv_selected_image);
            uploadImageViews.add(ivSelectedImage);
            ImageView ivDeleteImage = ButterKnife.findById(view,R.id.iv_delete_image);
            ivDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBlogEntity.getImageList().remove(position);
                    updateImageLayout();
                    if (mBlogEntity.getImageList() == null || mBlogEntity.getImageList().size() == 0){
                        hsImages.setVisibility(View.GONE);
                    }
                }
            });

            mImageManager.loadLocalImage(imageList.get(i),ivSelectedImage);
            llContainer.addView(view);
        }

        View view = LayoutInflater.from(this).inflate(R.layout.item_image_pick_layout, null);
        UploadImageView ivSelectedImage = ButterKnife.findById(view,R.id.iv_selected_image);
        ImageView ivDeleteImage = ButterKnife.findById(view,R.id.iv_delete_image);
        ivSelectedImage.setProgressFinish();
        ivDeleteImage.setVisibility(View.GONE);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlogEntity.getImageList() != null && mBlogEntity.getImageList().size() >= 9){
                    ToastTip.show("最多上传9张图片!");
                }else{
                    DisplayUtil.hideKeyboard(FeedBackActivity.this);
                    showSelectImageDialog();
                }
            }
        });

        ivSelectedImage.setImageResource(R.mipmap.ic_add_image);
        llContainer.addView(view);
    }}
