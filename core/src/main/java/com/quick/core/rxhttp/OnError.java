package com.quick.core.rxhttp;

import com.quick.core.rxhttp.entity.ErrorInfo;
import io.reactivex.functions.Consumer;

/**
 * Created by zhaolong.
 * Description: RxJava 错误回调 ,加入网络异常处理
 * Date: 2019/12/6 0006 14:28
 */
public interface OnError extends Consumer<Throwable> {

    @Override
    default void accept(Throwable throwable) throws Exception {
        onError(new ErrorInfo(throwable));
    }

    void onError(ErrorInfo error) throws Exception;
}
