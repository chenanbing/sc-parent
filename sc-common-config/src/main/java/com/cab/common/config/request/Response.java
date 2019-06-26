package com.cab.common.config.request;


import com.cab.common.config.code.ErrorCodeEnum;

import java.io.Serializable;

public class Response<T> implements Serializable {

    private int code;
    private String message;
    private T data;

    public Response() {
        this.setErrorCode(ErrorCodeEnum.SUCCESS);
    }

    public Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setErrorCode(ErrorCodeEnum errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = null;
    }

    public void setErrorCode(ErrorCodeEnum errorCode, String msg) {
        this.code = errorCode.getCode();
        this.message = String.format(errorCode.getMessage(), msg);
        this.data = null;
    }

}
