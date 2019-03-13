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

    private int code;

    private String msg;

    private T data;

    public ServerResponse() {
    }

    private ServerResponse(int code) {
        this.code = code;
    }

    private ServerResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ServerResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    private ServerResponse(int code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg());
    }

    public static <T> ServerResponse<T> createBySuccess(String msg) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ServerResponse<T> createByError(int errorCode) {
        return new ServerResponse<T>(errorCode);
    }

    public static <T> ServerResponse<T> createByError(int errorCode, String msg) {
        return new ServerResponse<T>(errorCode, msg);
    }

    public static <T> ServerResponse<T> createByError(int errorCode, T data) {
        return new ServerResponse<T>(errorCode, data);
    }

    public static <T> ServerResponse<T> createByError(int errorCode, String msg, T data) {
        return new ServerResponse<T>(errorCode, msg, data);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }


}
