package com.gm.circley.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.gm.circley.R;
import com.gm.circley.base.BaseActivity;
import com.gm.circley.widget.UploadAvatarView;

import butterknife.Bind;
import butterknife.OnClick;

public class UserProfileActivity extends BaseActivity {
    @Bind(R.id.iv_user_avatar)
    UploadAvatarView ivUserAvatar;
    @Bind(R.id.tv_nick_name)
    TextView tvNickName;
    @Bind(R.id.tv_user_sign)
    TextView tvUserSign;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {
        showHomeBack(true, "个人中心");
        setStatusBarTintColor(R.color.darkpurple);
        mToolbar.setBackgroundResource(R.color.darkpurple);
    }

    @Override
    protected void initData() {

    }

    public void logout(View v) {

    }

    @OnClick({R.id.iv_user_avatar, R.id.tv_nick_name, R.id.tv_user_sign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_avatar:

                break;
            case R.id.tv_nick_name:

                break;
            case R.id.tv_user_sign:

                break;
        }
    }
}
