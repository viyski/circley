package com.gm.circley.util;

import android.app.Activity;
import android.os.Process;

import java.util.Stack;

/**
 * Created by lgm on 2016/7/17.
 * </p> Activity管理类
 */
public class AppManager {

    private static AppManager mAppManager;
    private static Stack<Activity> activityStack;


    private AppManager(){}

    public static AppManager getAppManager(){
        if (mAppManager == null)
            mAppManager = new AppManager();
        return mAppManager;
    }

    public void addActivity(Activity activity){
        if (activityStack == null)
            activityStack = new Stack<>();
        activityStack.add(activity);
    }

    public void finishActivity(Activity activity){
        if (activity != null && !activity.isFinishing()){
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void finishAllActivity(){
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                finishActivity(activityStack.get(i));
                break;
            }
        }

        activityStack.clear();
    }

    public void finishActivity(Class<?> clz){
        for (Activity activity : activityStack){
            if (activity.getClass().equals(clz)) {
                finishActivity(activity);
                break;
            }
        }
    }

    public Activity getActivity(Class<?> clz){
        if (activityStack != null){
            for (Activity activity : activityStack){
                if (activity.getClass().equals(clz))
                    return activity;
            }
        }
        return null;
    }

    public void AppExit(){
        try {
            finishAllActivity();
            Process.killProcess(Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
