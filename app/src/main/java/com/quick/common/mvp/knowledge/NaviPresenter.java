package com.quick.common.mvp.knowledge;

import android.app.Activity;
import androidx.lifecycle.LifecycleOwner;

import com.quick.common.bean.main.NaviBean;
import com.quick.common.config.HttpConfig;
import com.quick.common.mvp.base.BasePresenter;
import com.quick.core.rxhttp.OnError;
import com.rxjava.rxlife.RxLife;

import rxhttp.wrapper.param.RxHttp;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/21 0021 15:40
 */
public class NaviPresenter extends BasePresenter<NaviView>{
    public NaviPresenter(LifecycleOwner owner, NaviView mvpView, Activity activity) {
        super(owner, mvpView, activity);
    }

    public void getNaviList(){
        RxHttp.get(HttpConfig.NAVI_LIST)
            .asResponseList(NaviBean.class)
            .as(RxLife.asOnMain(this))
            .subscribe(s -> {
                //成功回调
                mvpView.getNaviListSuccess(s);
            }, (OnError) error -> {
                //失败回调
            });
    }
}
