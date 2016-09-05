package com.gm.circley.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gm.circley.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lgm on 2016/8/30.
 */
public class MovieImageRecyclerAdapter extends BaseRecyclerAdapter<String> {

    public MovieImageRecyclerAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_avatar_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder listHolder = (ViewHolder) holder;
        String imageUrl = mData.get(position);
        if (!TextUtils.isEmpty(imageUrl)) {
            mImageManager.loadUrlImage(imageUrl,listHolder.ivImage);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.iv_image)
        ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
