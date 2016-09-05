package com.gm.circley.model;

import java.io.Serializable;

/**
 * Created by lgm on 2016/8/30.
 */
public class BookTags implements Serializable {

    private int count;
    private String name;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
