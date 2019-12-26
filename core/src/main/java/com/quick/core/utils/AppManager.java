package com.quick.core.utils;

import android.app.ActivityManager;
import android.content.Context;
import com.quick.core.base.App;
import com.quick.core.base.CoreActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhaolong on 2019/12/6.
 */

public class AppManager {
    private static final String TAG = AppManager.class.getSimpleName();
    private static AppManager mInstance = null;
    private List<CoreActivity> activityList = Collections.synchronizedList(new ArrayList<CoreActivity>());

    private AppManager() {
    }

    public static synchronized AppManager getAppManager() {
        if (mInstance == null) {
            mInstance = new AppManager();
        }
        return mInstance;
    }

    /**
     * 添加一个ACT
     *
     * @param activity
     */
    public void addActivity(CoreActivity activity) {
        if (activity == null) {
            LogUtils.e(TAG, "Failed to add BaseActivity");
            return;
        }
        activityList.add(activity);
    }

    /**
     * 移除一个ACT
     *
     * @param activity
     */
    public void removeActivity(CoreActivity activity) {
        if (activityList == null || activity == null) {
            LogUtils.e(TAG, "Failed to remove BaseActivity");
            return;
        }
        activityList.remove(activity);
    }

    /**
     * 获取当前页面的上一个页面
     *
     * @param act 当前页面
     * @return {@link CoreActivity}
     */
    public CoreActivity getPreActivity(CoreActivity act) {
        int index = activityList.indexOf(act);
        if (-1 == index || index < 1)
            return null;
        return activityList.get(index - 1);
    }

    /**
     * 返回到应用程序首页
     */
    public void backToHome() {
        backTo(1);
    }

    /**
     * 返回指定的步骤
     *
     * @param step 指定的步骤
     */
    private void backTo(int step) {
        if (activityList == null) {
            LogUtils.e(TAG, String.format("Failed to backTo(%s)", step));
            return;
        }
        synchronized (activityList) {
            if (step < 0 || step > activityList.size()) {
                return;
            }
            List<CoreActivity> sub = new ArrayList<CoreActivity>(activityList.subList(step, activityList.size()));
            while (!sub.isEmpty()) {
                CoreActivity a = sub.remove(0);
                activityList.remove(a);
                if (a != null && !a.isFinishing()) {
                    if (sub.size() != 0) {
//                        a.setFinishAnimation(0);
                    }
                    a.finish();
                }
            }
//            activities.get(activities.size()-1).onResult(new Bundle());
        }
    }



    /**
     * 返回到标记页面之前的页面，标记页面也关闭
     *
     * @param tag 页面标记
     */
    public void backToTagFront(Object tag) {
        if (activityList == null) {
            LogUtils.e(TAG, String.format("Failed to backToTagFront()"));
            return;
        }
        int step = 0;
        synchronized (activityList) {
            for (CoreActivity a : activityList) {
                step++;
                if (tag != null && tag.equals(a.getTag())) {
                    break;
                }
            }
        }
        if (step > 0) {
            step -= 1;
        }
        backTo(step);
    }

    /**
     * 返回到标记页面，标记页面不关闭
     *
     * @param tag 页面标记
     */
    public void backToTag(Object tag) {
        backToTag(tag, null);
    }

    /**
     * 返回到标记页面，标记页面不关闭
     *
     * @param tag 页面标记
     */
    public void backToTag(Object tag, Object bundle) {
        if (activityList == null) {
            LogUtils.e(TAG, String.format("Failed to backToTag()"));
            return;
        }
        int step = 0;
        synchronized (activityList) {
            for (CoreActivity a : activityList) {
                step++;
                if (tag != null && tag.equals(a.getTag())) {
                    break;
                }
            }
        }
        backTo(step, bundle);
    }

    private void backTo(int step, Object bundle) {
        backTo(step);
        activityList.get(activityList.size() - 1).onResult(bundle);
    }

    /**
     * 清除所有ACT
     */
    public void clearAllActivities() {
        activityList.clear();
    }

    /**
     * 关闭app
     */
    public void closeApp() {
        if (activityList == null) {
            return;
        }
        synchronized (activityList) {
            for (int i = 0; i < activityList.size(); i++) {
                CoreActivity a = activityList.get(i);
                activityList.remove(a);
                if (a != null && !a.isFinishing()) {
//                    a.setFinishAnimation(0);
                    a.finish();
                }
//            activities.get(activities.size()-1).onResult(new Bundle());
            }
            ActivityManager activityManager = (ActivityManager) App.getApp().getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(App.getApp().getPackageName());
            System.exit(0);
        }
    }

}
