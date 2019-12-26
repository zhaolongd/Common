package com.quick.common.mvp.home;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import com.quick.common.bean.home.BannerBean;
import com.quick.common.bean.main.ArticleListBean;
import com.quick.common.config.HttpConfig;
import com.quick.common.mvp.base.BasePresenter;
import com.quick.core.rxhttp.OnError;
import com.rxjava.rxlife.RxLife;
import rxhttp.wrapper.param.RxHttp;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/10 0010 15:32
 */
public class HomePresenter extends BasePresenter<HomeView>{

    public HomePresenter(LifecycleOwner owner, HomeView mvpView, Activity activity) {
        super(owner, mvpView, activity);
    }

    public void getBanner() {
       RxHttp.get(HttpConfig.BANNER)
            .asResponseList(BannerBean.class)
            .as(RxLife.asOnMain(this)) //这里的this 为Scope接口对象
            .subscribe(pageList -> {
                //成功回调
                mvpView.getBannerSuccess(pageList);
            }, (OnError) error -> {
                //失败回调
            });
    }

    public void getArticleList(int page){
        RxHttp.get(String.format(HttpConfig.ARTICLE_LIST, page))
            .asResponse(ArticleListBean.class)
            .as(RxLife.asOnMain(this))
            .subscribe(s -> {
                //成功回调
                mvpView.getArticleListSuccess(s);
            }, (OnError) error -> {
                //失败回调
            });
    }
}
