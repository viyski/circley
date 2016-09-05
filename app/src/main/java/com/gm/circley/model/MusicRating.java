package com.gm.circley.model;

import java.io.Serializable;

/**
 * Created by lgm on 2016/8/30.
 */
public class MusicRating implements Serializable {

    /**
     * max : 10
     * average : 0.0
     * numRaters : 20
     * min : 0
     */

    private int max;
    private float average;
    private int numRaters;
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

    public int getNumRaters() {
        return numRaters;
    }

    public void setNumRaters(int numRaters) {
        this.numRaters = numRaters;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
