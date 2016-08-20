package com.gm.circley.util;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by lgm on 2016/7/16.
 */
public class FastJsonUtil {

    public static String toJson(Object obj){
        return JSON.toJSONString(obj);
    }

    public static <T> T parseJson(String jsonStr,Class<T> clazz){
        return JSON.parseObject(jsonStr,clazz);
    }

    public static <T> List<T> parseJsonArray(String jsonStr, Class<T> clz){
        return JSON.parseArray(jsonStr,clz);
    }

}
