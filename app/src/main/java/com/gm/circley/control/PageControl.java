package com.gm.circley.control;

import android.content.Context;
import android.os.Environment;

import com.framework.annotation.AsyncAtomMethod;
import com.framework.base.BaseControl;
import com.framework.okhttp.OkHttpProxy;
import com.framework.okhttp.callback.JsonCallBack;
import com.framework.okhttp.request.RequestCall;
import com.framework.proxy.MessageProxy;
import com.gm.circley.control.manager.DBManager;
import com.gm.circley.db.DBPhoto;
import com.gm.circley.interf.ConstantsParams;
import com.gm.circley.interf.UrlParams;
import com.gm.circley.model.NewsApiEntity;
import com.gm.circley.model.NewsEntity;
import com.gm.circley.model.PageBean;
import com.gm.circley.model.ShowapiResBody;
import com.gm.circley.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by lgm on 2016/7/24.
 */
public class PageControl extends BaseControl {

    private static final int PAGE_SIZE_LIMIT = 20;
    private static final int PAGE_SIZE_LIMIT_10 = 10;
    private int pageSize = PAGE_SIZE_LIMIT;
    private int lastPageSize = 0;

    public PageControl(MessageProxy mMessageCallBack) {
        super(mMessageCallBack);
    }

    @AsyncAtomMethod
    public void getNewsListData(String channel){
        pageSize = PAGE_SIZE_LIMIT;

        Map<String, String> params = new HashMap<>();
        params.put("channelName",channel);
        params.put("page","1");
        RequestCall build = OkHttpProxy.get()
                .url(UrlParams.BASE_URL)
                .addHeader("apikey", ConstantsParams.SHOWAPI_APP_KEY)
                .params(params).build();
        build.execute(new JsonCallBack<NewsApiEntity>() {
            @Override
            public void onSuccess(NewsApiEntity newsApiEntity) {
                ShowapiResBody showapi_res_body = newsApiEntity.getShowapi_res_body();

                if (showapi_res_body != null) {
                    PageBean pagebean = showapi_res_body.getPagebean();
                    if (pagebean != null) {
                    }
                    List<NewsEntity> contentlist = pagebean.getContentlist();
                    if (contentlist != null && contentlist.size() > 0) {
                        mModel.put(1, contentlist);
                        if (contentlist.size() == 0) {
                            sendMessage("getDataEmpty");
                        } else if (contentlist.size() == PAGE_SIZE_LIMIT) {
                            sendMessage("getDataAdequate");
                        } else {
                            sendMessage("getDataInadequate");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call request, Exception e) {
                dealWithExceptionMessage(e.getMessage());
                sendMessage("getDataFailed");
            }
        });
    }

    /**
     * 首页List (More)
     */
    @AsyncAtomMethod
    public void getNewsListDataMore(String channel,int currentPage) {
        if (lastPageSize == pageSize) {
            return ;
        } else {
            lastPageSize = pageSize;
        }

        Map<String,String> params = new HashMap<>();
        params.put("channelName",channel);
        params.put("page",String.valueOf(currentPage));

        RequestCall build = OkHttpProxy.get()
                .url(UrlParams.BASE_URL)
                .addHeader("apikey", ConstantsParams.SHOWAPI_APP_KEY)
                .params(params).build();
        build.execute(new JsonCallBack<NewsApiEntity>() {
            @Override
            public void onSuccess(NewsApiEntity newsApiEntity) {
                ShowapiResBody showapi_res_body = newsApiEntity.getShowapi_res_body();
                if (showapi_res_body != null) {
                    PageBean pagebean = showapi_res_body.getPagebean();
                    if (pagebean != null) {
                        List<NewsEntity> contentlist = pagebean.getContentlist();
                        if (contentlist != null && contentlist.size() > 0) {
                            pageSize += contentlist.size();
                            mModel.put(2, contentlist);
                            if (contentlist.size() == 0) {
                                sendMessage("getMoreDataEmpty");
                            } else if (contentlist.size() == PAGE_SIZE_LIMIT) {
                                sendMessage("getMoreDataAdequate");
                            } else {
                                sendMessage("getMoreDataInadequate");
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call request, Exception e) {
                dealWithExceptionMessage(e.getMessage());
                sendMessage("getMoreDataFailed");
            }
        });
    }

    public boolean savePhotoList(Context context, DBManager dbManager) {
        // 读取photo.json文件
        boolean isSuccess;
        InputStream is = null;
        try {
            is = context.getAssets().open("photo.json");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            String json = new String(buffer);

            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.has("photoList")){
                List<DBPhoto> list = new ArrayList<>();
                JSONArray array = (JSONArray) jsonObject.opt("photoList");
                for (int i = 0; i < array.length(); i++) {
                    DBPhoto dbPhoto = new DBPhoto();
                    JSONObject object = array.optJSONObject(i);
                    String imageUrl = object.optString("imageUrl");
                    dbPhoto.setId(i + 1);
                    dbPhoto.setImageUrl(imageUrl);
                    list.add(dbPhoto);
                }
                if (!list.isEmpty()) {
                    dbManager.insertPhotoList(list);
                }
            }
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }


    public void getPhotoDataFromDB(DBManager dbManager) {
        List<DBPhoto> list = dbManager.getPhotoList();
        if (list != null){
            mModel.put(1,list);
            sendMessage("getDataSuccess");
            if (list.isEmpty()){
                sendMessage("getDataEmpty");
            }
        }else{
            sendMessage("getDataFailed");
        }
    }

    public void getMorePhotoDataFromDB(DBManager dbManager,int offset,int limit){
        List<DBPhoto> list = dbManager.getLimitPhotoList(offset, limit);
        if (list != null){
            mModel.put(2,list);
            sendMessage("getMoreDataAdequate");
            if(list.size() < PAGE_SIZE_LIMIT_10){
                sendMessage("getMoreDataInadequate");
            }
        }else{
            sendMessage("getMoreDataInadequate");
        }
    }

    private final static String ALBUM_PATH
            = Environment.getExternalStorageDirectory() + "/download_photo";

    public void savePhotoToMediaStore(Context context,String url) {

        HttpURLConnection connection = null;
        try {

            File dir = new File(ALBUM_PATH);
            if (!dir.exists()){
                dir.mkdirs();
            }

            URL uri = new URL(url);
            connection = (HttpURLConnection) uri.openConnection();
            connection.setConnectTimeout(5 * 1000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream is = connection.getInputStream();

                String filename = DateUtil.getCurrentMillis() + ".jpg";
                File file = new File(dir, filename);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.flush();

                bos.close();
                is.close();
            }

            sendMessage("savePhotoSuccess");
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage("savePhotoFail");
        }finally {
            assert connection != null;
            connection.disconnect();
        }

    }
}
