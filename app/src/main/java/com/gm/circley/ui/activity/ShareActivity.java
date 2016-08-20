package com.gm.circley.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.gm.circley.base.BaseActivity;
import com.gm.circley.interf.BaseUIListener;
import com.gm.circley.interf.ConstantsParams;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

/**
 * Created by lgm on 2016/8/11.
 */
public class ShareActivity extends BaseActivity {

    private Tencent mTencent;
    private IWXAPI wxApi;

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    protected void initView() {
        mTencent = Tencent.createInstance(ConstantsParams.QQ_APP_ID, this.getApplicationContext());
        wxApi = WXAPIFactory.createWXAPI(this,ConstantsParams.WECHAT_APP_ID,true);
        wxApi.registerApp(ConstantsParams.WECHAT_APP_ID);
    }

    @Override
    protected void initData() {

    }

    private BaseUIListener listener = new BaseUIListener(this);

    private void shareToQQ(){
        Bundle bundle = new Bundle();
        //这条分享消息被好友点击后的跳转URL。
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://connect.qq.com/");
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_ SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, "我在测试");
        //分享的图片URL
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
                "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg");
        //分享的消息摘要，最长50个字
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, "测试");
        //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "??我在测试");

        mTencent.shareToQQ(this, bundle, listener);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != mTencent)
            mTencent.onActivityResult(requestCode, resultCode, data);
    }
}
