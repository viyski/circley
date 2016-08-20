package com.gm.circley.model;

/**
 * Created by lgm on 2016/7/28.
 */
public class ShowapiResBody {
    private PageBean pagebean;
    private int ret_code;

    public PageBean getPagebean() {
        return pagebean;
    }

    public void setPagebean(PageBean pagebean) {
        this.pagebean = pagebean;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }
}