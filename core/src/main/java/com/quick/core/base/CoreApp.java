package com.quick.core.base;

import android.content.Context;
import android.content.res.Configuration;
import com.quick.core.utils.LogUtils;
import com.quick.core.utils.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/5 15:53
 */
public abstract class CoreApp extends App{
    private static final List<String> APP_LIKE_LIST = new ArrayList<>();

    private final List<AppLike> mAppLikeList = new ArrayList<>(APP_LIKE_LIST.size());

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        findAppLike();
        onAppLikeAttachBaseContext(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        onAppLikeCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onAppLikeConfigurationChanged(newConfig);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        onAppLikeTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        onAppLikeLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        onAppLikeTrimMemory(level);
    }

    private void onAppLikeAttachBaseContext(Context context) {
        for (AppLike appLike : mAppLikeList) {
            long ts = System.currentTimeMillis();
            appLike.attachBaseContext(context);
            long te = System.currentTimeMillis();
            logAppLikeInfo(appLike, "attachBaseContext", te - ts);
        }
    }

    private void onAppLikeCreate() {
        for (AppLike appLike : mAppLikeList) {
            long ts = System.currentTimeMillis();
            appLike.onCreate(getApp());
            long te = System.currentTimeMillis();
            logAppLikeInfo(appLike, "attach", te - ts);
        }
    }

    private void onAppLikeConfigurationChanged(Configuration newConfig) {
        for (AppLike appLike : mAppLikeList) {
            long ts = System.currentTimeMillis();
            appLike.onConfigurationChanged(getApp(), newConfig);
            long te = System.currentTimeMillis();
            logAppLikeInfo(appLike, "onConfigurationChanged", te - ts);
        }
    }

    private void onAppLikeTerminate() {
        for (AppLike appLike : mAppLikeList) {
            long ts = System.currentTimeMillis();
            appLike.onTerminate(getApp());
            long te = System.currentTimeMillis();
            logAppLikeInfo(appLike, "onTerminate", te - ts);
        }
    }

    private void onAppLikeLowMemory() {
        for (AppLike appLike : mAppLikeList) {
            long ts = System.currentTimeMillis();
            appLike.onLowMemory(getApp());
            long te = System.currentTimeMillis();
            logAppLikeInfo(appLike, "onLowMemory", te - ts);
        }
    }

    private void onAppLikeTrimMemory(int level) {
        for (AppLike appLike : mAppLikeList) {
            long ts = System.currentTimeMillis();
            appLike.onTrimMemory(getApp(), level);
            long te = System.currentTimeMillis();
            logAppLikeInfo(appLike, "onTrimMemory", te - ts);
        }
    }

    private void findAppLike() {
        for (String classPath : APP_LIKE_LIST) {
            try {
                Class clazz = Class.forName(classPath);
                AppLike appLike = (AppLike) clazz.newInstance();
                mAppLikeList.add(appLike);
            } catch (Exception ignore) {
            }
        }
    }

    private void logAppLikeInfo(AppLike a, String msg, long d) {
        LogUtils.i(a.getClass().getSimpleName(), msg + " [" + d + "ms]");
    }
}
