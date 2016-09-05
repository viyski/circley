package com.gm.circley.ui.fragment;

import android.view.View;

import com.gm.circley.R;
import com.gm.circley.adapter.BaseRecyclerAdapter;
import com.gm.circley.adapter.BlogRecyclerAdapter;
import com.gm.circley.base.BaseListFragment;
import com.gm.circley.interf.EventType;
import com.gm.circley.model.BlogEntity;
import com.gm.circley.model.event.EventEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by lgm on 2016/7/16.
 */
public class CollectListFragment extends BaseListFragment<BlogEntity>{

    @Override
    protected BaseRecyclerAdapter<BlogEntity> getRecyclerAdapter() {
        return new BlogRecyclerAdapter(mActivity,mData);
    }

    @Override
    protected View initView() {
        isSetThemeType(false);
        EventBus.getDefault().register(this);
        return super.initView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusListener(EventEntity event) {
        switch (event.getType()) {
            case EventType.EVENT_TYPE_UPDATE_BLOG_LIST:
                onRefreshStart();
                break;
        }
    }

    @Override
    protected void onRefreshStart() {
        mControl.getBlogListData(mContext);
    }

    @Override
    protected void onScrollLast() {
        mControl.getBlogListDataMore(mContext);
    }

    @Override
    protected int emptyDataString() {
        return R.string.no_data;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
