package com.gm.circley.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lgm on 2016/7/16.
 */
public class NetUtil {

    private static ConnectivityManager getConnectivityManager(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager;
    }

    /**
     * 网络是否连接
     * @param context
     * @return
     */
    public static boolean isNetConnected(Context context){
        ConnectivityManager manager = getConnectivityManager(context);
        NetworkInfo[] allNetworkInfo = manager.getAllNetworkInfo();
        if (allNetworkInfo != null){
            for (NetworkInfo info : allNetworkInfo){
                if(info.isConnected())
                    return true;
            }
        }
        return false;
    }

    /**
     * wifi网络连接
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context){
        ConnectivityManager cm = getConnectivityManager(context);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI)
            return true;
        return false;
    }

    /**
     * 3g网络连接
     * @param context
     * @return
     */
    public static boolean is3gConnected(Context context){
        ConnectivityManager cm = getConnectivityManager(context);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE)
            return true;
        return false;
    }

    /**
     * 检测网址链接是否有效
     * @param link
     * @return
     */
    public static boolean isLinkAvailable(String link) {
        Pattern pattern = Pattern.compile("^(http://|https://)?((?:[A-Za-z0-9]+-[A-Za-z0-9]+|[A-Za-z0-9]+)\\.)+([A-Za-z]+)[/\\?\\:]?.*$",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(link);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

}
