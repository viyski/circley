package com.gm.circley.model;

/**
 * Created by lgm on 2016/7/24.
 */
public class CommentCountInfoEntity {
    private int qreply;
    private int total;
    private int show;
    private int comment_status;
    private int praise;
    private int dispraise;

    public int getQreply() {
        return qreply;
    }

    public void setQreply(int qreply) {
        this.qreply = qreply;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public int getComment_status() {
        return comment_status;
    }

    public void setComment_status(int comment_status) {
        this.comment_status = comment_status;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public int getDispraise() {
        return dispraise;
    }

    public void setDispraise(int dispraise) {
        this.dispraise = dispraise;
    }
}