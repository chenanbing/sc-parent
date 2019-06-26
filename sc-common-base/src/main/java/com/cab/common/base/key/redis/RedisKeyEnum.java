package com.cab.common.base.key.redis;


import com.cab.common.base.config.TimeConfig;

/**
 */
public enum RedisKeyEnum {


    USER_TOKEN("USER:TOKEN",TimeConfig.WEEK,"登陆token"),
    ;

    private String key;
    private int expireTime;
    private String desc;

    RedisKeyEnum(String key, int expireTime, String desc) {
        this.key = key;
        this.expireTime = expireTime;
        this.desc = desc;
    }

    RedisKeyEnum(String key, String desc) {
        this.key = key;
        this.expireTime = TimeConfig.NO_EXPIRE;
        this.desc = desc;
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

    public String getKey(Object... args) {
        RedisKey redisKey = this.get(args);
        return redisKey.get();
    }

    public RedisKey get(Object... args) {
        return new RedisKey(this.key,this.desc,this.expireTime,args);
    }

    @Override
    public String toString() {
        return "RedisKeyEnum{" +
                "key='" + key + '\'' +
                ", expireTime=" + expireTime +
                ", desc='" + desc + '\'' +
                '}';
    }
    public static void main(String[] args) {
        System.err.println(RedisKeyEnum.USER_TOKEN.get("a", "b", "c", "d", 3333).get());
    }
}
