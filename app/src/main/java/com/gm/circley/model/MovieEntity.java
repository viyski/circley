package com.gm.circley.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lgm on 2016/8/28.
 */
public class MovieEntity implements Serializable{

    /*{
            "rating":Object{...},
            "genres":Array[3],
            "title":"谍影重重5",
            "casts":Array[3],
            "collect_count":41789,
            "original_title":"Jason Bourne",
            "subtype":"movie",
            "directors":[Object{...}],
            "year":"2016",
            "images":Object{...},
            "alt":"https://movie.douban.com/subject/26266072/",
            "id":"26266072"
    }*/

    private MovieRating rating;
    private String title;
    private int collect_count;
    private String original_title;
    private String subtype;
    private String year;
    private ImagesEntity images;
    private String alt;
    private String id;
    private List<String> genres;
    private List<MovieCast> casts;
    private List<MovieDirector> directors;

    public MovieRating getRating() {
        return rating;
    }

    public void setRating(MovieRating rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ImagesEntity getImages() {
        return images;
    }

    public void setImages(ImagesEntity images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<MovieCast> getCasts() {
        return casts;
    }

    public void setCasts(List<MovieCast> casts) {
        this.casts = casts;
    }

    public List<MovieDirector> getDirectors() {
        return directors;
    }

    public void setDirectors(List<MovieDirector> directors) {
        this.directors = directors;
    }

}
