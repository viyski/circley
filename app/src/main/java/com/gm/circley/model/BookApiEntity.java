package com.gm.circley.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lgm on 2016/8/30.
 */
public class BookApiEntity implements Serializable {

    /*{
            "start": 0,
            "count": 10,
            "total": 30,
            "books" : [Book, ]
    }*/

    private int start;
    private int count;
    private int total;
    private List<BookEntity> books;

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

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }
}
