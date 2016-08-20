package com.gm.circley.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.framework.dialog.ToastTip;
import com.framework.okhttp.OkHttpProxy;
import com.framework.okhttp.callback.JsonCallBack;
import com.framework.okhttp.request.RequestCall;
import com.gm.circley.R;
import com.gm.circley.base.BaseActivity;
import com.gm.circley.control.UIHelper;
import com.gm.circley.interf.ConstantsParams;
import com.gm.circley.interf.UrlParams;
import com.gm.circley.model.QQUserInfoEntity;
import com.gm.circley.model.SinaUserInfoEntity;
import com.gm.circley.model.UserEntity;
import com.gm.circley.util.FastJsonUtil;
import com.orhanobut.logger.Logger;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.UpdateListener;
import okhttp3.Call;

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getName();
    private static final int SINA_WEIBO_LOGIN = 87;
    private static final int QQ_LOGIN = 88;

    @Bind(R.id.ll_wechat_weibo)
    LinearLayout llWechatWeibo;
    @Bind(R.id.ll_qq_login)
    LinearLayout llQqLogin;
    private Tencent mTencent;
    private UserInfo mInfo;
    private BmobUser.BmobThirdUserAuth thirdAuthInfo;
    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;

    @Override
    protected void requestWindowFeature() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mSwipeBackLayout.getEnableGesture()) {
            mSwipeBackLayout.setEnableGesture(false);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void initView() {
        setStatusBarTintColor(R.color.transparent);
    }

    @Override
    protected void initData() {

    }

    public void gotoMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick({R.id.ll_wechat_weibo, R.id.ll_qq_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_wechat_weibo:
                login2Weibo();
                break;
            case R.id.ll_qq_login:
                login2QQ();
                break;
        }
    }

    private void login2Weibo() {
        mAuthInfo = new AuthInfo(this, ConstantsParams.SINA_APP_KEY,ConstantsParams.SINA_REDIRECT_URL,ConstantsParams.SINA_SCOPE);
        mSsoHandler = new SsoHandler(this,mAuthInfo);
        mSsoHandler.authorize(new AuthListener());
    }

    private void login2QQ() {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(ConstantsParams.QQ_APP_ID, this.getApplicationContext());
        }
        mTencent.logout(this);
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
        }
    }

    private class AuthListener implements WeiboAuthListener{

        @Override
        public void onComplete(Bundle bundle) {
            mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
            if (mAccessToken.isSessionValid()) {
                getAccountSharedPreferences().access_token(mAccessToken.getToken());
                getAccountSharedPreferences().uid(mAccessToken.getUid());
                getAccountSharedPreferences().expires_in(mAccessToken.getExpiresTime());
                getAccountSharedPreferences().refresh_token(mAccessToken.getRefreshToken());

                loginSuccess(SINA_WEIBO_LOGIN);
            } else {
                String code = bundle.getString("code");
                String message = getString(R.string.str_auth_fail);
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                ToastTip.show(message);
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            ToastTip.show("授权出错");
        }

        @Override
        public void onCancel() {
            ToastTip.show("授权取消");
        }
    }

    private IUiListener loginListener = new IUiListener() {
        @Override
        public void onComplete(Object response) {
            if (null == response) {
                ToastTip.show(getString(R.string.str_login_fail));
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                ToastTip.show(getString(R.string.str_login_fail));
                return;
            }
            try {
                String token = jsonResponse.getString(Constants.PARAM_ACCESS_TOKEN);
                String expires = jsonResponse.getString(Constants.PARAM_EXPIRES_IN);
                String openId = jsonResponse.getString(Constants.PARAM_OPEN_ID);

                getAccountSharedPreferences().access_token(token);
                getAccountSharedPreferences().uid(openId);
                getAccountSharedPreferences().expires_in(Long.parseLong(expires));

                // login success
                loginSuccess(QQ_LOGIN);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onError(UiError uiError) {
            ToastTip.show("QQ授权出错!");
        }

        @Override
        public void onCancel() {
            ToastTip.show("授权取消!");
        }
    };

    private void loginSuccess(int loginType){
        if (loadingDialog != null){
            loadingDialog.showCancelDialog(getString(R.string.logging));
        }

        switch (loginType){
            case SINA_WEIBO_LOGIN:
                thirdAuthInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_WEIBO,mAccessToken.getToken(),
                        mAccessToken.getExpiresTime()+"",mAccessToken.getUid());
                BmobUser.loginWithAuthData(LoginActivity.this, thirdAuthInfo, new OtherLoginListener() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        mUserEntity = BmobUser.getCurrentUser(LoginActivity.this, UserEntity.class);
                        getSinaUserInfo();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        ToastTip.show(getString(R.string.str_login_fail));
                        loadingDialog.dismiss();
                    }
                });
                break;
            case QQ_LOGIN:

                thirdAuthInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ,
                        getAccountSharedPreferences().access_token(),getAccountSharedPreferences().expires_in()+"",getAccountSharedPreferences().uid());

                BmobUser.loginWithAuthData(LoginActivity.this, thirdAuthInfo, new OtherLoginListener() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        mUserEntity = BmobUser.getCurrentUser(LoginActivity.this, UserEntity.class);
                        getUserInfo();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        ToastTip.show(getString(R.string.str_login_fail) + ":" + i + "s:"+s);
                        loadingDialog.dismiss();
                    }
                });
                break;
        }
    }

    private void getSinaUserInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("access_token",getAccountSharedPreferences().access_token());
        params.put("uid",getAccountSharedPreferences().uid());
        RequestCall build = OkHttpProxy.get().url(UrlParams.URL_SINA_USER_INFO).params(params).build();
        build.execute(new JsonCallBack<SinaUserInfoEntity>(){

            @Override
            public void onSuccess(SinaUserInfoEntity response) {
                updateUserInfo(response);
            }

            @Override
            public void onFailure(Call request, Exception e) {
                gotoMainOrProfile();
            }
        });
    }

    private void getUserInfo() {
        if (mTencent != null) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {
                    Logger.i("获取QQ用户信息失败!");
                }

                @Override
                public void onComplete(final Object response) {
                    JSONObject json = (JSONObject)response;
                    Logger.i(TAG,response.toString());
                    if (json.has("ret") && json.optString("ret").equals("0")){
                        QQUserInfoEntity entity = FastJsonUtil.parseJson(json.toString(), QQUserInfoEntity.class);
                        if (entity == null){
                            gotoMainOrProfile();
                            return;
                        }

                        boolean isUpdate = false;
                        if (TextUtils.isEmpty(mUserEntity.getNickName()) && !TextUtils.isEmpty(entity.getNickname())){
                            mUserEntity.setNickName(entity.getNickname());
                            isUpdate = true;
                        }

                        if (TextUtils.isEmpty(mUserEntity.getUserAvatar()) && !TextUtils.isEmpty(entity.getFigureurl_1())){
                            mUserEntity.setUserAvatar(entity.getFigureurl_1());
                            isUpdate = true;
                        }

                        if (isUpdate){
                            mUserEntity.update(LoginActivity.this, mUserEntity.getObjectId(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    gotoMainOrProfile();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    gotoMainOrProfile();
                                }
                            });
                        }else{
                            gotoMainOrProfile();
                        }
                    }else{
                        gotoMainOrProfile();
                    }
                }

                @Override
                public void onCancel() {
                    ToastTip.show("取消授权!");
                }
            };
            mInfo = new UserInfo(LoginActivity.this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);
        }
    }

    private void updateUserInfo(SinaUserInfoEntity entity){
        if (entity == null){
            gotoMainOrProfile();
            return;
        }

        boolean isUpdate = false;
        if (TextUtils.isEmpty(mUserEntity.getNickName()) && !TextUtils.isEmpty(entity.getScreen_name())){
            mUserEntity.setNickName(entity.getName());
            isUpdate = true;
        }

        if (TextUtils.isEmpty(mUserEntity.getUserAvatar()) && !TextUtils.isEmpty(entity.getAvatar_hd())){
            mUserEntity.setUserAvatar(entity.getAvatar_hd());
            isUpdate = true;
        }

        if (TextUtils.isEmpty(mUserEntity.getUserSign()) && !TextUtils.isEmpty(entity.getDescription())){
            mUserEntity.setUserSign(entity.getDescription());
            isUpdate = true;
        }
        if (isUpdate){
            mUserEntity.update(LoginActivity.this, mUserEntity.getObjectId(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    gotoMainOrProfile();
                }

                @Override
                public void onFailure(int i, String s) {
                    gotoMainOrProfile();
                }
            });
        }else{
            gotoMainOrProfile();
        }
    }

    private void gotoMainOrProfile(){
        loadingDialog.dismiss();
        UIHelper.gotoMainActivity(LoginActivity.this);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (loadingDialog != null){
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        }

        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

    }

}
