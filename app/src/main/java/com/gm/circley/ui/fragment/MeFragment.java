package com.gm.circley.ui.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gm.circley.R;
import com.gm.circley.base.BaseFragment;
import com.gm.circley.model.UserEntity;
import com.gm.circley.ui.activity.MyCollectActivity;
import com.gm.circley.ui.activity.SettingActivity;
import com.gm.circley.ui.activity.UserProfileActivity;
import com.gm.circley.util.ShareUtil;
import com.gm.circley.widget.PullToZoomListView;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;

/**
 * Created by lgm on 2016/7/17.
 */
public class MeFragment extends BaseFragment{

    @Bind(R.id.list_view)
    PullToZoomListView listView;
    private ImageView ivAvatar;
    private TextView tvUsername;
    private UserEntity mUser;

    @Override
    protected View initView() {
        return mInflater.inflate(R.layout.fragment_me, null);
    }

    @Override
    protected void initData() {
        View headView = mInflater.inflate(R.layout.item_header_me, null);
        headView.findViewById(R.id.ll_msg_root).setOnClickListener(this);
        headView.findViewById(R.id.ll_collect).setOnClickListener(this);
        headView.findViewById(R.id.ll_share).setOnClickListener(this);
        headView.findViewById(R.id.ll_setting).setOnClickListener(this);
        ivAvatar = (ImageView) headView.findViewById(R.id.iv_avatar);
        tvUsername = (TextView) headView.findViewById(R.id.tv_username);
        listView.addHeaderView(headView);
        listView.setAdapter(null);
        mUser = BmobUser.getCurrentUser(mContext, UserEntity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_msg_root:
                startActivity(new Intent(mContext,UserProfileActivity.class));
                mActivity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.ll_collect:
                startActivity(new Intent(mContext,MyCollectActivity.class));
                mActivity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.ll_share:
                ShareUtil.share(mContext);
                break;
            case R.id.ll_setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                mActivity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mUser != null){
            String nickName = mUser.getNickName();
            String userAvatar = mUser.getUserAvatar();
            tvUsername.setText("reese");
            mImageManager.loadCircleUrlImage("http://a1.qpic.cn/psb?/V11OmR2B18Wklo/iw9VCzL*TPuiGdW8la5EbQ3xRi967Efh*VqVmZ5CUaA!/b/dAsBAAAAAAAA&bo=gAJyBAAAAAAFB9A!&rf=viewer_4",ivAvatar);
            if (!TextUtils.isEmpty(nickName)){
                tvUsername.setText(nickName);
            }

            if(!TextUtils.isEmpty(userAvatar)){
                mImageManager.loadCircleUrlImage(userAvatar,ivAvatar);
            }
        }
    }
}
