package com.quick.core.rxhttp.entity;

import java.io.Serializable;

/**
 * Created by zhaolong.
 * Description: Http返回的数据结构抽象成下面的Bean类
 * Date: 2019/12/5 14:23
 */

public class Response<T> implements Serializable{
//    public boolean status;
    private int errorCode;
    private String errorMsg;
    private T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //    @Override
//    public String toString() {
//        return "Response{\n" +//
//                "\tstatus=" + status + "\n" +//
//                "\tcode=" + code + "\n" +//
//                "\tmsg='" + msg + "\'\n" +//
//                "\tdata=" + data + "\n" +//
//                '}';
//    }
}
