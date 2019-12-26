package com.quick.common.mvp.wx;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;

import com.quick.common.bean.main.ChapterBean;
import com.quick.common.config.HttpConfig;
import com.quick.common.mvp.base.BasePresenter;
import com.quick.core.rxhttp.OnError;
import com.rxjava.rxlife.RxLife;

import rxhttp.wrapper.param.RxHttp;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/10 0010 15:39
 */
public class WxPresenter extends BasePresenter<WxView>{

    public WxPresenter(LifecycleOwner owner, WxView mvpView, Activity activity) {
        super(owner, mvpView, activity);
    }

    public void getWxArticleChapters(){
        RxHttp.get(HttpConfig.WX_ARTICLE_CHAPTER)
            .asResponseList(ChapterBean.class)
            .as(RxLife.asOnMain(this))
            .subscribe(s -> {
                //成功回调
                mvpView.getWxArticleChaptersSuccess(s);
            }, (OnError) error -> {
                //失败回调
            });
    }

}
