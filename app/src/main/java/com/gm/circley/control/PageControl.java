package com.gm.circley.control;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

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
import com.gm.circley.model.BlogEntity;
import com.gm.circley.model.BookApiEntity;
import com.gm.circley.model.BookEntity;
import com.gm.circley.model.MovieApiEntity;
import com.gm.circley.model.MovieDetail;
import com.gm.circley.model.MovieEntity;
import com.gm.circley.model.MusicApiEntity;
import com.gm.circley.model.MusicEntity;
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

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
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

    @AsyncAtomMethod
    public void getBlogListData(Context context) {
        if (context == null) return;
        pageSize = PAGE_SIZE_LIMIT;
        BmobQuery<BlogEntity> list = new BmobQuery<>();
        list.setLimit(PAGE_SIZE_LIMIT);
        list.order("-createdAt");
        list.include("userEntity");
        list.findObjects(context, new FindListener<BlogEntity>() {
            @Override
            public void onSuccess(List<BlogEntity> entities) {
                mModel.put(1, entities);
                if (entities.size() == 0) {
                    sendMessage("getDataEmpty");
                } else if (entities.size() == PAGE_SIZE_LIMIT) {
                    sendMessage("getDataAdequate");
                } else {
                    sendMessage("getDataInadequate");
                }
            }

            @Override
            public void onError(int i, String s) {
                dealWithExceptionMessage(s);
                sendMessage("getDataFailed");
            }
        });
    }

    @AsyncAtomMethod
    public void getBlogListDataMore(Context context) {
        if (context == null) return;
        if (lastPageSize == pageSize) {
            return ;
        } else {
            lastPageSize = pageSize;
        }
        BmobQuery<BlogEntity> list = new BmobQuery<>();
        list.setSkip(pageSize);
        list.setLimit(PAGE_SIZE_LIMIT);
        list.order("-createdAt");
        list.include("userEntity");
        list.findObjects(context, new FindListener<BlogEntity>() {
            @Override
            public void onSuccess(List<BlogEntity> entities) {
                pageSize += entities.size();
                mModel.put(2, entities);
                if (entities.size() == 0) {
                    sendMessage("getMoreDataEmpty");
                } else if (entities.size() == PAGE_SIZE_LIMIT) {
                    sendMessage("getMoreDataAdequate");
                } else {
                    sendMessage("getMoreDataInadequate");
                }
            }

            @Override
            public void onError(int i, String s) {
                dealWithExceptionMessage(s);
                sendMessage("getMoreDataFailed");
            }
        });
    }

    /**
     * @param context
     */
    @AsyncAtomMethod
    public void getMovieList(Context context,String movie) {
        Map<String, String> params = new HashMap<>();
        if (movie.contains(UrlParams.MOVIE_IN_THEATERS)) {
            params.put("city", "广州");
        }
        RequestCall build = OkHttpProxy.get()
                .url(UrlParams.DOUBAN_API_BASE_URL + movie)
                .params(params).build();
        build.execute(new JsonCallBack<MovieApiEntity>(){

            @Override
            public void onSuccess(MovieApiEntity response) {
                int count = response.getCount();
                List<MovieEntity> subjects = response.getSubjects();
                if (subjects != null) {
                    mModel.put(1, subjects);
                    if (subjects.size() == 0) {
                        sendMessage("getDataEmpty");
                    } else if (count == PAGE_SIZE_LIMIT) {
                        sendMessage("getDataAdequate");
                    } else {
                        sendMessage("getDataInadequate");
                    }
                }
            }

            @Override
            public void onFailure(Call request, Exception e) {

            }
        });
    }

    @AsyncAtomMethod
    public void getMovieMoreList(Context context,String movie,int start){
        Map<String, String> params = new HashMap<>();
        if (movie.contains(UrlParams.MOVIE_IN_THEATERS)) {
            params.put("city", "广州");
        }
        params.put("start",start + "");
        RequestCall build = OkHttpProxy.get()
                .url(UrlParams.DOUBAN_API_BASE_URL + movie)
                .params(params).build();
        build.execute(new JsonCallBack<MovieApiEntity>(){

            @Override
            public void onSuccess(MovieApiEntity response) {
                int count = response.getCount();
                List<MovieEntity> subjects = response.getSubjects();
                if (subjects != null){
                    pageSize += subjects.size();
                    mModel.put(2, subjects);
                    if (subjects.size() == 0) {
                        sendMessage("getMoreDataEmpty");
                    } else if (subjects.size() == PAGE_SIZE_LIMIT) {
                        sendMessage("getMoreDataAdequate");
                    } else {
                        sendMessage("getMoreDataInadequate");
                    }
                }
            }

            @Override
            public void onFailure(Call request, Exception e) {

            }
        });
    }

    public void getMovieDesc(String id) {
        RequestCall build = OkHttpProxy.get()
                .url(UrlParams.DOUBAN_API_BASE_URL + UrlParams.MOVIE_SUBJECT + id)
                .build();
        build.execute(new JsonCallBack<MovieDetail>() {
            @Override
            public void onSuccess(MovieDetail response) {
                if (response != null){
                    String summary = response.getSummary();
                    if (!TextUtils.isEmpty(summary)){
                        mModel.put("movieDesc",summary);
                        sendMessage("setMovieDesc");
                    }
                }
            }

            @Override
            public void onFailure(Call request, Exception e) {
            }
        });
    }

    /**
     * @param context
     */
    @AsyncAtomMethod
    public void getBookList(Context context,String bookTag) {
        Map<String, String> params = new HashMap<>();
        params.put("tag",bookTag);
        RequestCall build = OkHttpProxy.get()
                .url(UrlParams.DOUBAN_API_BASE_URL + UrlParams.BOOK_SEARCH)
                .params(params).build();
        build.execute(new JsonCallBack<BookApiEntity>(){

            @Override
            public void onSuccess(BookApiEntity response) {
                int count = response.getCount();
                List<BookEntity> books = response.getBooks();
                if (books != null) {
                    mModel.put(1, books);
                    if (books.size() == 0) {
                        sendMessage("getDataEmpty");
                    } else if (count == PAGE_SIZE_LIMIT) {
                        sendMessage("getDataAdequate");
                    } else {
                        sendMessage("getDataInadequate");
                    }
                }
            }

            @Override
            public void onFailure(Call request, Exception e) {

            }
        });
    }

    @AsyncAtomMethod
    public void getBookMoreList(Context context,String bookTag,int start){
        Map<String, String> params = new HashMap<>();
        params.put("tag",bookTag);
        params.put("start",start + "");
        RequestCall build = OkHttpProxy.get()
                .url(UrlParams.DOUBAN_API_BASE_URL + UrlParams.BOOK_SEARCH)
                .params(params).build();
        build.execute(new JsonCallBack<BookApiEntity>(){

            @Override
            public void onSuccess(BookApiEntity response) {
                int count = response.getCount();
                List<BookEntity> books = response.getBooks();
                if (books != null){
                    pageSize += books.size();
                    mModel.put(2, books);
                    if (books.size() == 0) {
                        sendMessage("getMoreDataEmpty");
                    } else if (books.size() == PAGE_SIZE_LIMIT) {
                        sendMessage("getMoreDataAdequate");
                    } else {
                        sendMessage("getMoreDataInadequate");
                    }
                }
            }

            @Override
            public void onFailure(Call request, Exception e) {

            }
        });
    }

    /**
     * @param context
     */
    @AsyncAtomMethod
    public void getMusicList(Context context,String bookTag) {
        Map<String, String> params = new HashMap<>();
        params.put("tag",bookTag);
        RequestCall build = OkHttpProxy.get()
                .url(UrlParams.DOUBAN_API_BASE_URL + UrlParams.MUSIC_SEARCH)
                .params(params).build();
        build.execute(new JsonCallBack<MusicApiEntity>(){

            @Override
            public void onSuccess(MusicApiEntity response) {
                int count = response.getCount();
                List<MusicEntity> musics = response.getMusics();
                if (musics != null) {
                    mModel.put(1, musics);
                    if (musics.size() == 0) {
                        sendMessage("getDataEmpty");
                    } else if (count == PAGE_SIZE_LIMIT) {
                        sendMessage("getDataAdequate");
                    } else {
                        sendMessage("getDataInadequate");
                    }
                }
            }

            @Override
            public void onFailure(Call request, Exception e) {

            }
        });
    }

    @AsyncAtomMethod
    public void getMusicMoreList(Context context,String bookTag,int start){
        Map<String, String> params = new HashMap<>();
        params.put("tag",bookTag);
        params.put("start",start + "");
        RequestCall build = OkHttpProxy.get()
                .url(UrlParams.DOUBAN_API_BASE_URL + UrlParams.MUSIC_SEARCH)
                .params(params).build();
        build.execute(new JsonCallBack<MusicApiEntity>(){

            @Override
            public void onSuccess(MusicApiEntity response) {
                int count = response.getCount();
                List<MusicEntity> musics = response.getMusics();
                if (musics != null){
                    pageSize += musics.size();
                    mModel.put(2, musics);
                    if (musics.size() == 0) {
                        sendMessage("getMoreDataEmpty");
                    } else if (musics.size() == PAGE_SIZE_LIMIT) {
                        sendMessage("getMoreDataAdequate");
                    } else {
                        sendMessage("getMoreDataInadequate");
                    }
                }
            }

            @Override
            public void onFailure(Call request, Exception e) {

            }
        });
    }
}
