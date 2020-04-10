package com.quick.common.mvp.project;

import android.app.Activity;
import androidx.lifecycle.LifecycleOwner;

import com.quick.common.bean.main.ArticleListBean;
import com.quick.common.config.HttpConfig;
import com.quick.common.mvp.base.BasePresenter;
import com.quick.core.rxhttp.OnError;
import com.rxjava.rxlife.RxLife;

import rxhttp.wrapper.param.RxHttp;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/24 0024 15:05
 */
public class ProjectArticlePresenter extends BasePresenter<ProjectArticleView> {

    public ProjectArticlePresenter(LifecycleOwner owner, ProjectArticleView mvpView, Activity activity) {
        super(owner, mvpView, activity);
    }

    public void getProjectArticleList(int id, int page) {
        RxHttp.get(String.format(HttpConfig.PROJECT_ARTICLE_LIST, page))
                .add("cid", id)
                .asResponse(ArticleListBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(s -> {
                    //成功回调
                    mvpView.getProjectArticleListSuccess(s);
                }, (OnError) error -> {
                    //失败回调
                });
    }
}
