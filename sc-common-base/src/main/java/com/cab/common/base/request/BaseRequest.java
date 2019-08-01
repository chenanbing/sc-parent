package com.cab.common.base.request;

import java.io.Serializable;

public class BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登陆验证token
     */
    private String toke;

    public String getToke() {
        return toke;
    }

    public void setToke(String toke) {
        this.toke = toke;
    }
}
