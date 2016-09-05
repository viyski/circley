package com.gm.circley;

import android.app.Application;
import android.content.Context;

import com.framework.DroidFramework;
import com.gm.circley.interf.ConstantsParams;

import java.io.File;

import cn.bmob.v3.Bmob;
import im.fir.sdk.FIR;

/**
 * Created by viysk on 2016/7/13.
 *
 */
public class BaseApplication extends Application {

    public static final String APP_ROOT_DIR = "circley";
    public static final String APP_CACHE_DIR = APP_ROOT_DIR + File.separator + "cache";
    private static Context mContext;
    private static BaseApplication mAppInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppInstance = this;
        mContext = getApplicationContext();

        DroidFramework.init(this, BuildConfig.DEBUG, BuildConfig.LOG_DEBUG);
        FIR.init(this);
        Bmob.initialize(this, ConstantsParams.BMOB_APP_ID);
    }

    public static BaseApplication getAppInstance() {
        return mAppInstance;
    }

    public static Context getAppContext() {
        return mContext;
    }

}
