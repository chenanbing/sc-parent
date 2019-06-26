package com.cab.lib.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class ListObj implements Serializable {

    public List list ;

    public ListObj(){
        this.list = new ArrayList();
    }

    public ListObj(List list) {
        this.list = list;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
