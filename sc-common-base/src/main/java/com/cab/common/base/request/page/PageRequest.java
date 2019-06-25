
package com.cab.common.base.request.page;

import java.io.Serializable;

public class PageRequest<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int page;
    private int pageSize;
    private T param;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }
}
