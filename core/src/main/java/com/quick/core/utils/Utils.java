package com.quick.core.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/5  16:00
 */
public class Utils {
    @SuppressLint("StaticFieldLeak")
    private static Context context = null;
    private static List<OnInit> sOnInits = null;

    public static void init(Context context) {
        Utils.context = context;
        LogUtils.init(context);
//        Blurred.init(context);
        if (sOnInits != null) {
            for (OnInit onInit : sOnInits) {
                onInit.onInit(context);
            }
        }
    }

    public static Context getAppContext() {
        if (context == null) {
            throw new RuntimeException("Utils未在Application中初始化");
        }
        return context;
    }

    public static void onInit(OnInit onInit) {
        if (sOnInits == null) {
            sOnInits = new ArrayList<>();
        }
        sOnInits.add(onInit);
    }

    public interface OnInit {
        void onInit(Context context);
    }
}
