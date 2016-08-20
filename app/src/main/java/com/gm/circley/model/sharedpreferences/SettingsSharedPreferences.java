package com.gm.circley.model.sharedpreferences;

import de.devland.esperandro.SharedPreferenceActions;
import de.devland.esperandro.SharedPreferenceMode;
import de.devland.esperandro.annotations.SharedPreferences;

/**
 * Created by lgm on 2016/8/8.
 *
 */
@SharedPreferences(name = "settings",mode = SharedPreferenceMode.PRIVATE)
public interface SettingsSharedPreferences extends SharedPreferenceActions {

    boolean isReceivePush();
    void isReceivePush(boolean isReceivePush);

    String newBlogUrl();
    void newBlogUrl(String newBlogUrl);

    String newBlogDes();
    void newBlogDes(String newBlogDes);


}
