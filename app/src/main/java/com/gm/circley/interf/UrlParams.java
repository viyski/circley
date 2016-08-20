package com.gm.circley.interf;

/**
 * Created by lgm on 2016/7/16.
 */
public interface UrlParams {

    String BASE_URL = " http://apis.baidu.com/showapi_open_bus/channel_news/search_news";

    // 国内焦点、国际焦点、军事焦点、财经焦点、互联网焦点、房产焦点、汽车焦点、体育焦点、娱乐焦点、游戏焦点
    String CHANNEL_NEWS_TOP = "国内最新";
    String CHANNEL_NEWS_RECOMMEND = "推荐";
    String CHANNEL_NEWS_AUTO = "汽车焦点";
    String CHANNEL_NEWS_ENT = "娱乐";
    String CHANNEL_NEWS_SPORTS = "体育焦点";
    String CHANNEL_NEWS_FINANCE = "财经焦点";
    String CHANNEL_NEWS_TECH = "科技";
    String CHANNEL_NEWS_TRAVEL = "旅游";
    String CHANNEL_NEWS_FUNNY = "搞笑";
    String CHANNEL_NEWS_WEIBO = "博客";
    String CHANNEL_NEWS_KNOWNLEDGE = "科普最新";

    String URL_SINA_USER_INFO = "https://api.weibo.com/2/users/show.json";
    String PHOTO_REQUEST_URL = "http://192.168.1.101:8080/image/photo.json";
}
