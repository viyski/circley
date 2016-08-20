package com.gm.circley.control.manager;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gm.circley.R;
import com.gm.circley.widget.GlideCircleTransform;

import java.io.File;

/**
 * Created by lgm on 2016/7/14.
 * <p/>
 * 图片加载工具类
 */
public class ImageManager {

    private Context mContext;
    private static final String ANDROID_RESOURCE = "android.resource://";
    private static final String FORWARD_SLASH = File.separator;

    public ImageManager(Context context) {
        mContext = context;
    }

    public Uri convertResIdToUri(int resId) {
        return Uri.parse(ANDROID_RESOURCE + mContext.getPackageName() + FORWARD_SLASH + resId);
    }

    public void loadUrlImage(String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .placeholder(R.color.color_image_default)
                .error(R.color.color_image_default)
                .crossFade()
                .into(imageView);
    }

    public void loadLocalImage(String path, ImageView imageView) {
        Glide.with(mContext)
                .load("file://" + path)
                .placeholder(R.color.color_image_default)
                .error(R.color.color_image_default)
                .crossFade()
                .into(imageView);
    }

    public void loadResImage(int resId, ImageView imageView) {
        Glide.with(mContext)
                .load(convertResIdToUri(resId))
                .placeholder(R.color.color_image_default)
                .error(R.color.color_image_default)
                .crossFade()
                .into(imageView);
    }

    public void loadCircleUrlImage(String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .placeholder(R.mipmap.ic_user)
                .error(R.mipmap.ic_user)
                .crossFade()
                .transform(new GlideCircleTransform(mContext))
                .into(imageView);
    }

    public void loadCircleResImage(int resId, ImageView imageView) {
        Glide.with(mContext)
                .load(convertResIdToUri(resId))
                .placeholder(R.color.color_image_default)
                .error(R.color.color_image_default)
                .crossFade()
                .transform(new GlideCircleTransform(mContext))
                .into(imageView);
    }

    public void loadCircleLocalImage(String path, ImageView imageView) {
        Glide.with(mContext)
                .load("file://" + path)
                .placeholder(R.color.color_image_default)
                .error(R.color.color_image_default)
                .crossFade()
                .transform(new GlideCircleTransform(mContext))
                .into(imageView);
    }
}
