package com.gm.circley.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.gm.circley.R;
import com.gm.circley.adapter.MovieImageRecyclerAdapter;
import com.gm.circley.base.BaseActivity;
import com.gm.circley.control.PageControl;
import com.gm.circley.model.MovieAvatar;
import com.gm.circley.model.MovieCast;
import com.gm.circley.model.MovieDirector;
import com.gm.circley.model.MovieEntity;
import com.gm.circley.model.ImagesEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by lgm on 2016/8/30.
 */
public class MovieDetailActivity extends BaseActivity<PageControl> {

    @Bind(R.id.iv_header_movie)
    ImageView ivHeaderMovie;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_movie_desc)
    TextView tvMovieDesc;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private MovieEntity entity;

    private List<String> mData;
    private MovieImageRecyclerAdapter mAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void initView() {
        mData = new ArrayList<>();
        entity = (MovieEntity) getIntent().getSerializableExtra("MovieDetail");
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (mAdapter == null){
            mAdapter = new MovieImageRecyclerAdapter(mContext, mData);
            recyclerView.setAdapter(mAdapter);
        }else{
            recyclerView.setAdapter(mAdapter);
        }

    }

    @Override
    protected void initData() {
        if (entity != null){
            String title = entity.getTitle();
            toolbar.setTitle(title);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

            ImagesEntity images = entity.getImages();
            assert images != null;
            String largeImage = images.getLarge();
            mImageManager.loadUrlImage(largeImage,ivHeaderMovie);

            List<MovieCast> casts = entity.getCasts();
            List<MovieDirector> directors = entity.getDirectors();
            String id = entity.getId();
            if (!TextUtils.isEmpty(id)){
                mControl.getMovieDesc(id);
            }

            if (casts != null && !casts.isEmpty()){
                for (MovieCast cast : casts){
                    MovieAvatar avatars = cast.getAvatars();
                    String largeAvatar = avatars.getLarge();
                    if (!TextUtils.isEmpty(largeAvatar))
                        mData.add(largeAvatar);
                }
            }

            if (directors != null && !directors.isEmpty()){
                for (MovieDirector director : directors){
                    MovieAvatar avatars = director.getAvatars();
                    String largeAvatar = avatars.getLarge();
                    if (!TextUtils.isEmpty(largeAvatar))
                        mData.add(largeAvatar);
                }
            }
        }

        mAdapter.notifyDataSetChanged();
    }

    public void setMovieDesc(){
        String movieDesc = mModel.get("movieDesc");
        if (!TextUtils.isEmpty(movieDesc)){
            tvMovieDesc.setText(movieDesc);
        }
    }
}
