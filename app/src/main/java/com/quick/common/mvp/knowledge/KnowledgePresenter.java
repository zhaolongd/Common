package com.quick.common.mvp.knowledge;

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
 * Date: 2019/12/21 0021 15:30
 */
public class KnowledgePresenter extends BasePresenter<KnowledgeView>{
    public KnowledgePresenter(LifecycleOwner owner, KnowledgeView mvpView, Activity activity) {
        super(owner, mvpView, activity);
    }

    public void getKnowledgeList(){
        RxHttp.get(HttpConfig.KNOWLEDGE_LIST)
            .asResponseList(ChapterBean.class)
            .as(RxLife.asOnMain(this))
            .subscribe(s -> {
                //成功回调
                mvpView.getKnowledgeListSuccess(s);
            }, (OnError) error -> {
                //失败回调
            });
    }
}
