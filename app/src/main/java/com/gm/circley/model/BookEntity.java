package com.gm.circley.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lgm on 2016/8/30.
 */
public class BookEntity implements Serializable {  //implements Parcelable
    /**
     * id : 1003078
     * isbn10 : 7505715666
     * isbn13 : 9787505715660
     * title : 小王子
     * origin_title :
     * alt_title :
     * subtitle :
     * url : https://api.douban.com/v2/book/1003078
     * alt : https://book.douban.com/subject/1003078/
     * image : https://img3.doubanio.com/mpic/s1001902.jpg
     * images : {"small":"https://img3.doubanio.com/spic/s1001902.jpg","large":"https://img3.doubanio.com/lpic/s1001902.jpg","medium":"https://img3.doubanio.com/mpic/s1001902.jpg"}
     * author : ["（法）圣埃克苏佩里"]
     * translator : ["胡雨苏"]
     * publisher : 中国友谊出版公司
     * pubdate : 2000-9-1
     * rating : {"max":10,"numRaters":9438,"average":"9.1","min":0}
     * tags : [{"count":2416,"name":"小王子"},{"count":1914,"name":"童话"},{"count":1185,"name":"圣埃克苏佩里"},{"count":863,"name":"法国"},{"count":647,"name":"经典"},{"count":597,"name":"外国文学"},{"count":495,"name":"感动"},{"count":368,"name":"寓言"}]
     * binding : 平装
     * price : 19.8
     * series : {"id":"2065","title":"新史学&多元对话系列"}
     * pages : 111
     * author_intro :
     * summary :
     * catalog : 序言：法兰西玫瑰 小王子 圣埃克苏佩里年表
     * ebook_url : https://read.douban.com/ebook/1234567/(该字段只在存在对应电子书时提供)
     * ebook_price : 12.00
     */

    private String id;
    private String isbn10;
    private String isbn13;
    private String title;
    private String origin_title;
    private String alt_title;
    private String subtitle;
    private String url;
    private String alt;
    private String image;
    private ImagesEntity images;
    private String publisher;
    private String pubdate;
    private BookRating rating;
    private String binding;
    private String price;
    private BookSeries series;
    private String pages;
    private String author_intro;
    private String summary;
    private String catalog;
    private String ebook_url;
    private String ebook_price;
    private List<String> author;
    private List<String> translator;
    private List<BookTags> tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ImagesEntity getImages() {
        return images;
    }

    public void setImages(ImagesEntity images) {
        this.images = images;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public BookRating getRating() {
        return rating;
    }

    public void setRating(BookRating rating) {
        this.rating = rating;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public BookSeries getSeries() {
        return series;
    }

    public void setSeries(BookSeries series) {
        this.series = series;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getEbook_url() {
        return ebook_url;
    }

    public void setEbook_url(String ebook_url) {
        this.ebook_url = ebook_url;
    }

    public String getEbook_price() {
        return ebook_price;
    }

    public void setEbook_price(String ebook_price) {
        this.ebook_price = ebook_price;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public List<String> getTranslator() {
        return translator;
    }

    public void setTranslator(List<String> translator) {
        this.translator = translator;
    }

    public List<BookTags> getTags() {
        return tags;
    }

    public void setTags(List<BookTags> tags) {
        this.tags = tags;
    }


    public static class BookSeries implements Serializable{
        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
