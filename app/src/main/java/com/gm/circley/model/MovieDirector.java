package com.gm.circley.model;

import java.io.Serializable;

/**
 * Created by lgm on 2016/8/28.
 */
public class MovieDirector implements Serializable {

    /**
     * alt : https://movie.douban.com/celebrity/1025193/
     * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/9714.jpg","large":"https://img3.doubanio.com/img/celebrity/large/9714.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/9714.jpg"}
     * name : 保罗·格林格拉斯
     * id : 1025193
     */

    private String alt;
    private String name;
    private MovieAvatar avatars;
    private String id;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
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

    public MovieAvatar getAvatars() {
        return avatars;
    }

    public void setAvatars(MovieAvatar avatars) {
        this.avatars = avatars;
    }
}
