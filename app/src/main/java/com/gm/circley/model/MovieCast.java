package com.gm.circley.model;

import java.io.Serializable;

/**
 * Created by lgm on 2016/8/28.
 */
public class MovieCast implements Serializable {

    /**
     * alt : https://movie.douban.com/celebrity/1054443/
     * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/620.jpg","large":"https://img3.doubanio.com/img/celebrity/large/620.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/620.jpg"}
     * name : 马特·达蒙
     * id : 1054443
     */

    private String alt;
    private MovieAvatar avatars;
    private String name;
    private String id;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public MovieAvatar getAvatars() {
        return avatars;
    }

    public void setAvatars(MovieAvatar avatars) {
        this.avatars = avatars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
