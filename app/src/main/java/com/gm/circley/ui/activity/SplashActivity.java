package com.gm.circley.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gm.circley.R;
import com.gm.circley.control.UIHelper;
import com.gm.circley.control.manager.DBManager;
import com.gm.circley.db.DBPhoto;
import com.gm.circley.model.UserEntity;
import com.squareup.picasso.Picasso;

import java.util.Random;

import cn.bmob.v3.BmobUser;

/**
 * Created by lgm on 2016/7/16.
 * </p>
 */
public class SplashActivity extends AppCompatActivity {

    private UserEntity mUserEntity;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                gotoActivity();
            }
        }
    };
    private DBManager dbManager;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature();
        setContentView(getLayoutRes());
        initView();
    }

    private void requestWindowFeature() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private int getLayoutRes() {
        return R.layout.activity_welcome;
    }

    private void initView() {
        View view = findViewById(R.id.ll_root);
        imageView = (ImageView) findViewById(R.id.iv_background);
        final Random random = new Random();
        dbManager = new DBManager(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        view.startAnimation(animation);
        new Thread(){
            @Override
            public void run() {
                int photoListSize = (int) dbManager.getPhotoListSize();
                if (photoListSize > 0) {
                    int i = random.nextInt(photoListSize);
                    final DBPhoto dbPhoto = dbManager.getPhotoById(i + 1);
                    if (dbPhoto != null && !TextUtils.isEmpty(dbPhoto.getImageUrl())) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Picasso.with(SplashActivity.this)
                                        .load(dbPhoto.getImageUrl())
                                        .placeholder(R.color.font_black_6)
                                        .error(R.color.font_black_6)
                                        .into(imageView);
                                handler.sendEmptyMessageDelayed(0, 2000);
                            }
                        });
                    }
                }else{
                    handler.sendEmptyMessageDelayed(0, 2000);
                }
            }
        }.start();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    private void gotoActivity() {
        mUserEntity = BmobUser.getCurrentUser(this,UserEntity.class);
        if (null == mUserEntity)
            UIHelper.gotoLoginActivity(this);
        else
            UIHelper.gotoMainActivity(this);

        finish();
    }
}
