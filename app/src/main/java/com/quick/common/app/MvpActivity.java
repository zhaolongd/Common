package com.quick.common.app;

import android.os.Bundle;
import com.quick.common.mvp.base.BasePresenter;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/7 17:00
 */
public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity{
    protected P mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();

        }
    }
}
