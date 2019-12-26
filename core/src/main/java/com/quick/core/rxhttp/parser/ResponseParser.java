package com.quick.core.rxhttp.parser;

import com.quick.core.rxhttp.entity.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;

/**
 * Created by zhaolong.
 * Description: 自定义解析器
 * Date: 2019/12/5 14:27
 */

@Parser(name = "Response")
public class ResponseParser<T> extends AbstractParser<T> {
    protected ResponseParser() {
        super();
    }

    public ResponseParser(Class<T> type) {
        super(type);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T onParse(okhttp3.Response response) throws IOException {
        final Type type = ParameterizedTypeImpl.get(Response.class, mType); //获取泛型类型
        Response<T> data = convert(response, type);
        T t = data.getData(); //获取data字段
        if (t == null && mType == String.class) {
            /*
             * 考虑到有些时候服务端会返回：{"errorCode":0,"errorMsg":"关注成功"}  类似没有data的数据
             * 此时code正确，但是data字段为空，直接返回data的话，会报空指针错误，
             * 所以，判断泛型为String类型时，重新赋值，并确保赋值不为null
             */
            t = (T) data.getErrorMsg();
        }
        if (data.getErrorCode() != 0 || t == null) {//code不等于0，说明数据不正确，抛出异常
            throw new ParseException(String.valueOf(data.getErrorCode()), data.getErrorMsg(), response);
        }
        return t;
    }
}
