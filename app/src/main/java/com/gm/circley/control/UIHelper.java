package com.gm.circley.control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.gm.circley.interf.ConstantsParams;
import com.gm.circley.ui.activity.LoginActivity;
import com.gm.circley.ui.activity.MainActivity;
import com.gm.circley.ui.activity.WebPageActivity;

import java.io.File;


/**
 * Created by lgm on 2016/7/16.
 * </p>
 */
public class UIHelper {

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


}
