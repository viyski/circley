package com.gm.circley.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgm on 2016/7/24.
 */
public class NewsEntity {
    private String channelId;
    private String channelName;
    private String content;
    private String desc;
    private String html;
    private String link;
    private String nid;
    private String pubDate;
    private int sentiment_display;
    private String source;
    private String title;
    private List<Pic> imageurls;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public int getSentiment_display() {
        return sentiment_display;
    }

    public void setSentiment_display(int sentiment_display) {
        this.sentiment_display = sentiment_display;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Pic> getImageurls() {
        return imageurls;
    }

    public void setImageurls(List<Pic> imageurls) {
        this.imageurls = imageurls;
    }

    public List<String> getPicList(List<Pic> imageurls) {
        List<String> picList = new ArrayList<>();
        picList.clear();
        for (int i = 0; i < imageurls.size(); i++) {
            if (i >= 9)
                break;
            picList.add(imageurls.get(i).getUrl());
        }
        return picList;
    }
}

