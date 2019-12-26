package com.quick.core.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;
import com.quick.core.utils.AppManager;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/6 17:18
 */
public abstract class CoreActivity extends AppCompatActivity {
    //页面标记 方便跳转
    String acTag = "";
    public Context context;

    public void setTag(String tag) {
        this.acTag = tag;
    }

    public String getTag() {
        return acTag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }


    public void jumpActivity(Class clazz) {
        jumpActivity(clazz, null);
    }

    public void jumpActivityForResult(Class clazz, int code) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, code);
    }

    public void jumpActivityForResult(Class clazz, Bundle bundle, int code) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, code);
    }

    /**
     * 快捷跳转函数
     *
     * @param clazz
     * @param bundle
     */
    public void jumpActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void onResult(Object data) {}

    public abstract void showLoad();

    public abstract void hintLoad();


    /**
     * 和 setContentView 对应的方法
     */
    public ViewGroup getContentView() {
        return findViewById(Window.ID_ANDROID_CONTENT);
    }

}
