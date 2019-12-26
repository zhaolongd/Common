package com.quick.core.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/6 17:32
 */
public class CoreFragment extends Fragment{

    public void jumpActivity(Class clazz) {
        jumpActivity(clazz, null);
    }

    /**
     * 快捷跳转函数
     *
     * @param clazz
     * @param bundle
     */
    public void jumpActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        if (bundle != null) {
            intent.putExtras( bundle);
        }
        startActivity(intent);
    }

    public void jumpActivityForResult(Class clazz, int code) {
        Intent intent = new Intent(getContext(), clazz);
        startActivityForResult(intent, code);
    }

    public void jumpActivityForResult(Class clazz, Bundle bundle, int code) {
        Intent intent = new Intent(getContext(), clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, code);
    }
}
