package com.framework.proxy.helper;

import android.view.MenuItem;

import com.framework.base.BaseAsyncActivity;
import com.framework.base.BaseControl;
import com.framework.proxy.handler.AsyncActivityHandler;

public class ActivityHelper<T extends BaseControl, R extends BaseAsyncActivity> extends BaseHelper<T, R> {

    private boolean isPause;

    public ActivityHelper(R refrenceObj) {
        super(refrenceObj, new AsyncActivityHandler(refrenceObj));
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onResume() {
        isPause = false;
        super.onResume();
    }

    public void onPause() {
        isPause = true;
        super.onPause();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mReferenceObj.finish();
                return true;
        }
        return false;
    }

    public boolean isPause() {
        return isPause;
    }

}
