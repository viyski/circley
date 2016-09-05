package com.gm.circley.model;

import java.io.Serializable;

/**
 * Created by lgm on 2016/8/28.
 */
public class MovieRating implements Serializable {

    /**
     * max : 10
     * average : 7.4
     * stars : 40
     * min : 0
     */

    private int max;
    private float average;
    private String stars;
    private int min;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
