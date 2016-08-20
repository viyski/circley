package com.gm.circley.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Created by lgm on 2016/7/16.
 * </p>
 */
public class AppUtil {

    public static String getVersionName(Context context){
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            if (!TextUtils.isEmpty(version))
                return "V" + version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "V1.0.0";
    }

    public static int getVersionCode(Context context){
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int versionCode = info.versionCode;
            if (versionCode != 0)
                return versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 75;
    }
}
