package com.gm.circley.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lgm on 2016/8/30.
 */
public class MusicApiEntity implements Serializable {

    /*{
            "start": 0,
            "count": 10,
            "total": 30,
            "musics" : [Music, ]
    }*/

    private int start;
    private int count;
    private int total;

    public List<MusicEntity> getMusics() {
        return musics;
    }

    public void setMusics(List<MusicEntity> musics) {
        this.musics = musics;
    }

    private List<MusicEntity> musics;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
