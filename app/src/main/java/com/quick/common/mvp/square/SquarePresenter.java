package com.quick.common.mvp.square;

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
 * Date: 2019/12/25 0025 16:47
 */
public class SquarePresenter extends BasePresenter<SquareView> {
    public SquarePresenter(LifecycleOwner owner, SquareView mvpView, Activity activity) {
        super(owner, mvpView, activity);
    }

    public void getSquareArticleList(int page){
        RxHttp.get(String.format(HttpConfig.USER_ARTICLE_LIST, page))
                .asResponse(ArticleListBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(s -> {
                    //成功回调
                    mvpView.getSquareArticleListSuccess(s);
                }, (OnError) error -> {
                    //失败回调
                });
    }
}
