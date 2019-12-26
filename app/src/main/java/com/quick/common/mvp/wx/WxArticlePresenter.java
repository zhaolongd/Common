package com.quick.common.mvp.wx;

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
 * Date: 2019/12/24 0024 10:40
 */
public class WxArticlePresenter extends BasePresenter<WxArticleView> {
    public WxArticlePresenter(LifecycleOwner owner, WxArticleView mvpView, Activity activity) {
        super(owner, mvpView, activity);
    }

    public void getWxArticleList(int id, int page){
        RxHttp.get(String.format(HttpConfig.WX_ARTICLE_LIST, id, page))
                .asResponse(ArticleListBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(s -> {
                    //成功回调
                    mvpView.getWxArticleListSuccess(s);
                }, (OnError) error -> {
                    //失败回调
                });
    }
}
