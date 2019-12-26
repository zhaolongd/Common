package com.quick.common.mvp.home;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;

import com.quick.common.bean.home.HotKeyBean;
import com.quick.common.config.HttpConfig;
import com.quick.common.mvp.base.BasePresenter;
import com.quick.common.utils.SearchHistoryUtils;
import com.quick.common.utils.SettingUtils;
import com.quick.core.rxhttp.OnError;
import com.rxjava.rxlife.RxLife;

import java.util.List;

import rxhttp.wrapper.param.RxHttp;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/25 0025 15:17
 */
public class SearchHistoryPresenter extends BasePresenter<SearchHistoryView> {
    private final SearchHistoryUtils mSearchHistoryUtils = SearchHistoryUtils.newInstance();

    public SearchHistoryPresenter(LifecycleOwner owner, SearchHistoryView mvpView, Activity activity) {
        super(owner, mvpView, activity);
    }

    public void getHotKeyList(){
        RxHttp.get(HttpConfig.HOT_KEY_LIST)
            .asResponseList(HotKeyBean.class)
            .as(RxLife.asOnMain(this))
            .subscribe(s -> {
                //成功回调
                mvpView.getHotKeyListSuccess(s);
            }, (OnError) error -> {
                //失败回调
            });
    }

    public List<String> getHistory(){
        return mSearchHistoryUtils.get();
    }

    public void saveHistory(List<String> list){
        List<String> saves = list;
        int max = SettingUtils.getInstance().getSearchHistoryMaxCount();
        if (list != null && list.size() > max) {
            saves = list.subList(0, max - 1);
        }
        mSearchHistoryUtils.save(saves);
    }
}
