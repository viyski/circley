package com.gm.circley.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gm.circley.R;
import com.gm.circley.control.manager.ImageManager;
import com.gm.circley.db.DBPhoto;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lgm on 2016/8/13.
 */
public class ImageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<DBPhoto> mData;
    private ImageManager mImageManager;

    public ImageRecyclerAdapter(Context context, List<DBPhoto> data) {
        mContext = context;
        mData = data;
        mImageManager = new ImageManager(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_staggered_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder rHolder, int position) {
        ViewHolder holder = (ViewHolder) rHolder;
        DBPhoto dbPhoto = mData.get(position);
        final int mPosition = position;
        if (dbPhoto != null) {
            if (!TextUtils.isEmpty(dbPhoto.getImageUrl())) {
                Picasso.with(mContext).load(dbPhoto.getImageUrl()).into(holder.ivImage);
//                mImageManager.loadUrlImage(dbPhoto.getImageUrl(), holder.ivImage);

                holder.rlImageRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.anim_rotate);
                        v.startAnimation(anim);
                    }
                });

            }
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.iv_image)
        ImageView ivImage;
        @Bind(R.id.rl_image_root)
        RelativeLayout rlImageRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
