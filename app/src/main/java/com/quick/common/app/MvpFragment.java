package com.quick.common.app;

import android.os.Bundle;
import com.quick.common.mvp.base.BasePresenter;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/7 17:02
 */
public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment {

    protected P mvpPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter = createPresenter();
    }

    protected abstract P createPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
            mvpPresenter = null;
        }
    }
}
