package com.gm.circley.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.framework.dialog.ToastTip;
import com.gm.circley.R;
import com.gm.circley.control.UIHelper;
import com.gm.circley.interf.ConstantsParams;
import com.gm.circley.model.BlogEntity;
import com.gm.circley.model.UserEntity;
import com.gm.circley.util.DateUtil;
import com.gm.circley.util.NetUtil;
import com.gm.circley.util.ShareUtil;
import com.gm.circley.widget.groupImageView.GroupImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by lgm on 2016/8/10.
 *
 */
public class BlogRecyclerAdapter extends BaseRecyclerAdapter<BlogEntity> {

    private UserEntity userEntity;
    private int type = HANDLE_NORMAL;

    public static final int HANDLE_NORMAL = 0;
    public static final int HANDLE_CANCEL_FAVORITE = 1;

    private static final int TYPE_LIST = 0;
    private static final int TYPE_FOOT_VIEW = 1;

    private View footView;

    public BlogRecyclerAdapter(Activity activity, List<BlogEntity> data) {
        super(activity, data);
        userEntity = BmobUser.getCurrentUser(activity, UserEntity.class);
    }

    public BlogRecyclerAdapter(Context context, List<BlogEntity> data) {
        super(context, data);
        userEntity = BmobUser.getCurrentUser(context, UserEntity.class);
    }

    public void setHandleType(int type) {
        this.type = type;
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOT_VIEW;
        } else {
            return TYPE_LIST;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case TYPE_LIST:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect_list, parent, false);
                viewHolder = new ListViewHolder(view);
                break;
            case TYPE_FOOT_VIEW:
                footView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_footview_layout, parent, false);
                footView.setVisibility(View.GONE);
                viewHolder = new FootViewHolder(footView);
                break;
            default:
                viewHolder = new ListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect_list, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListViewHolder) {
            final ListViewHolder listViewHolder = (ListViewHolder) holder;
            final BlogEntity entity = mData.get(position);
            final UserEntity userEntity = entity.getUserEntity();
            final int mPosition = position;

            if (userEntity != null && !TextUtils.isEmpty(userEntity.getUserAvatar())) {
                mImageManager.loadCircleUrlImage(userEntity.getUserAvatar(), listViewHolder.ivUserAvatar);
            } else {
                mImageManager.loadCircleResImage(R.mipmap.ic_user, listViewHolder.ivUserAvatar);
            }

            if (userEntity != null && !TextUtils.isEmpty(userEntity.getNickName())) {
                listViewHolder.tvNickName.setText(userEntity.getNickName());
            } else {
                listViewHolder.tvNickName.setText("NULL");
            }

            listViewHolder.tvDescribe.setText(entity.getDescribe());

            if (entity.getCreateTime() > 0) {
                listViewHolder.tvTime.setVisibility(View.VISIBLE);
                listViewHolder.tvTime.setText(DateUtil.getDateStr(mActivity, entity.getCreateTime()));
            } else {
                listViewHolder.tvTime.setVisibility(View.GONE);
            }

            if (entity.getImageList() != null && entity.getImageList().size() > 0) {
                listViewHolder.givImageGroup.setVisibility(View.VISIBLE);
                listViewHolder.givImageGroup.setPics(mActivity,mPosition,entity.getImageList());
            } else {
                listViewHolder.givImageGroup.setVisibility(View.GONE);
            }

            listViewHolder.rlRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetUtil.isLinkAvailable(entity.getWebsite())) {
                        UIHelper.gotoWebPageActivity(mActivity,"",entity.getWebsite(), ConstantsParams.THEME_TYPE_TEAL);
                    }
                }
            });

            listViewHolder.rlRootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopMenu(v, mPosition);
                    return false;
                }
            });

            listViewHolder.ivUserAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (entity.getUserEntity() != null) {
                        UIHelper.gotoUserInfoActivity(mActivity, entity.getUserEntity());
                    }
                }
            });
        }
    }

    public void setLoadMoreViewText(String text) {
        if (footView == null) return;
        ((TextView) ButterKnife.findById(footView, R.id.tv_loading_more)).setText(text);
        notifyItemChanged(getItemCount());
    }

    public void setLoadMoreViewVisibility(int visibility) {
        if (footView == null) return;
        footView.setVisibility(visibility);
        notifyItemChanged(getItemCount());
    }

    public boolean isLoadMoreShown() {
        if (footView == null) return false;
        return footView.isShown();
    }

    public String getLoadMoreViewText() {
        if (footView == null) return "";
        return ((TextView) ButterKnife.findById(footView, R.id.tv_loading_more)).getText().toString();
    }

    /**
     * 弹出菜单
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void showPopMenu(View ancho, final int position) {
        userEntity = BmobUser.getCurrentUser(mActivity, UserEntity.class);
        final BlogEntity entity = mData.get(position);
        List<String> favoriteList = userEntity.getFavoriteList();

        PopupMenu popupMenu = new PopupMenu(mActivity, ancho);
        popupMenu.getMenuInflater().inflate(R.menu.item_pop_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.pop_favorite:
                        if (item.getTitle().equals("取消收藏")) {
                            cancelFavoriteBlog(entity.getObjectId(), position);
                        } else {
                            handleFavoriteBingo(entity.getObjectId());
                        }
                        userEntity = BmobUser.getCurrentUser(mActivity, UserEntity.class);
                        return true;
                    case R.id.pop_share:
                        ShareUtil.share(mActivity, entity.getDescribe() + entity.getWebsite() + "\n[来自" + mActivity.getString(R.string.app_name) + "的分享，下载地址：http://fir.im/circley]");
                        return true;
                    case R.id.pop_delete:
                        new MaterialDialog.Builder(mActivity)
                                .content("确认删除么？")
                                .contentColor(mActivity.getResources().getColor(R.color.font_black_3))
                                .positiveText(R.string.ok)
                                .negativeText(R.string.cancel)
                                .negativeColor(mActivity.getResources().getColor(R.color.font_black_3))
                                .callback(new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                        deleteCollectBlog(position);
                                    }

                                    @Override
                                    public void onNegative(MaterialDialog dialog) {
                                    }
                                })
                                .show();
                        break;
                }
                return false;
            }
        });
        if (type == HANDLE_CANCEL_FAVORITE || (favoriteList != null && favoriteList.indexOf(entity.getObjectId()) >= 0)) {
            MenuItem menuItem = popupMenu.getMenu().findItem(R.id.pop_favorite);
            menuItem.setTitle("取消收藏");
        }
        MenuItem menuItem = popupMenu.getMenu().findItem(R.id.pop_delete);
        if (entity.getUserId().equals(userEntity.getObjectId())) {
            menuItem.setVisible(true);
        } else {
            menuItem.setVisible(false);
        }

        popupMenu.setGravity(Gravity.CENTER);
        //使用反射。强制显示菜单图标
        try {
            Field field = popupMenu.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            MenuPopupHelper mHelper = (MenuPopupHelper) field.get(popupMenu);
            mHelper.setForceShowIcon(true);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        popupMenu.show();
    }

    /**
     * 收藏
     */
    private void handleFavoriteBingo(String bingoId) {
        List<String> favoriteList = userEntity.getFavoriteList();
        if (favoriteList == null) {
            favoriteList = new ArrayList<>();
        }
        if (favoriteList.indexOf(bingoId) >= 0) {
            ToastTip.show("您已收藏过了");
            return;
        }
        favoriteList.add(bingoId);
        userEntity.setFavoriteList(favoriteList);
        userEntity.update(mActivity, userEntity.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                ToastTip.show("收藏成功");
            }

            @Override
            public void onFailure(int i, String s) {
                ToastTip.show("收藏失败");
            }
        });
    }

    /**
     * 取消收藏
     */
    private void cancelFavoriteBlog(String bingoId, final int position) {
        List<String> favoriteList = userEntity.getFavoriteList();
        if (favoriteList == null) {
            favoriteList = new ArrayList<>();
        }
        if (favoriteList.indexOf(bingoId) < 0) {
            ToastTip.show("您已取消收藏了");
            return;
        }
        favoriteList.remove(bingoId);
        userEntity.setFavoriteList(favoriteList);
        userEntity.update(mActivity, userEntity.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                ToastTip.show("取消成功");
                if (type == HANDLE_CANCEL_FAVORITE) {
                    mData.remove(position);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int i, String s) {
                ToastTip.show("取消失败");
            }
        });
    }

    private void deleteCollectBlog(final int position) {
        BlogEntity entity = mData.get(position);
        entity.delete(mActivity, new DeleteListener() {
            @Override
            public void onSuccess() {
                ToastTip.show("删除成功");
                mData.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastTip.show("删除失败");
            }
        });
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_avatar)
        ImageView ivUserAvatar;
        @Bind(R.id.tv_nick_name)
        TextView tvNickName;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.rl_user_info)
        RelativeLayout rlUserInfo;
//        @Bind(R.id.iv_item_more)
//        ImageView ivItemMore;
        @Bind(R.id.tv_describe)
        TextView tvDescribe;
        @Bind(R.id.giv_image_group)
        GroupImageView givImageGroup;
        @Bind(R.id.rl_root_view)
        RelativeLayout rlRootView;

        public ListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_loading_more)
        TextView tvLoadingMore;

        public FootViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
