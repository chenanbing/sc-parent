package com.cab.common.beans.database.view;

import java.io.Serializable;

public class BaseVO implements Serializable {

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
