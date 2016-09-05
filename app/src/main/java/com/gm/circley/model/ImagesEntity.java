package com.gm.circley.model;

import java.io.Serializable;

/**
 * Created by lgm on 2016/8/28.
 */
public class ImagesEntity implements Serializable {

    /**
     * small : https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p2375019545.jpg
     * large : https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2375019545.jpg
     * medium : https://img3.doubanio.com/view/movie_poster_cover/spst/public/p2375019545.jpg
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
