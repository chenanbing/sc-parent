package com.cab.common.base.key.redis;


import com.cab.common.base.config.TimeConfig;

/**
 */
public enum RedisKeyEnum {


    USER_TOKEN("USER:TOKEN",TimeConfig.WEEK,"登陆token"),
    USER_NO_EXPIRE("USER:NO:EXPIRE","不超时"),
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

    public RedisKey getRedisKey() {
        return new RedisKey(this.key,this.expireTime,this.desc);
    }

    public RedisKey getRedisKey(Object... args) {
        return new RedisKey(this.key,this.expireTime,this.desc,args);
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
        System.err.println(RedisKeyEnum.USER_NO_EXPIRE.getRedisKey().getKey());
        System.err.println(RedisKeyEnum.USER_TOKEN.getRedisKey("a", "b", "c", "d", 3333).getKeyStr());
    }
}
