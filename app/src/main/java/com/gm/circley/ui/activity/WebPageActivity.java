package com.gm.circley.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.gm.circley.R;
import com.gm.circley.base.BaseActivity;
import com.gm.circley.interf.ConstantsParams;

import java.lang.reflect.InvocationTargetException;

import butterknife.Bind;

public class WebPageActivity extends BaseActivity {

    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.ll_load_fail)
    LinearLayout llLoadFail;
    private WebSettings settings;
    private String mTitle;
    private String url;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_web_page;
    }

    @Override
    protected void initView() {
        int theme = getIntent().getIntExtra("type", 0);
        switch (theme) {
            case ConstantsParams.THEME_TYPE_BLUE:
                setStatusBarTintColor(R.color.colorPrimary);
                mToolbar.setBackgroundResource(R.color.colorPrimary);
                break;
            case ConstantsParams.THEME_TYPE_RED:
                setStatusBarTintColor(R.color.crimson);
                mToolbar.setBackgroundResource(R.color.crimson);
            case ConstantsParams.THEME_TYPE_TEAL:
                setStatusBarTintColor(R.color.teal);
                mToolbar.setBackgroundResource(R.color.teal);
                break;
        }
    }

    @Override
    protected void initData() {
        final Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        url = intent.getStringExtra("url");
        if (TextUtils.isEmpty(mTitle))
            showHomeBack(true,"");
        else
            showHomeBack(true,mTitle);

        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);

        // >= 19(SDK4.4)启动硬件加速，否则启动软件加速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            settings.setLoadsImagesAutomatically(false);
        }

        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setSaveFormData(true);
        settings.setSupportMultipleWindows(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        //webView.addJavascriptInterface(this,"");
        webView.setHorizontalScrollbarOverlay(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER); // 取消WebView中滚动或拖动到顶部、底部时的阴影
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); // 取消滚动条白边效果
        webView.requestFocus();

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  //接受所有证书
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                webView.setLayerType(View.LAYER_TYPE_NONE,null);
                progressBar.setVisibility(View.GONE);
                if (!settings.getLoadsImagesAutomatically()){
                    settings.setLoadsImagesAutomatically(true);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                progressBar.setVisibility(View.GONE);
                view.loadUrl("file:///android_asset/404.html");
                llLoadFail.setVisibility(View.VISIBLE);
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progressBar.getProgress() >= 100){
                    progressBar.setVisibility(View.GONE);
                }else{
                    if (progressBar.getVisibility() == View.GONE){
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }


            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (TextUtils.isEmpty(mTitle)) {
                    if (title.contains(".")) {
                        title = title.substring(0, title.indexOf("."));
                    } else if (title.contains("|")) {
                        title = title.substring(0, title.indexOf("|"));
                    } else if (title.contains("_")) {
                        title = title.substring(0, title.indexOf("_"));
                    } else if (title.contains("-")) {
                        title = title.substring(0, title.indexOf("-"));
                    } else {
                        title = "详情";
                    }
                    showHomeBack(true, title);
                }
            }
        });

        webView.setDownloadListener(new WebViewDownloadListener());

        llLoadFail.setOnClickListener(this);
    }

    private class WebViewDownloadListener implements DownloadListener{

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_load_fail){
            v.setVisibility(View.GONE);
            webView.loadUrl(url);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            Class.forName("android.webkit.WebView")
                    .getMethod("onPause", (Class[]) null)
                    .invoke(webView, (Object[]) null);

        } catch(ClassNotFoundException cnfe) {

        } catch(NoSuchMethodException nsme) {

        } catch(InvocationTargetException ite) {

        } catch (IllegalAccessException iae) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (webView != null && webView.canGoBack()){
                webView.goBack();
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}

