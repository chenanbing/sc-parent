package com.cab.lib.redis;

import java.util.Arrays;

public class RedisKey {

    public static final String EMPTY = "";
    public static final String DS = ".";
    public static final String COLON = ":";
    public static final String UNDERLINE = "_";

    private String key;
    private String desc;
    private int expireTime;
    private Object[] args;

    public RedisKey() {

    }

    public RedisKey(String key, String desc, int expireTime, Object... args) {
        this.key = key;
        this.desc = desc;
        this.expireTime = expireTime;
        this.args = args;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public RedisKey(RedisKeyEnum redisKeyEnum, Object... args) {
        this.key = redisKeyEnum.getKey();
        this.desc = redisKeyEnum.getDesc();
        this.expireTime = redisKeyEnum.getExpireTime();
        this.args = args;
    }



    public String get() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getKey());
        if (args == null) {
            return stringBuilder.toString();
        }
        stringBuilder.append(COLON);
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
//            System.err.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "RedisKey{" +
                "key='" + key + '\'' +
                ", desc='" + desc + '\'' +
                ", expireTime=" + expireTime +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
