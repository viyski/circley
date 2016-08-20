package com.gm.circley.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.gm.circley.interf.ConstantsParams;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by lgm on 2016/8/11.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this,ConstantsParams.WECHAT_APP_ID,true);
        api.registerApp(ConstantsParams.WECHAT_APP_ID);

        api.handleIntent(getIntent(),this);

    }

    @Override
    public void onReq(BaseReq baseReq) {
        // 请求
    }

    @Override
    public void onResp(BaseResp baseResp) {
        // 响应
    }
}
