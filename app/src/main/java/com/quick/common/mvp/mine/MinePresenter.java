package com.quick.common.mvp.mine;

import android.app.Activity;
import androidx.lifecycle.LifecycleOwner;

import com.quick.common.mvp.base.BasePresenter;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/10 0010 15:43
 */
public class MinePresenter extends BasePresenter<MineView>{

    public MinePresenter(LifecycleOwner owner, MineView mvpView, Activity activity) {
        super(owner, mvpView, activity);
    }
}
