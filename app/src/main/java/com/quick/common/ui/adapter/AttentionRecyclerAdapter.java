package com.quick.common.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.quick.common.R;
import com.quick.common.app.BaseBean;
import com.quick.common.bean.kaiyan.AttentionCard;
import com.quick.common.bean.kaiyan.VideoHeaderBean;
import com.quick.common.utils.ImageLoader;
import com.quick.core.utils.DateTimeUtils;
import com.quick.video.views.CoverVideoPlayerView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import org.jetbrains.annotations.NotNull;

/**
 * Created by zhaolong.
 * Description: 开眼视频 关注列表
 * Date: 2020/4/13 0013 10:58
 */
public class AttentionRecyclerAdapter extends BaseQuickAdapter<BaseBean, BaseViewHolder> implements LoadMoreModule {

    public AttentionRecyclerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BaseBean baseBean) {
        if (baseBean == null)
        {
            return;
        }
        CoverVideoPlayerView videoItemPlayer  = baseViewHolder.getView(R.id.video_item_player);
        AttentionCard cardViewModel = (AttentionCard) baseBean;
        GSYVideoOptionBuilder gsyVideoOptionBuilder =
                new GSYVideoOptionBuilder();
        videoItemPlayer.loadCoverImage(cardViewModel.coverUrl, 0);
        videoItemPlayer.rvContent.setOnClickListener(v -> {
            VideoHeaderBean headerBean =
                        new VideoHeaderBean(cardViewModel.title,
                                cardViewModel.category, cardViewModel.description,
                                cardViewModel.collectionCount, cardViewModel.shareCount,
                                cardViewModel.avatarUrl, cardViewModel.issuerName,
                                cardViewModel.authorDescription, cardViewModel.playUrl,
                                cardViewModel.blurredUrl, cardViewModel.videoId);
//                ARouter.getInstance()
//                        .build(RouterActivityPath.Video.PAGER_VIDEO)
//                        .withParcelable("videoInfo", headerBean)
//                        .navigation();
            });
        gsyVideoOptionBuilder.setIsTouchWiget(false)
                .setUrl(cardViewModel.playUrl)
                .setVideoTitle(cardViewModel.title)
                .setCacheWithPlay(false)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag("2")
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(baseViewHolder.getAdapterPosition())
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects)
                    {
                        super.onPrepared(url, objects);
                        if (!videoItemPlayer.isIfCurrentIsFullscreen())
                        {
                            // 静音
                            GSYVideoManager.instance().setNeedMute(true);
                        }
                    }
                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                            super.onQuitFullscreen(url, objects);
                            // 全屏不静音
                            GSYVideoManager.instance().setNeedMute(true);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                        videoItemPlayer.getCurrentPlayer()
                                .getTitleTextView()
                                .setText((String)objects[0]);
                    }
                })
                .build(videoItemPlayer);

        // 增加title
        videoItemPlayer.getTitleTextView().setVisibility(View.GONE);

        // 设置返回键
        videoItemPlayer.getBackButton().setVisibility(View.GONE);

        ImageView ivAvatar = baseViewHolder.getView(R.id.iv_avatar);
        ImageLoader.image(ivAvatar, cardViewModel.getAvatarUrl());
        baseViewHolder.setText(R.id.tv_issuer_name, cardViewModel.getIssuerName());
        baseViewHolder.setText(R.id.tv_title, cardViewModel.getTitle());
        baseViewHolder.setText(R.id.tv_description, cardViewModel.getDescription());
        baseViewHolder.setText(R.id.tv_collection_count, cardViewModel.getCollectionCount() + "");
        baseViewHolder.setText(R.id.tv_reply_count, cardViewModel.getReplyCount() + "");
        baseViewHolder.setText(R.id.iv_share, cardViewModel.getShareCount() + "");

        baseViewHolder.setText(R.id.tv_release_time , DateTimeUtils.getDate(String.valueOf(cardViewModel.releaseTime), "HH:mm"));
    }
}
