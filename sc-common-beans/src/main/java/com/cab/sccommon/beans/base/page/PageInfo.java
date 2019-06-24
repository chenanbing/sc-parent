package com.cab.sccommon.beans.base.page;

import java.io.Serializable;
import java.util.List;

public class PageInfo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int page;

    private int pageSize;

    private long total;

    private int pages;

    private List<T> list;

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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public static Integer getPage(Integer total, Integer pageSize){
        return  (total  +  pageSize  - 1) / pageSize;
    }

    public static Integer getStart(Integer page, Integer pageSize){
        return  (page - 1) * pageSize;
    }

    public static Integer getEnd(Integer page, Integer pageSize){
        return  page * pageSize;
    }
}
