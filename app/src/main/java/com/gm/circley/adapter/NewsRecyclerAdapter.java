package com.gm.circley.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gm.circley.R;
import com.gm.circley.control.UIHelper;
import com.gm.circley.interf.ConstantsParams;
import com.gm.circley.model.NewsEntity;
import com.gm.circley.model.Pic;
import com.gm.circley.util.DateUtil;
import com.gm.circley.widget.groupImageView.GroupImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lgm on 2016/7/18.
 *
 */
public class NewsRecyclerAdapter extends BaseRecyclerAdapter<NewsEntity> {

    private static final int TYPE_LIST = 0;
    private static final int TYPE_FOOT_VIEW = 1;
    private View footView;
    private int themeType = ConstantsParams.THEME_TYPE_BLUE;

    public NewsRecyclerAdapter(Activity activity, List<NewsEntity> data) {
        super(activity,data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case TYPE_LIST:
                View view = mInflater.inflate(R.layout.item_recycler_list, parent, false);
                viewHolder = new ListViewHolder(view);
                break;
            case TYPE_FOOT_VIEW:
                footView = mInflater.inflate(R.layout.recyclerview_footview_layout, parent, false);
                footView.setVisibility(View.GONE);
                viewHolder = new FooterViewHolder(footView);
                break;
            default:
                viewHolder = new ListViewHolder(mInflater.inflate(R.layout.item_recycler_list, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListViewHolder) {
            final ListViewHolder listHolder = (ListViewHolder) holder;
            final NewsEntity entity = mData.get(position);
            final int mPosition = position;

            String title = entity.getTitle();
            String source = entity.getSource();
            String link = entity.getLink();
            String nid = entity.getNid();
            String pubDate = entity.getPubDate();

            String desc = entity.getDesc();
            List<Pic> imageurls = entity.getImageurls();

            List<String> picList = entity.getPicList(imageurls);

            if (title != null && !title.equals("")) {
                listHolder.tvTitle.setText(title);
            } else if (desc != null && !desc.equals("")) {
                if (desc.length() >= 2) {
                    desc = desc.substring(2);
                }
                listHolder.tvTitle.setText(desc);
            }

            if (pubDate != null)
                listHolder.tvPublishDate.setText(DateUtil.getDate(mActivity, pubDate));

            if (source != null)
                listHolder.tvPublisher.setText(source);
            else
                listHolder.tvPublisher.setText("未知");

            if (picList != null && picList.size() > 0) {
                listHolder.givImageGroup.setVisibility(View.VISIBLE);
                listHolder.givImageGroup.setPics(mActivity, mPosition, picList);
            } else {
                listHolder.givImageGroup.setVisibility(View.GONE);
            }

            listHolder.rlRootView.setTag(entity);

            listHolder.rlRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsEntity newsEntity = (NewsEntity) v.getTag();
                    if (newsEntity != null) {
                        String clickTitle = newsEntity.getTitle();
                        String clickSource = newsEntity.getSource();
                        String clickLink = newsEntity.getLink();

                        if (TextUtils.isEmpty(clickTitle) && TextUtils.isEmpty(clickSource))
                            return;
                        if (!TextUtils.isEmpty(clickLink))
                            UIHelper.gotoWebPageActivity(mActivity, clickTitle, clickLink, themeType);
                    }
                }
            });
        }
    }

    public void setThemeType(int themeType){
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
        ButterKnife.findById(footView,R.id.pb_load).setVisibility(visible ? View.VISIBLE : View.GONE);
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
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.giv_image_group)
        GroupImageView givImageGroup;
        @Bind(R.id.tv_publish_date)
        TextView tvPublishDate;
        @Bind(R.id.tv_publisher)
        TextView tvPublisher;
        @Bind(R.id.rl_root_view)
        RelativeLayout rlRootView;

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
