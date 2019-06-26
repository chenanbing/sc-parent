package com.cab.lib.redis;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 */
public class SetObj implements Serializable {

    public Set set;

    public SetObj() {
        this.set = new HashSet();
    }

    public SetObj(Set set) {
        this.set = set;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }

}
