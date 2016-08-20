package com.gm.circley.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by lgm on 2016/7/17.
 * </p>
 */
public class Utils {

    private static Toast mToast;
    private static ProgressDialog mProgressDialog;

    public static String[] getStrArray(Context context, int arrayResId){
        return context.getResources().getStringArray(arrayResId);
    }

    public static String getString(Context context,int strResId){
        return context.getResources().getString(strResId);
    }

    public static void showResultDialog(Context context, String msg,
                                              String title) {
        if(msg == null) return;
        String rmsg = msg.replace(",", "\n");
        Log.d("Util", rmsg);
        new AlertDialog.Builder(context).setTitle(title).setMessage(rmsg)
                .setNegativeButton("知道了", null).create().show();
    }


    public static final void dismissDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }


    public static final void showProgressDialog(Context context, String title,
                                                String message) {
        dismissDialog();
        if (TextUtils.isEmpty(title)) {
            title = "请稍候";
        }
        if (TextUtils.isEmpty(message)) {
            message = "正在加载...";
        }
        mProgressDialog = ProgressDialog.show(context, title, message);
    }

    /**
     * 打印消息并且用Toast显示消息
     *
     * @param activity
     * @param message
     * @param logLevel
     *            填d, w, e分别代表debug, warn, error; 默认是debug
     */
    public static final void toastMessage(final Activity activity,
                                          final String message, String logLevel) {
        if ("w".equals(logLevel)) {
            Log.w("sdkDemo", message);
        } else if ("e".equals(logLevel)) {
            Log.e("sdkDemo", message);
        } else {
            Log.d("sdkDemo", message);
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (mToast != null) {
                    mToast.cancel();
                    mToast = null;
                }
                mToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }


    /**
     * 打印消息并且用Toast显示消息
     *
     * @param activity
     * @param message
     * @param logLevel
     *            填d, w, e分别代表debug, warn, error; 默认是debug
     */
    public static final void toastMessage(final Activity activity,
                                          final String message) {
        toastMessage(activity, message, null);
    }

}
