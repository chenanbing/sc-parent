package com.cab.lib.redis;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class MapObj implements Serializable {

    public Map map;

    public MapObj() {
        this.map = new HashMap();
    }

    public MapObj(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

}
