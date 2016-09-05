package com.gm.circley.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gm.circley.R;
import com.gm.circley.control.UIHelper;
import com.gm.circley.interf.ConstantsParams;
import com.gm.circley.model.MovieCast;
import com.gm.circley.model.MovieDirector;
import com.gm.circley.model.MovieEntity;
import com.gm.circley.model.ImagesEntity;
import com.gm.circley.model.MovieRating;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lgm on 2016/8/28.
 */
public class MovieRecyclerAdapter extends BaseRecyclerAdapter<MovieEntity> {

    private static final int TYPE_LIST = 0;
    private static final int TYPE_FOOT_VIEW = 1;
    private View footView;
    private int themeType = ConstantsParams.THEME_TYPE_BLUE;

    public MovieRecyclerAdapter(Activity activity, List<MovieEntity> data) {
        super(activity, data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case TYPE_LIST:
                View view = mInflater.inflate(R.layout.item_movie_list, parent, false);
                viewHolder = new ListViewHolder(view);
                break;
            case TYPE_FOOT_VIEW:
                footView = mInflater.inflate(R.layout.recyclerview_footview_layout, parent, false);
                footView.setVisibility(View.GONE);
                viewHolder = new FooterViewHolder(footView);
                break;
            default:
                viewHolder = new ListViewHolder(mInflater.inflate(R.layout.item_movie_list, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListViewHolder) {
            final ListViewHolder listHolder = (ListViewHolder) holder;
            final MovieEntity entity = mData.get(position);
            final int mPosition = position;

            if (entity != null){
                String title = entity.getTitle();
                List<String> genres = entity.getGenres();
                ImagesEntity images = entity.getImages();
                MovieRating rating = entity.getRating();
                List<MovieCast> casts = entity.getCasts();
                List<MovieDirector> directors = entity.getDirectors();
                if(images != null){
                    String largeImage = images.getLarge();
                    if (!TextUtils.isEmpty(largeImage))
                        mImageManager.loadUrlImage(largeImage,listHolder.ivMovie);
                }

                if (rating != null){
                    try {
                        float average = rating.getAverage();
                        String ratingStr = average + "";
                        if (average == 0.0){
                            ratingStr = "暂无评分!";
                        }
                        listHolder.tvMovieRating.setText(ratingStr);
                    } catch (Exception e) {
                        listHolder.tvMovieRating.setText("暂无评分!");
                    }
                }

                if (genres != null && genres.size() > 0){
                    String genreStr = "";
                    for (String genre : genres){
                        genreStr += genre + " ";
                    }
                    listHolder.tvMovieGenres.setText(genreStr);
                }

                if (!TextUtils.isEmpty(title))
                    listHolder.tvMovieTitle.setText(title);

                if (casts != null && casts.size() > 0){
                    String castStr = "";
                    for (MovieCast cast : casts){
                        castStr += cast.getName() + " ";
                    }

                    listHolder.tvMovieCast.setText(castStr);
                }

                if (directors != null && directors.size() > 0){
                    String directorStr = "";
                    for (MovieDirector director : directors){
                        directorStr += director.getName() + " ";
                    }

                    listHolder.tvMovieDirector.setText(directorStr);
                }

                listHolder.cvRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UIHelper.gotoMovieDetailActivity(mActivity,entity);
                    }
                });
            }
        }
    }

    public void setThemeType(int themeType) {
        this.themeType = themeType;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOT_VIEW;
        }
        return TYPE_LIST;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size() + 1;
    }

    public boolean isLoadMoreShown() {
        if (footView == null) return false;
        return footView.isShown();

    }

    public String getLoadMoreViewText() {
        if (footView == null) return "";
        return ((TextView) ButterKnife.findById(footView, R.id.tv_loading_more)).getText().toString();
    }

    public void setLoadMoreViewVisible(int visible) {
        if (footView == null) return;
        footView.setVisibility(visible);
        notifyItemChanged(getItemCount());
    }

    public void setLoadViewVisible(boolean visible) {
        if (footView == null) return;
        footView.setVisibility(View.VISIBLE);
        ButterKnife.findById(footView, R.id.pb_load).setVisibility(visible ? View.VISIBLE : View.GONE);
        notifyItemChanged(getItemCount());
    }

    public void setLoadViewText(String text) {
        if (footView == null) return;
        ((TextView) ButterKnife.findById(footView, R.id.tv_loading_more)).setText(text);
        if(text.contains("没有更多")){
            ButterKnife.findById(footView,R.id.pb_load).setVisibility(View.GONE);
        }else{
            ButterKnife.findById(footView,R.id.pb_load).setVisibility(View.VISIBLE);
        }
        notifyItemChanged(getItemCount());
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.cv_root)
        View cvRoot;
        @Bind(R.id.iv_movie)
        ImageView ivMovie;
        @Bind(R.id.tv_movie_title)
        TextView tvMovieTitle;
        @Bind(R.id.tv_movie_genres)
        TextView tvMovieGenres;
        @Bind(R.id.tv_movie_director)
        TextView tvMovieDirector;
        @Bind(R.id.tv_movie_cast)
        TextView tvMovieCast;
        @Bind(R.id.tv_movie_rating)
        TextView tvMovieRating;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_loading_more)
        TextView tvLoadMore;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
