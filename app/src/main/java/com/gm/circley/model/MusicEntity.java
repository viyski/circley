package com.gm.circley.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lgm on 2016/8/30.
 */
public class MusicEntity implements Serializable{

    /**
     * id : 10000037
     * title : 我只在乎你
     * alt : https://music.douban.com/music/10000037
     * author : [{"name":"邓丽君"}]
     * alt_title : 留聲經典復刻版
     * tags : [{"count":20,"name":"经典"},{"count":20,"name":"邓丽君"}]
     * summary :
     * image : https://img3.doubanio.com/spic/s11185741.jpg
     * mobile_link :
     * rating : {"max":10,"average":"0.0","numRaters":20,"min":0}
     */

    private int id;
    private String title;
    private String alt;
    private String alt_title;
    private String summary;
    private String image;
    private String mobile_link;
    private MusicRating rating;
    private MusicAttrs attrs;
    private List<MusicAuthor> author;
    private List<MusicTags> tags;

    public MusicAttrs getAttrs() {
        return attrs;
    }

    public void setAttrs(MusicAttrs attrs) {
        this.attrs = attrs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMobile_link() {
        return mobile_link;
    }

    public void setMobile_link(String mobile_link) {
        this.mobile_link = mobile_link;
    }

    public MusicRating getRating() {
        return rating;
    }

    public void setRating(MusicRating rating) {
        this.rating = rating;
    }

    public List<MusicAuthor> getAuthor() {
        return author;
    }

    public void setAuthor(List<MusicAuthor> author) {
        this.author = author;
    }

    public List<MusicTags> getTags() {
        return tags;
    }

    public void setTags(List<MusicTags> tags) {
        this.tags = tags;
    }

    public static class MusicAuthor implements Serializable{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class MusicTags implements Serializable{
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

}
