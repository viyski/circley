package com.gm.circley.ui.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gm.circley.R;
import com.gm.circley.adapter.ExploreRecyclerAdapter;
import com.gm.circley.base.BaseFragment;
import com.gm.circley.interf.RecyclerItemClickListener;
import com.gm.circley.model.ExploreListEntity;
import com.gm.circley.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * Created by lgm on 2016/7/16.
 */
public class ExploreFragment extends BaseFragment{

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private int[] imgs;
    private String[] titles;
    private String[] actvitys;
    private List<ExploreListEntity> mData;

    @Override
    protected View initView() {
        return mInflater.inflate(R.layout.fragment_explore,null);
    }

    @Override
    protected void initData() {
        imgs = new int[]{R.mipmap.ic_action_twitter,R.mipmap.ic_action_picture,R.mipmap.ic_action_movie,
                R.mipmap.ic_action_music, R.mipmap.ic_action_book,R.mipmap.ic_action_planet};
        titles = Utils.getStrArray(mContext,R.array.explore_list_title);

        actvitys = new String[]{"WeiboExploreActivity","PhotoExploreActivity","MovieExploreActivity","MusicExploreActivity",
                "BookExploreActivity","ScienceExploreActivity"};

        int size = imgs.length;
        mData = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ExploreListEntity exploreListEntity = new ExploreListEntity();
            exploreListEntity.setTitle(titles[i]);
            exploreListEntity.setResId(imgs[i]);
            exploreListEntity.setActivity(actvitys[i]);
            mData.add(exploreListEntity);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new ExploreRecyclerAdapter(mContext,mData));
        recyclerView.addOnItemTouchListener(mRecyclerItemClickListener);
    }

    private RecyclerItemClickListener mRecyclerItemClickListener =
            new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ExploreListEntity exploreListEntity = mData.get(position);
                    String activity = exploreListEntity.getActivity();
                    startActivity(activity);
                }
            });

    private void startActivity(String activity){
        activity = mContext.getPackageName() + ".ui.activity.explore." + activity;
        Intent intent = new Intent();
        ComponentName component = new ComponentName(mContext,activity);
        intent.setComponent(component);
        startActivity(intent);
        mActivity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
