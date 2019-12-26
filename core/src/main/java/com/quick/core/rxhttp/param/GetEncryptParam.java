package com.quick.core.rxhttp.param;

import rxhttp.wrapper.annotation.Param;
import rxhttp.wrapper.param.Method;
import rxhttp.wrapper.param.NoBodyParam;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/6 16:14
 */

@Param(methodName = "getEncrypt")
public class GetEncryptParam extends NoBodyParam {

    public GetEncryptParam(String url) {
        super(url, Method.GET);
    }

}
