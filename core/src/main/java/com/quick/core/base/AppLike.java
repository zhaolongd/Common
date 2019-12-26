package com.quick.core.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/5 15:58
 */
interface AppLike {
    void attachBaseContext(Context context);
    void onCreate(Application app);
    void onConfigurationChanged(Application app, Configuration newConfig);
    void onTerminate(Application app);
    void onLowMemory(Application app);
    void onTrimMemory(Application app, int level);
}
