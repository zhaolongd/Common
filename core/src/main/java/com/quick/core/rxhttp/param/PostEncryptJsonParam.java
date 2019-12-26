package com.quick.core.rxhttp.param;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rxhttp.wrapper.annotation.Param;
import rxhttp.wrapper.param.JsonParam;
import rxhttp.wrapper.param.Method;

/**
 * Created by zhaolong.
 * Description: 发送Post请求，参数以Json字符串的形式提交，并且参数需要加密
 * Date: 2019/12/5 15:04
 */
@Param(methodName = "postEncryptJson")
public class PostEncryptJsonParam extends JsonParam {

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public PostEncryptJsonParam(String url) {
        super(url, Method.POST);
    }

    /**
     * @return 根据自己的业务需求返回对应的RequestBody
     */
    @Override
    public RequestBody getRequestBody() {
       /* //我们要发送Post请求，参数以加密后的json形式发出
        //第一步，将参数转换为Json字符串
        JsonObject jsonObject = BuildUtil.mapToJson(this);
        String json = jsonObject.toString();
        //第二步，加密
        byte[] encryptByte = encrypt(json, "RxHttp");
        //第三部，创建RequestBody并返回
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, encryptByte);
        return requestBody;*/
       return null;
    }

    /**
     * @param content  要加密的字符串
     * @param password 密码
     * @return 加密后的字节数组
     */
    private byte[] encrypt(String content, String password) {
        //加密代码省略
        return null;
    }
}
