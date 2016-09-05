package com.gm.circley.interf;

/**
 * Created by lgm on 2016/7/13.
 */
public interface ConstantsParams {

    // bmob
    String BMOB_APP_ID = "80370e320d2ae57e70c675fa0d635d32";

    // showapi
    String SHOWAPI_APP_KEY = "7bca822212208bb7f147c7c1150088fb";

    // fir.im信息
    String FIR_IM_ID = "57a9f56c959d6908c5001abc";
    String FIR_IM_API_TOKEN = "9aeac10f25507c5b24b383a2cd15ec66";

    // 新浪微博信息
    String SINA_APP_KEY = "747013853";
    String SINA_APP_SECRET = "990529a1885d79d72ff229302b04fec5";

    String SINA_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    String SINA_SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";

    // weixin
    String WECHAT_APP_ID = "wx22e548df161b55bd";
    String WECHAT_APP_Secret = "59f145464c4725776dbcfea44f0c71ae";

    // qq
    String QQ_APP_ID = "1105604364";
    String QQ_APP_KEY = "QRxwQW6AJ5IMwHnu";


    int TAKE_PICTURE_REQUEST_CODE = 7;
    int CHOOSE_PICTURE_REQUEST_CODE = 23;
    int CROP_CUTTING_REQUEST_CODE = 79;
    int PROFILE_REQUEST_CODE = 29;

    int THEME_TYPE_BLUE = 0x000;
    int THEME_TYPE_RED = 0x001;
    int THEME_TYPE_TEAL = 0x002;
    String PARAM_TARGET_URL = "PARAM_TARGET_URL";
    String PARAM_TITLE = "PARAM_TITLE";
    String PARAM_IMAGE_URL = "PARAM_IMAGE_URL";
    String PARAM_SUMMARY = "PARAM_SUMMARY";
    String PARAM_APPNAME = "PARAM_APPNAME";
    String PARAM_APP_SOURCE = "PARAM_APP_SOURCE";
    String HTTP_GET = "Get";
}
