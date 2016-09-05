package com.gm.circley.ui.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.framework.dialog.ToastTip;
import com.gm.circley.R;
import com.gm.circley.adapter.SettingRecyclerAdapter;
import com.gm.circley.base.BaseActivity;
import com.gm.circley.control.UIHelper;
import com.gm.circley.interf.RecyclerItemClickListener;
import com.gm.circley.model.SettingListEntity;
import com.gm.circley.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by lgm on 2016/8/2.
 */
public class SettingActivity extends BaseActivity {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.btn_logout)
    Button btnLogout;
    private List<SettingListEntity> mData;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        showHomeBack(true, "设置");
        setStatusBarTintColor(R.color.darkpurple);
        mToolbar.setBackgroundResource(R.color.darkpurple);

        mData = new ArrayList<>();
        String[] titles = Utils.getStrArray(SettingActivity.this,R.array.setting_list_title);
        String[] activitys = {"ChangeTheme","FeedbackActivity","AboutActivity","CheckUpdate"};
        for (int i = 0; i < titles.length; i++) {
            SettingListEntity settingListEntity = new SettingListEntity();
            settingListEntity.setTitle(titles[i]);
            settingListEntity.setActivity(activitys[i]);
            mData.add(settingListEntity);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
        SettingRecyclerAdapter adapter = new SettingRecyclerAdapter(SettingActivity.this,mData);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(SettingActivity.this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        switch (position){
                            case 0:
                                break;
                            case 1:
                                UIHelper.gotoFeedbackActivity(SettingActivity.this);
                                break;
                            case 2:
                                UIHelper.gotoAboutActivity(SettingActivity.this);
                                break;
                            case 3:
                                ToastTip.show("已是最新版本!");
                                break;
                        }
                    }
                }));
    }


    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_logout)
    public void onClick() {

    }
}
