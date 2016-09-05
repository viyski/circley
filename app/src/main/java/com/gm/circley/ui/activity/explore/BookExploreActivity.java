package com.gm.circley.ui.activity.explore;

import com.gm.circley.R;
import com.gm.circley.adapter.BaseRecyclerAdapter;
import com.gm.circley.adapter.BookRecyclerAdapter;
import com.gm.circley.base.BaseListActivity;
import com.gm.circley.model.BookEntity;

public class BookExploreActivity extends BaseListActivity<BookEntity> {

    @Override
    protected void initView() {
        super.initView();
        showHomeBack(true, "书籍推荐");
        setStatusBarTintColor(R.color.crimson);
    }

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        return new BookRecyclerAdapter(this, mData);
    }

    @Override
    protected void onRefreshStart() {
        mControl.getBookList(mContext,"经典文学");
    }

    @Override
    protected void onScrollLast() {
        mControl.getBookMoreList(mContext,"经典文学",mData.size() + 1);
    }

    @Override
    protected int emptyDataString() {
        return R.string.str_no_data;
    }
}
