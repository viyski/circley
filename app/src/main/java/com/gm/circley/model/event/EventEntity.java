package com.gm.circley.model.event;

import java.io.Serializable;

/**
 * Created by lgm on 2016/7/24.
 */
public class EventEntity implements Serializable {

    private String type;
    private int arg1;
    private int arg2;
    private String str;
    private Object obj;

    public EventEntity(String type) {
        this.type = type;
    }

    public EventEntity(String type, int arg1) {
        this(type);
        this.arg1 = arg1;
    }

    public EventEntity(String type, String str) {
        this(type);
        this.str = str;
    }

    public EventEntity(String type, Object obj) {
        this(type);
        this.obj = obj;
    }

    public EventEntity(String type, int arg1, Object obj) {
        this(type, arg1);
        this.obj = obj;
    }

    public EventEntity(String type, int arg1, String str) {
        this(type, arg1);
        this.str = str;
    }

    public EventEntity(String type, int arg1, int arg2, Object obj) {
        this(type, arg1, obj);
        this.arg2 = arg2;
    }

    public EventEntity(String type, int arg1, int arg2, String str) {
        this(type, arg1, str);
        this.arg2 = arg2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getArg1() {
        return arg1;
    }

    public void setArg1(int arg1) {
        this.arg1 = arg1;
    }

    public int getArg2() {
        return arg2;
    }

    public void setArg2(int arg2) {
        this.arg2 = arg2;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
