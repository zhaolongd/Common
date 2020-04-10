package com.quick.common.mvp.project;

import android.app.Activity;
import androidx.lifecycle.LifecycleOwner;
import com.quick.common.bean.main.ChapterBean;
import com.quick.common.config.HttpConfig;
import com.quick.common.mvp.base.BasePresenter;
import com.quick.core.rxhttp.OnError;
import com.rxjava.rxlife.RxLife;
import rxhttp.wrapper.param.RxHttp;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/10 0010 15:41
 */
public class ProjectPresenter extends BasePresenter<ProjectView>{

    public ProjectPresenter(LifecycleOwner owner, ProjectView mvpView, Activity activity) {
        super(owner, mvpView, activity);
    }

    public void getProjectChapters(){
        RxHttp.get(HttpConfig.PROJECT_CHAPTER)
                .asResponseList(ChapterBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(s -> {
                    //成功回调
                    mvpView.getProjectChaptersSuccess(s);
                }, (OnError) error -> {
                    //失败回调
                });
    }
}
