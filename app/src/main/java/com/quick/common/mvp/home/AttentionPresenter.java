package com.quick.common.mvp.home;

import android.app.Activity;

import com.quick.common.app.BaseBean;
import com.quick.common.bean.kaiyan.AttentionCard;
import com.quick.common.bean.kaiyan.AttentionCardBean;
import com.quick.common.config.HttpConfig;
import com.quick.common.mvp.base.BasePresenter;
import com.quick.common.utils.GsonUtils;
import com.quick.core.rxhttp.OnError;
import com.rxjava.rxlife.RxLife;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import rxhttp.wrapper.param.RxHttp;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2020/4/10 0010 14:14
 */
public class AttentionPresenter extends BasePresenter<AttentionView> {
    private String nextPageUrl = "";
    /**
     * 上拉刷新 true or 下拉加载 false
     */
    private boolean isRefresh = true;

    public AttentionPresenter(LifecycleOwner owner, AttentionView mvpView, Activity activity) {
        super(owner, mvpView, activity);
    }

    public void getAttentionData(boolean isRefresh) {
        RxHttp.get(isRefresh ? HttpConfig.KAIYAN_ATTENTION : nextPageUrl)
                .asString()
                .as(RxLife.asOnMain(this))
                .subscribe(s -> {
                    //成功回调

                    mvpView.getAttentionDataSuccess(parseData(s));
                }, (OnError) error -> {
                    //失败回调
                });
    }

    private List<BaseBean> parseData(String data) {
        AttentionCardBean attentionCardBean = GsonUtils.fromLocalJson(data, AttentionCardBean.class);
        ArrayList<BaseBean> viewModels = new ArrayList<>();
        nextPageUrl = attentionCardBean.getNextPageUrl();
        for (int i=0;i<attentionCardBean.getItemList().size();i++){
            AttentionCardBean.ItemListBean itemListBean = attentionCardBean.getItemList().get(i);
            AttentionCard cardViewModel = new AttentionCard();
            cardViewModel.avatarUrl = itemListBean.getData().getHeader().getIcon();
            cardViewModel.issuerName = itemListBean.getData().getHeader().getIssuerName();
            cardViewModel.releaseTime = itemListBean.getData().getHeader().getTime();
            cardViewModel.title = itemListBean.getData().getContent().getData().getTitle();
            cardViewModel.description = itemListBean.getData().getContent().getData().getDescription();
            cardViewModel.coverUrl = itemListBean.getData().getContent().getData().getCover().getFeed();
            cardViewModel.playUrl = itemListBean.getData().getContent().getData().getPlayUrl();
            cardViewModel.collectionCount = itemListBean.getData().getContent().getData().getConsumption().getCollectionCount();
            cardViewModel.replyCount = itemListBean.getData().getContent().getData().getConsumption().getReplyCount();
            cardViewModel.realCollectionCount = itemListBean.getData().getContent().getData().getConsumption().getRealCollectionCount();
            cardViewModel.shareCount = itemListBean.getData().getContent().getData().getConsumption().getShareCount();
            cardViewModel.category = itemListBean.getData().getContent().getData().getCategory();
            cardViewModel.authorDescription = itemListBean.getData().getContent().getData().getAuthor().getDescription();
            cardViewModel.blurredUrl = itemListBean.getData().getContent().getData().getCover().getBlurred();
            cardViewModel.videoId = itemListBean.getData().getContent().getData().getId();
            viewModels.add(cardViewModel);
        }
        return viewModels;
    }
}
