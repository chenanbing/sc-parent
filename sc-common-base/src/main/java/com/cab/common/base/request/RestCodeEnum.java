package com.cab.common.base.request;

import java.util.HashMap;
import java.util.Map;

public enum RestCodeEnum {

    SUCCESS(0, "success"),
    FAIL(1, "fail"),
    SYSTEM_ERROR(1001, "系统异常"),
    PARAM_ERROR(1002, "参数错误 %s"),
    TOKEN_ERROR(1003, "登录失效"),
    ;

    private int code;
    private String message;

    RestCodeEnum(int code, String message) {
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

    public static Map<Integer, RestCodeEnum> map = new HashMap<Integer, RestCodeEnum>();
    static {
        for (RestCodeEnum e : RestCodeEnum.values()) {
            map.put(e.code, e);
        }
    }

    public static RestCodeEnum getErrorCodeEnum(int code){
        return map.get(code);
    }

    public static RestResponse getRestResponse(){
        return getRestResponse(RestCodeEnum.SUCCESS);
    }

    public static RestResponse getRestResponse(RestCodeEnum errorCode) {
        return new RestResponse(errorCode.code,errorCode.message);
    }

    public static RestResponse getRestResponse(RestCodeEnum errorCode, String msg) {
        return new RestResponse(errorCode.code,String.format(errorCode.message, msg));
    }
}
