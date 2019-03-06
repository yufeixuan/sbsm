package com.snsprj.sbsm.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;

/**
 * Created by John on 2017/6/11.
 */
@JsonInclude(value = Include.NON_NULL)
public class ServerResponse<T> implements Serializable {

    private final static String SUCCESS_CODE = "200";

    private String code;

    private String msg;

    private T data;

    public ServerResponse(){}

    private ServerResponse(String code) {
        this.code = code;
    }

    private ServerResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ServerResponse(String code, T data) {
        this.code = code;
        this.data = data;
    }

    private ServerResponse(String code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code.equals(SUCCESS_CODE);
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(SUCCESS_CODE);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg) {
        return new ServerResponse<T>(SUCCESS_CODE, msg);
    }

    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<T>(SUCCESS_CODE, data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(SUCCESS_CODE, msg, data);
    }

    public static <T> ServerResponse<T> createByError(String errorCode) {
        return new ServerResponse<T>(errorCode);
    }

    public static <T> ServerResponse<T> createByError(String errorCode, String msg) {
        return new ServerResponse<T>(errorCode, msg);
    }

    public static <T> ServerResponse<T> createByError(String errorCode, T data) {
        return new ServerResponse<T>(errorCode, data);
    }

    public static <T> ServerResponse<T> createByError(String errorCode, String msg, T data) {
        return new ServerResponse<T>(errorCode, msg, data);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }


}
