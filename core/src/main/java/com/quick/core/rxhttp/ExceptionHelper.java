package com.quick.core.rxhttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.quick.core.R;
import com.quick.core.base.App;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

/**
 * Created by zhaolong.
 * Description: 异常处理帮助类
 * Date: 2019/12/6 14:14
 */
public class ExceptionHelper {
    //处理网络异常
    public static <T> String handleNetworkException(T throwable) {
        int stringId = -1;
        if (throwable instanceof UnknownHostException) {
            if (!isNetworkConnected(App.getApp())) {
                stringId = R.string.network_error;
            } else {
                stringId = R.string.notify_no_network;
            }
        } else if (throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException) {
            //前者是通过OkHttpClient设置的超时引发的异常，后者是对单个请求调用timeout方法引发的超时异常
            stringId = R.string.time_out_please_try_again_later;
        } else if (throwable instanceof ConnectException) {
            stringId = R.string.esky_service_exception;
        }
        return stringId == -1 ? null : App.getApp().getString(stringId);
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
