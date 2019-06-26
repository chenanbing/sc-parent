package com.cab.common.base.key.ehchace;

import java.util.HashMap;
import java.util.Map;

/**
 */
public enum EhcacheConfig {
    push("push", "推送"),
    ;
    private String cacheKey;
    private String cacheName;

    EhcacheConfig(String cacheKey, String cacheName) {
        this.cacheKey = cacheKey;
        this.cacheName = cacheName;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    @Override
    public String toString() {
        return cacheKey;
    }

    private final static Map<String,EhcacheConfig> map = new HashMap<String, EhcacheConfig>();
    static {
        for (EhcacheConfig c : EhcacheConfig.values()) {
            map.put(c.getCacheKey(),c);
        }
    }

    public static EhcacheConfig getConfig(String key){
        return map.get(key);
    }
}
