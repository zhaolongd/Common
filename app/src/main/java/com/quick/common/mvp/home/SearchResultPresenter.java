package com.quick.common.mvp.home;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;

import com.quick.common.bean.main.ArticleListBean;
import com.quick.common.config.HttpConfig;
import com.quick.common.mvp.base.BasePresenter;
import com.quick.core.rxhttp.OnError;
import com.rxjava.rxlife.RxLife;

import rxhttp.wrapper.param.RxHttp;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/25 0025 15:53
 */
public class SearchResultPresenter extends BasePresenter<SearchResultView> {
    public SearchResultPresenter(LifecycleOwner owner, SearchResultView mvpView, Activity activity) {
        super(owner, mvpView, activity);
    }

    public void search(int page, String key){
        RxHttp.postForm(String.format(HttpConfig.SEARCH, page))
                .add("k", key)
            .asResponse(ArticleListBean.class)
            .as(RxLife.asOnMain(this))
            .subscribe(s -> {
                //成功回调
                mvpView.searchSuccess(s);
            }, (OnError) error -> {
                //失败回调
            });
    }
}
