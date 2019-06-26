package com.cab.common.config.code;

import java.util.HashMap;
import java.util.Map;

public enum ErrorCodeEnum {

    SUCCESS(0, "success"),
    FAIL(1, "fail"),
    SYSTEM_ERROR(1001, "系统异常"),
    PARAM_ERROR(1002, "参数错误 %s"),
    TOKEN_ERROR(1003, "登录失效"),
    ;

    private int code;
    private String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
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

    public static Map<Integer,ErrorCodeEnum> map = new HashMap<Integer, ErrorCodeEnum>();
    static {
        for (ErrorCodeEnum e : ErrorCodeEnum.values()) {
            map.put(e.code, e);
        }
    }

    public static ErrorCodeEnum getErrorCodeEnum(int code){
        return map.get(code);
    }

}
