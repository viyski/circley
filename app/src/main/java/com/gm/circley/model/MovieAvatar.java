package com.gm.circley.model;

import java.io.Serializable;

/**
 * Created by lgm on 2016/8/28.
 */
public class MovieAvatar implements Serializable {

    /**
     * small : https://img3.doubanio.com/img/celebrity/small/9714.jpg
     * large : https://img3.doubanio.com/img/celebrity/large/9714.jpg
     * medium : https://img3.doubanio.com/img/celebrity/medium/9714.jpg
     */

    private String small;
    private String large;
    private String medium;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}
