package com.gm.circley.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by lgm on 2016/7/25.
 */
public class UploadService extends IntentService {

    public UploadService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    public void uploadExceptionInfo(String result) {

    }
}
