package com.gm.circley.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.bmob.BmobProFile;
import com.framework.dialog.TipDialog;
import com.framework.dialog.ToastTip;
import com.gm.circley.BaseApplication;
import com.gm.circley.R;
import com.gm.circley.base.BaseActivity;
import com.gm.circley.control.SingleControl;
import com.gm.circley.control.UIHelper;
import com.gm.circley.interf.ConstantsParams;
import com.gm.circley.interf.EventType;
import com.gm.circley.model.BlogEntity;
import com.gm.circley.model.UserEntity;
import com.gm.circley.model.event.EventEntity;
import com.gm.circley.util.DateUtil;
import com.gm.circley.util.DialogUtil;
import com.gm.circley.util.DisplayUtil;
import com.gm.circley.util.NetUtil;
import com.gm.circley.util.SelectorUtil;
import com.gm.circley.util.image.GetPathFromUri4kitkat;
import com.gm.circley.widget.ActionSheet;
import com.gm.circley.widget.UploadImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by lgm on 2016/8/2.
 */
public class EditNewBlogActivity extends BaseActivity<SingleControl> {

    @Bind(R.id.et_website)
    EditText etWebsite;
    @Bind(R.id.cv_website)
    CardView cvWebsite;
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
        return R.layout.activity_edit_new_blog;
    }

    @Override
    protected void initView() {
        showHomeBack(true, "添加收藏");
        setStatusBarTintColor(R.color.teal);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            mToolbar.setBackground(new ColorDrawable(getResources().getColor(R.color.teal)));

        mBlogEntity = new BlogEntity();
        if (getIntent() != null) {
            if (getIntent().hasExtra("url")) {
                etWebsite.setText(getIntent().getStringExtra("url"));
            }
        }
    }

    @Override
    protected void initData() {
        hsImages.setVisibility(View.GONE);
        setRectShapeViewBackground(rlBottomLayout);
        setRoundShapeViewBackground(ivImage);
        setRoundShapeViewBackground(tvCommit);
    }

    private void setRoundShapeViewBackground(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(SelectorUtil.createRoundRectShapeSelector(getResources().getColor(R.color.teal)));
        } else {
            view.setBackgroundDrawable(SelectorUtil.createRoundRectShapeSelector(getResources().getColor(R.color.teal)));
        }
    }

    private void setRectShapeViewBackground(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(SelectorUtil.createRectShapeSelector(getResources().getColor(R.color.teal)));
        } else {
            view.setBackgroundDrawable(SelectorUtil.createRectShapeSelector(getResources().getColor(R.color.teal)));
        }
    }

    private void checkClipboard() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = clipboardManager.getPrimaryClip();
        if (clipData == null) return;

        ClipData.Item clipDataItem = clipData.getItemAt(0);
        final String text = clipDataItem.getText().toString();
        if (!TextUtils.isEmpty(text) && text.startsWith("http") && isShareDes && !text.equals(getSettingsSharedPreferences().newBlogDes())) {
            TipDialog dialog = new TipDialog(this);
            dialog.show("将复制的内容粘贴到文章描述处", text, "立即粘贴", "暂不", new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    super.onPositive(dialog);
                    isShareDes = false;
                    etDescribe.setText(text);
                }

                @Override
                public void onNegative(MaterialDialog dialog) {
                    super.onNegative(dialog);
                    isShareDes = false;
                    getSettingsSharedPreferences().newBlogDes(text);
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkClipboard();
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
        String website = etWebsite.getText().toString().trim();
        if (TextUtils.isEmpty(website)) {
            ToastTip.show(getString(R.string.hint_input_website));
            return;
        }

        if (!NetUtil.isLinkAvailable(website)) {
            ToastTip.show(getString(R.string.hint_input_website_available));
            return;
        }

        String describe = etDescribe.getText().toString();
        if (TextUtils.isEmpty(describe)) {
            ToastTip.show(getString(R.string.hint_input_describe));
            return;
        }

        mBlogEntity.setWebsite(website);
        mBlogEntity.setDescribe(describe);
        mBlogEntity.setCreateTime(DateUtil.getCurrentMillis());
        mUser = BmobUser.getCurrentUser(this,UserEntity.class);
        mBlogEntity.setUserId(mUser.getObjectId());
        mBlogEntity.setUserEntity(mUser);

        loadingDialog.show();
        if (mBlogEntity.getImageList() != null && mBlogEntity.getImageList().size() > 0) {
            List<String> list = mBlogEntity.getImageList();
            int size = list.size();
            final String[] filePaths = list.toArray(new String[size]);
            BmobProFile.getInstance(this).uploadBatch(filePaths, new com.bmob.btp.callback.UploadBatchListener() {
                @Override
                public void onSuccess(boolean isFinish, String[] strings, String[] strings1, BmobFile[] bmobFiles) {
                    if (isFinish){
                        for (UploadImageView item :uploadImageViews) {
                            item.setProgressFinish();
                        }

                        mBlogEntity.setImageList(getBmobUrls(bmobFiles));
                        mBlogEntity.save(mContext, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                resultSuccess();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                resultFail();
                            }
                        });
                    }
                }

                @Override
                public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                    uploadImageViews.get(curIndex - 1).setProgress(curPercent);
                }

                @Override
                public void onError(int i, String s) {
                    resultFail();
                }
            });
        } else {
            mBlogEntity.save(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    resultSuccess();
                }

                @Override
                public void onFailure(int i, String s) {
                    resultFail();
                }
            });
        }
    }

    private List<String> getBmobUrls(BmobFile[] files) {
        List<String> urls = new ArrayList<>();
        for (BmobFile file : files){
            urls.add(file.getUrl());
        }
        return urls;
    }

    private void resultSuccess() {
        getSettingsSharedPreferences().newBlogUrl(etWebsite.getText().toString());
        getSettingsSharedPreferences().newBlogDes(etDescribe.getText().toString());
        EventBus.getDefault().post(new EventEntity(EventType.EVENT_TYPE_UPDATE_BLOG_LIST));

        loadingDialog.dismiss();
        DialogUtil.showSuccessDialog(this, new DialogUtil.DismissListener() {
            @Override
            public void onDismiss() {
                finish();
            }
        });
    }

    private void resultFail() {
        loadingDialog.dismiss();
        ToastTip.show(getString(R.string.str_commit_fail));
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
                                takePicturePath = "/" + BaseApplication.APP_CACHE_DIR + "/" + DateUtil.getCurrentMillis() + ".jpg";
                                UIHelper.gotoTakePicture(EditNewBlogActivity.this, takePicturePath);
                                break;
                            case 1:
                                UIHelper.gotoChoosePicture(EditNewBlogActivity.this);
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
                    setImageViewWithPath(Environment.getExternalStorageDirectory()+takePicturePath);
                    break;
                case ConstantsParams.CHOOSE_PICTURE_REQUEST_CODE:
                    Uri uri = data.getData();
                    String imagePath = GetPathFromUri4kitkat.getPath(this, uri);
                    if (!TextUtils.isEmpty(imagePath)){
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
        mControl.getCompressImagePath(this,imagePath); // 异步压缩图片
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

            mImageManager.loadLocalImage(imageList.get(position),ivSelectedImage);
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
                    DisplayUtil.hideKeyboard(EditNewBlogActivity.this);
                    showSelectImageDialog();
                }
            }
        });

        ivSelectedImage.setImageResource(R.mipmap.ic_add_image);
        llContainer.addView(view);
    }
}
