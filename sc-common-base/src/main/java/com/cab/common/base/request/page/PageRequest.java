
package com.cab.common.base.request.page;

import java.io.Serializable;

public class PageRequest<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    private int current = 1;
    /**
     * 每页显示条数，默认 10
     */
    private int size = 10;
    /**
     * 查询参数
     */
    private T condition;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public T getCondition() {
        return condition;
    }

    public void setCondition(T condition) {
        this.condition = condition;
    }
}
