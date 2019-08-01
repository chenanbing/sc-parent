package com.cab.common.base.key.redis;

import com.cab.common.base.config.BaseConfig;

import java.util.Arrays;

public class RedisKey {
    private String key;
    private int expireTime;
    private String desc;
    private Object[] args;

    public RedisKey() {

    }

    public RedisKey(String key, int expireTime, String desc) {
        this.key = key;
        this.expireTime = expireTime;
        this.desc = desc;
    }

    public RedisKey(String key, int expireTime, String desc, Object... args) {
        this.key = key;
        this.expireTime = expireTime;
        this.desc = desc;
        this.args = args;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }


    @Override
    public String toString() {
        return "RedisKey{" +
                "key='" + key + '\'' +
                ", expireTime=" + expireTime +
                ", desc='" + desc + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }

    public String getKeyStr() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getKey());
        if (args == null || args.length == 0) {
            return stringBuilder.toString();
        }
        stringBuilder.append(BaseConfig.COLON);
        String prefix = BaseConfig.EMPTY;
        for (Object arg : args) {
            if (null == arg) {
                continue;
            }
            String argStr = arg.toString();
            stringBuilder.append(prefix);
            stringBuilder.append(argStr);
            prefix = BaseConfig.COLON;
        }
        return stringBuilder.toString();
    }

}
