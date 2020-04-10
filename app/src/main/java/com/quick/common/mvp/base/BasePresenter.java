package com.quick.common.mvp.base;

import android.app.Activity;
import androidx.lifecycle.LifecycleOwner;
import android.widget.Toast;
import com.quick.common.config.HttpConfig;
import com.quick.common.config.KeyConfig;
import com.rxjava.rxlife.BaseScope;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/7 16:59
 */
public class BasePresenter <V> extends BaseScope implements HttpConfig, KeyConfig {
    public V mvpView;
    public Activity activity;

    public BasePresenter(LifecycleOwner owner, V mvpView, Activity activity) {
        super(owner);
        this.mvpView = mvpView;
        this.activity = activity;
    }

    public void attachView(V mvpView, Activity activity) {
        this.mvpView = mvpView;
        this.activity = activity;
    }

    public void detachView() {
        this.mvpView = null;
    }


    public interface Callback {
        void ok();
    }

    public void toast(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}
