package com.gm.circley.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lgm on 2016/8/28.
 */
public class MovieApiEntity implements Serializable {

    /*{
            "count":20,
            "start":0,
            "total":28,
            "subjects":[Object{...}],
            "title":"正在上映的电影-北京"
    }*/

    private int count;
    private int start;
    private int total;
    private List<MovieEntity> subjects;
    private String title;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MovieEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<MovieEntity> subjects) {
        this.subjects = subjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
