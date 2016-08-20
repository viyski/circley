package com.gm.circley.model;

import java.util.List;

/**
 * Created by lgm on 2016/7/28.
 */
public class PageBean {

    private int allNum;
    private int allPages;
    private List<NewsEntity> contentlist;
    private int currentPage;
    private int maxResult;

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public List<NewsEntity> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<NewsEntity> contentlist) {
        this.contentlist = contentlist;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }
}
