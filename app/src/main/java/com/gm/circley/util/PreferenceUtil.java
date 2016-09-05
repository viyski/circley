package com.gm.circley.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lgm on 2016/8/15.
 */
public class PreferenceUtil {

    public static void setBoolean(Context context,String key,boolean value){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    public static boolean getBoolean(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }

    public static void putInt(Context context,String key,int value) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).apply();
    }

    public static int getInt(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getInt(key,-1);
    }

    public static void putStringSet(Context context, String key, Set values) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putStringSet(key,values).apply();
    }

    public static Set<String> getStringSet(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        Set<String> defValues = new HashSet<>();
        return sp.getStringSet(key,defValues);
    }
}
