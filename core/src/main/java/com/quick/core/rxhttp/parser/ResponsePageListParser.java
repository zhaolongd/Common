package com.quick.core.rxhttp.parser;

import com.quick.core.rxhttp.entity.PageList;
import com.quick.core.rxhttp.entity.Response;
import java.io.IOException;
import java.lang.reflect.Type;
import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;

/**
 * Created by zhaolong.
 * Description: Response<PageList<T>> 数据解析器,解析完成对Response对象做判断,如果ok,返回数据 PageList<T>
 * Date: 2019/12/6 14:42
 */

@Parser(name = "ResponsePageList")
public class ResponsePageListParser<T> extends AbstractParser<PageList<T>> {
    protected ResponsePageListParser() {
        super();
    }

    public ResponsePageListParser(Class<T> type) {
        super(type);
    }

    @Override
    public PageList<T> onParse(okhttp3.Response response) throws IOException {
        final Type type = ParameterizedTypeImpl.get(Response.class, PageList.class, mType); //获取泛型类型
        Response<PageList<T>> data = convert(response, type);
        PageList<T> pageList = data.getData(); //获取data字段
        if (data.getErrorCode() != 0 || pageList == null) {  //code不等于0，说明数据不正确，抛出异常
            throw new ParseException(String.valueOf(data.getErrorCode()), data.getErrorMsg(), response);
        }
        return pageList;
    }
}
