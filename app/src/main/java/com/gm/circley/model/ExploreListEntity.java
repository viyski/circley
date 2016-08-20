package com.gm.circley.model;

/**
 * Created by lgm on 2016/8/2.
 */
public class ExploreListEntity {

    private String title;
    private int resId;
    private String activity;

    public ExploreListEntity() {}

    public ExploreListEntity(String title, int resId,String activity) {
        this.title = title;
        this.resId = resId;
        this.activity = activity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
