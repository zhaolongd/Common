package com.quick.core.rxhttp.parser;

import com.quick.core.rxhttp.entity.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;

/**
 * Created by zhaolong.
 * Description: ResponseListParser解析器
 * Date: 2019/12/5 14:37
 */

@Parser(name = "ResponseList")
public class ResponseListParser<T> extends AbstractParser<List<T>> {
    protected ResponseListParser() {
        super();
    }

    public ResponseListParser(Class<T> type) {
        super(type);
    }

    @Override
    public List<T> onParse(okhttp3.Response response) throws IOException {
        final Type type = ParameterizedTypeImpl.get(Response.class, List.class, mType); //获取泛型类型
        Response<List<T>> data = convert(response, type);
        List<T> list = data.getData(); //获取data字段
        if (data.getErrorCode() != 0 || list == null) {  //code不等于0，说明数据不正确，抛出异常
            throw new ParseException(String.valueOf(data.getErrorCode()), data.getErrorMsg(), response);
        }
        return list;
    }
}
