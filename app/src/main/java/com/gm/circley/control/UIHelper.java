package com.gm.circley.control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.gm.circley.interf.ConstantsParams;
import com.gm.circley.model.BlogEntity;
import com.gm.circley.model.BookEntity;
import com.gm.circley.model.MovieEntity;
import com.gm.circley.model.MusicEntity;
import com.gm.circley.model.UserEntity;
import com.gm.circley.ui.activity.AboutActivity;
import com.gm.circley.ui.activity.DoubanDetailActivity;
import com.gm.circley.ui.activity.FeedBackActivity;
import com.gm.circley.ui.activity.LoginActivity;
import com.gm.circley.ui.activity.MainActivity;
import com.gm.circley.ui.activity.MovieDetailActivity;
import com.gm.circley.ui.activity.WebPageActivity;

import java.io.File;


/**
 * Created by lgm on 2016/7/16.
 * </p>
 */
public class UIHelper {

    public static final int TYPE_BOOK = 1;
    public static final int TYPE_MUSIC = 2;

    /**
     * 拍照
     * @param activity
     * @param takePicturePath
     */
    public static void gotoTakePicture(Activity activity,String takePicturePath){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), takePicturePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        activity.startActivityForResult(intent, ConstantsParams.TAKE_PICTURE_REQUEST_CODE);
    }

    public static void gotoChoosePicture(Activity activity){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(intent, ConstantsParams.CHOOSE_PICTURE_REQUEST_CODE);
    }

    public static void gotoLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    public static void gotoMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public static void gotoWebPageActivity(Context context, String title, String url,int themeType) {
        Intent intent = new Intent(context, WebPageActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("type",themeType);
        context.startActivity(intent);
    }

    public static void gotoDetailPage(Context context, int position) {
//        Intent intent = new Intent(context, BookDetailActivity.class);
//            intent.putExtra("book", book);
//            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
//                    view.findViewById(R.id.iv_book), getString(R.string.transition_book_img));
//            ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
    }


    public static void gotoBlogDetailActivity(Context context, BlogEntity entity) {

    }

    public static void gotoUserInfoActivity(Context context, UserEntity userEntity) {

    }

    public static void gotoMovieDetailActivity(Context context, MovieEntity entity) {
        Intent intent = new Intent(context,MovieDetailActivity.class);
        intent.putExtra("MovieDetail",entity);
        context.startActivity(intent);
    }

    public static void gotoBookDetailActivity(Activity context, BookEntity entity) {
        Intent intent = new Intent(context, DoubanDetailActivity.class);
        intent.putExtra("bookDetail",entity);
        intent.putExtra("type",TYPE_BOOK);
        context.startActivity(intent);
    }

    public static void gotoMusicDetailActivity(Context context, MusicEntity entity) {
        Intent intent = new Intent(context, DoubanDetailActivity.class);
        intent.putExtra("musicDetail",entity);
        intent.putExtra("type",TYPE_MUSIC);
        context.startActivity(intent);
    }

    public static void gotoAboutActivity(Context context) {
        context.startActivity(new Intent(context,AboutActivity.class));
    }

    public static void gotoFeedbackActivity(Context context){
        context.startActivity(new Intent(context,FeedBackActivity.class));
    }

    public static void startPhotoZoom(Activity activity,Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 350);
        intent.putExtra("outputY", 350);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, ConstantsParams.CROP_CUTTING_REQUEST_CODE);
    }
}
