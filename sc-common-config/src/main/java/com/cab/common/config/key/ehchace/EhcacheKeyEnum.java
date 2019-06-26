package com.cab.common.config.key.ehchace;

import com.cab.common.config.base.TimeConfig;

/**
 */
public enum EhcacheKeyEnum {
    DS(":", TimeConfig.NO_EXPIRE),
    ;


    private String prefix;
    private Integer expire;

    EhcacheKeyEnum(Integer expireTime) {
        try {
            this.prefix = this.name().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.expire = expireTime;
    }

    EhcacheKeyEnum(String prefix, Integer expireTime) {
        this.expire = 0;
        this.prefix = prefix;
    }

    public Integer getExpire() {
        return expire;
    }

    /*public String getPrefix() {
        return prefix;
    }*/

    public String getKey() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        return stringBuilder.toString();
    }

    public String getKey(Object... args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DS.prefix).append(this.prefix);
        if (args.length > 0) {
            for (Object key : args) {
                key = null == key ? "" : key;
                stringBuilder.append(EhcacheKeyEnum.DS.prefix + key.toString());
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
    }
}
