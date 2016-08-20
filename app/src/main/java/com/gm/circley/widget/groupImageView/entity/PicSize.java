package com.gm.circley.widget.groupImageView.entity;

import java.io.Serializable;

/**
 * Created by lgm 16/7/18.
 */
public class PicSize implements Serializable {

    private String key;
    private int width;
    private int height;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
