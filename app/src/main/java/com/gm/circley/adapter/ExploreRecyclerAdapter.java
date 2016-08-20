package com.gm.circley.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gm.circley.R;
import com.gm.circley.model.ExploreListEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lgm on 2016/8/2.
 */
public class ExploreRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<ExploreListEntity> mData;

    public ExploreRecyclerAdapter(Context context, List<ExploreListEntity> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_explore_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder listHolder = (ViewHolder) holder;

        ExploreListEntity exploreListEntity = mData.get(position);
        if (exploreListEntity != null){
            listHolder.ivExploreImg.setImageResource(exploreListEntity.getResId());
            listHolder.tvExploreTitle.setText(exploreListEntity.getTitle());
        }

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_explore_img)
        ImageView ivExploreImg;
        @Bind(R.id.tv_explore_title)
        TextView tvExploreTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
