package com.gm.circley.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gm.circley.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgm on 2016/8/7.
 */
public class MovieComingFragment extends BaseFragment{

    private RecyclerView recyclerView;

    @Override
    protected View initView() {

        recyclerView = new RecyclerView(mContext);

        return recyclerView;
    }

    @Override
    protected void initData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add(i+"");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //recyclerView.setAdapter(new BaseRecyclerAdapter(data));
    }
}
