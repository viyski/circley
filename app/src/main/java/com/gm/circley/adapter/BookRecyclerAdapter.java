package com.gm.circley.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gm.circley.R;
import com.gm.circley.control.UIHelper;
import com.gm.circley.interf.ConstantsParams;
import com.gm.circley.model.BookEntity;
import com.gm.circley.model.BookRating;
import com.gm.circley.model.BookTags;
import com.gm.circley.model.ImagesEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lgm on 2016/8/28.
 */
public class BookRecyclerAdapter extends BaseRecyclerAdapter<BookEntity> {

    private static final int TYPE_LIST = 0;
    private static final int TYPE_FOOT_VIEW = 1;
    private View footView;
    private int themeType = ConstantsParams.THEME_TYPE_BLUE;

    public BookRecyclerAdapter(Activity activity, List<BookEntity> data) {
        super(activity, data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case TYPE_LIST:
                View view = mInflater.inflate(R.layout.item_book_list, parent, false);
                viewHolder = new ListViewHolder(view);
                break;
            case TYPE_FOOT_VIEW:
                footView = mInflater.inflate(R.layout.recyclerview_footview_layout, parent, false);
                footView.setVisibility(View.GONE);
                viewHolder = new FooterViewHolder(footView);
                break;
            default:
                viewHolder = new ListViewHolder(mInflater.inflate(R.layout.item_book_list, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListViewHolder) {
            final ListViewHolder listHolder = (ListViewHolder) holder;
            final BookEntity entity = mData.get(position);
            final int mPosition = position;

            if (entity != null) {
                String title = entity.getTitle();
                ImagesEntity images = entity.getImages();
                BookRating rating = entity.getRating();
                String publisher = entity.getPublisher();
                String price = entity.getPrice();
                List<String> authors = entity.getAuthor();
                List<BookTags> tags = entity.getTags();
                if (!TextUtils.isEmpty(title))
                    listHolder.tvBookTitle.setText(title);
                if(images != null){
                    String largeImage = images.getLarge();
                    if (!TextUtils.isEmpty(largeImage))
                        mImageManager.loadUrlImage(largeImage,listHolder.ivBook);
                }

                if (rating != null){
                    try {
                        float average = rating.getAverage();
                        String ratingStr = average + " ";
                        if (average == 0.0){
                            ratingStr = "暂无评分!";
                        }
                        listHolder.tvBookRating.setText(ratingStr);
                    } catch (Exception e) {
                        listHolder.tvBookRating.setText("暂无评分!");
                    }
                }

                if (!TextUtils.isEmpty(publisher)){
                    listHolder.tvBookPublisher.setText(publisher);
                }

                if (!TextUtils.isEmpty(price)){
                    listHolder.tvBookPrice.setText(price);
                }

                if (null != authors && !authors.isEmpty()){
                    String authorStr = "";
                    for (String author : authors){
                        authorStr += author + " ";
                    }

                    listHolder.tvBookAuthor.setText(authorStr);
                }

                if (tags != null && !tags.isEmpty()){
                    String tagStr = "";
                    for (BookTags tag : tags){
                        tagStr += tag.getName() + " ";
                    }

                    listHolder.tvBookTag.setText(tagStr);
                }

                listHolder.cvRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UIHelper.gotoBookDetailActivity(mActivity,entity);
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

        @Bind(R.id.iv_book)
        ImageView ivBook;
        @Bind(R.id.tv_book_title)
        TextView tvBookTitle;
        @Bind(R.id.tv_book_author)
        TextView tvBookAuthor;
        @Bind(R.id.tv_book_tag)
        TextView tvBookTag;
        @Bind(R.id.tv_book_publisher)
        TextView tvBookPublisher;
        @Bind(R.id.tv_book_price)
        TextView tvBookPrice;
        @Bind(R.id.tv_book_rating)
        TextView tvBookRating;
        @Bind(R.id.cv_root)
        LinearLayout cvRoot;

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
