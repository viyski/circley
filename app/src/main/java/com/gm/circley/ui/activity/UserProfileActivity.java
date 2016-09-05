package com.gm.circley.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.framework.dialog.ToastTip;
import com.gm.circley.BaseApplication;
import com.gm.circley.R;
import com.gm.circley.base.BaseActivity;
import com.gm.circley.control.SingleControl;
import com.gm.circley.control.UIHelper;
import com.gm.circley.interf.ConstantsParams;
import com.gm.circley.interf.EventType;
import com.gm.circley.model.UserEntity;
import com.gm.circley.model.event.EventEntity;
import com.gm.circley.util.image.GetPathFromUri4kitkat;
import com.gm.circley.widget.ActionSheet;
import com.gm.circley.widget.UploadAvatarView;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;

public class UserProfileActivity extends BaseActivity<SingleControl> {
    @Bind(R.id.iv_user_avatar)
    UploadAvatarView ivUserAvatar;
    @Bind(R.id.tv_nick_name)
    TextView tvNickName;
    @Bind(R.id.tv_user_sign)
    TextView tvUserSign;

    private String takePicturePath = "/" + BaseApplication.APP_CACHE_DIR + "/avatar.jpg";
    private String imagePath;

    private final String NICK_NAME = "nick_name";
    private final String USER_SIGN = "user_sign";


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {
        showHomeBack(true, "个人中心");
        setStatusBarTintColor(R.color.darkpurple);
        mToolbar.setBackgroundResource(R.color.darkpurple);

        mUserEntity = BmobUser.getCurrentUser(this, UserEntity.class);

    }

    @Override
    protected void initData() {
        if (mUserEntity != null){
            if (!TextUtils.isEmpty(mUserEntity.getUserAvatar()))
                mImageManager.loadCircleUrlImage(mUserEntity.getUserAvatar(),ivUserAvatar);
            else
                mImageManager.loadCircleUrlImage("http://a1.qpic.cn/psb?/V11OmR2B18Wklo/iw9VCzL*TPuiGdW8la5EbQ3xRi967Efh*VqVmZ5CUaA!/b/dAsBAAAAAAAA&bo=gAJyBAAAAAAFB9A!&rf=viewer_4",ivUserAvatar);

            if (!TextUtils.isEmpty(mUserEntity.getNickName()))
                tvNickName.setText(mUserEntity.getNickName());
            else
                tvNickName.setText("reese");

            if (!TextUtils.isEmpty(mUserEntity.getUserSign()))
                tvUserSign.setText(mUserEntity.getUsername());
        }
    }

    @OnClick({R.id.iv_user_avatar, R.id.tv_nick_name, R.id.tv_user_sign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_avatar:
                showSelectAvatarDialog();
                break;
            case R.id.tv_nick_name:
                updateNickName("昵称", NICK_NAME, mUserEntity.getNickName());
                break;
            case R.id.tv_user_sign:
                updateNickName("个性签名", USER_SIGN, mUserEntity.getUserSign());
                break;
        }
    }

    public void showSelectAvatarDialog() {
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles("拍照", "从相册选择")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        switch (index) {
                            case 0:
                                UIHelper.gotoTakePicture(UserProfileActivity.this, takePicturePath);
                                break;
                            case 1:
                                UIHelper.gotoChoosePicture(UserProfileActivity.this);
                                break;
                        }
                    }
                }).show();
    }


    private void setImageViewWithPath(String imagePath) {
        mImageManager.loadCircleLocalImage(imagePath, ivUserAvatar);
        mControl.getCompressImagePath(this, imagePath); //异步压缩图片
    }

    public void getCompressImagePathCallBack() {
        String compressImagePath = mModel.get(1);
        if (TextUtils.isEmpty(compressImagePath)) {
            ToastTip.show("请重新选择图片");
        } else {
            uploadAvatar(compressImagePath);
        }
    }

    private void uploadAvatar(String imagePath) {
        BmobProFile.getInstance(this).upload(imagePath, new UploadListener() {

            @Override
            public void onSuccess(String fileName, String url, BmobFile file) {
                Logger.i("fileName：" + fileName);
                Logger.i("url：" + url);
                Logger.i("file.getUrl()：" + file.getUrl());

                mUserEntity.setUserAvatar(file.getUrl());
                mUserEntity.update(UserProfileActivity.this, mUserEntity.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        EventBus.getDefault().post(new EventEntity(EventType.EVENT_TYPE_UPDATE_BLOG_LIST));
                        ivUserAvatar.setProgressOver();
                        setResult(ConstantsParams.PROFILE_REQUEST_CODE);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(UserProfileActivity.this, "头像上传失败", Toast.LENGTH_SHORT).show();
                        ivUserAvatar.setProgressOver();
                    }
                });
            }

            @Override
            public void onProgress(int progress) {
                ivUserAvatar.setProgress(progress);
            }

            @Override
            public void onError(int statusCode, String errorMsg) {
                Toast.makeText(UserProfileActivity.this, "文件上传失败", Toast.LENGTH_SHORT).show();
                ivUserAvatar.setProgressOver();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ConstantsParams.TAKE_PICTURE_REQUEST_CODE:
                    imagePath = Environment.getExternalStorageDirectory() + takePicturePath;
                    setImageViewWithPath(imagePath);
                    break;
                case ConstantsParams.CHOOSE_PICTURE_REQUEST_CODE:
                    Uri uri = data.getData();
                    imagePath = GetPathFromUri4kitkat.getPath(this, data.getData());
                    if (TextUtils.isEmpty(imagePath)) {
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

    private void gotoMain() {
        if (TextUtils.isEmpty(mUserEntity.getUserAvatar())) {
            ToastTip.show("请上传您的靓照哦");
            return;
        }
        if (TextUtils.isEmpty(mUserEntity.getNickName())) {
            ToastTip.show("给自己起个昵称吧");
            return;
        }
        if (TextUtils.isEmpty(mUserEntity.getUserSign())) {
            ToastTip.show("来句个性说说吧");
            return;
        }
        UIHelper.gotoMainActivity(this);
    }

    private View view;
    private EditText editText;
    private String inputText;

    private void updateNickName(final String title, final String type, final String content) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                .title(title)
                .customView(R.layout.material_dialog_input_layout, true)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .negativeColor(getResources().getColor(R.color.font_black_3))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        inputText = editText.getText().toString().trim();
                        if (!TextUtils.isEmpty(inputText)) {
                            switch (type) {
                                case NICK_NAME:
                                    mUserEntity.setNickName(inputText);
                                    break;
                                case USER_SIGN:
                                    mUserEntity.setUserSign(inputText);
                                    break;
                            }

                            mUserEntity.update(UserProfileActivity.this, mUserEntity.getObjectId(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    EventBus.getDefault().post(new EventEntity(EventType.EVENT_TYPE_UPDATE_BLOG_LIST));
                                    setResult(ConstantsParams.PROFILE_REQUEST_CODE);
                                    switch (type) {
                                        case NICK_NAME:
                                            tvNickName.setText(inputText);
                                            break;
                                        case USER_SIGN:
                                            tvUserSign.setText(inputText);
                                            break;
                                    }
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(UserProfileActivity.this, "修改" + title + "失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).build();

        view = materialDialog.getCustomView();
        editText = ButterKnife.findById(view, R.id.et_dialog_input);
        if (!TextUtils.isEmpty(content)) {
            editText.setText(content);
            editText.setSelection(content.length());
        } else {
            editText.setHint("请输入" + title);
        }
        materialDialog.show();
    }
}
