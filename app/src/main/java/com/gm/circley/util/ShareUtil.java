package com.gm.circley.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.framework.dialog.ToastTip;
import com.gm.circley.R;
import com.gm.circley.model.AppEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgm on 2016/7/17.
 * </p>
 * 分享
 */
public class ShareUtil {

    public static AppEntity getAppEntity(Context context) {
        AppEntity appEntity = new AppEntity();
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            appEntity.setPackageName(packageInfo.packageName);
            appEntity.setSrcPath(applicationInfo.publicSourceDir);
            appEntity.setAppName(applicationInfo.loadLabel(packageManager).toString());
            appEntity.setAppIcon(applicationInfo.loadIcon(packageManager));
            appEntity.setVersionName(packageInfo.versionName);
            appEntity.setVersionCode(packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appEntity;
    }

    public static void share(Context context) {
        share(context, context.getString(R.string.share_text));
    }

    public static void share(Context context, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share));
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent,context.getString(R.string.share)));
    }

    /**
     * 反馈
     */
    public static void feedback(Context context) {
        Uri uri = Uri.parse("mailto:viyski@163.com");
        final Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (infos == null || infos.size() <= 0){
            ToastTip.show(context.getString(R.string.no_email_app_tip));
            return;
        }
        context.startActivity(intent);
    }

    public static void sendToFriend(Context context){
        AppEntity appEntity = getAppEntity(context);
        PackageManager pm = context.getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(sendIntent,0);

        List<LabeledIntent> labelIntentList = new ArrayList<>();
        for (ResolveInfo resolveInfo : resolveInfos){
            String packageName = resolveInfo.activityInfo.packageName;
            if (packageName.contains("tencent")){
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName,resolveInfo.activityInfo.name));
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(new File(appEntity.getSrcPath())));
                intent.setAction(Intent.ACTION_SEND);
                labelIntentList.add(
                        new LabeledIntent(intent,packageName,resolveInfo.loadLabel(pm),resolveInfo.icon));
            }
        }

        LabeledIntent[] extraIntents = labelIntentList.toArray(new LabeledIntent[labelIntentList.size()]);
        Intent openInChooser = Intent.createChooser(labelIntentList.remove(0),
                context.getString(R.string.transfer_apk_to_friend, appEntity.getAppName()));
        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS,extraIntents);
        context.startActivity(openInChooser);

    }
}