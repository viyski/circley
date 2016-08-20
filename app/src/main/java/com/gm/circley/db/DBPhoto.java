package com.gm.circley.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lgm on 2016/8/14.
 *
 */
@Entity
public class DBPhoto {

    @Id(autoincrement = true)
    private long id;

    @Property(nameInDb = "IMAGEURL")
    private String imageUrl;

    @Generated(hash = 1214936907)
    public DBPhoto(long id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    @Generated(hash = 119859769)
    public DBPhoto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}