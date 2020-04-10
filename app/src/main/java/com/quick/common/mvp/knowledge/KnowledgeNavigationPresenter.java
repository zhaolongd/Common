package com.quick.common.mvp.knowledge;

import android.app.Activity;
import androidx.lifecycle.LifecycleOwner;

import com.quick.common.mvp.base.BasePresenter;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/10 0010 15:36
 */
public class KnowledgeNavigationPresenter extends BasePresenter<KnowledgeNavigationView>{

    public KnowledgeNavigationPresenter(LifecycleOwner owner, KnowledgeNavigationView mvpView, Activity activity) {
        super(owner, mvpView, activity);
    }
}
