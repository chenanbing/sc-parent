package com.cab.lib.redis;

import com.cab.lib.redis.obj.ListObj;
import com.cab.lib.redis.obj.MapObj;
import com.cab.lib.redis.obj.SetObj;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis操作基础接口实现类
 */
public class RedisUtil{

    private RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置 String 类型 key-value
     * @param key
     * @param value
     */
    private void set(String key,String value){
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置 String 类型 key-value 并添加过期时间 (分钟单位)
     * @param key
     * @param value
     * @param time 过期时间,分钟单位
     */
    private void setForTimeCustom(String key,String value,long time,TimeUnit type){
        redisTemplate.opsForValue().set(key, value, time, type);
    }

    /**
     * 设置 String 类型 key-value 并添加过期时间 (毫秒单位)
     * @param key
     * @param value
     * @param time 过期时间,毫秒单位
     */
    private void setForTimeMS(String key,String value,long time){
        this.setForTimeCustom(key, value, time, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取 String 类型 key-value
     * @param key
     * @return
     */
    private String get(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取指定key 的过期时间
     * @param key
     * @return
     */
    private Long getExpire(String key){
        return redisTemplate.boundValueOps(key).getExpire();
    }

    /**
     * 是否存在key
     * @param key
     * @return
     */
    private boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 如果 key 存在则覆盖,并返回旧值.
     * 如果不存在,返回null 并添加
     * @param key
     * @param value
     * @return
     */
    private String getAndSet(String key,String value){
        return (String) redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 对一个 key-value 的值进行加减操作,
     * 如果该 key 不存在 将创建一个key 并赋值该 number
     * 如果 key 存在,但 value 不是长整型 ,将报错
     * @param key
     * @param number
     */
    private Long increment(String key,long number){
        return redisTemplate.opsForValue().increment(key, number);
    }

    /**
     * 删除 key-value
     * @param key
     * @return
     */
    private boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    private Set<String> getKeys(RedisKey redisKey) throws Exception {
        return redisTemplate.keys(redisKey.getKeyStr() + "**");
    }

    /**
     * 是否存在key
     * @param redisKey
     * @return
     */
    public boolean hasKey(RedisKey redisKey){
        return this.hasKey(redisKey.getKeyStr());
    }

    /**
     * 获取 String 类型 key-value
     * @param redisKey
     * @return
     */
    public  void get(RedisKey redisKey){
        this.get(redisKey.getKeyStr());
    }

    /**
     * 设置 String 类型 key-value 并添加过期时间 (分钟单位)
     * @param redisKey
     * @param value
     */
    public  void set(RedisKey redisKey, String value){
        if(redisKey.getExpireTime() > 0){
            this.setForTimeMS(redisKey.getKeyStr(),value,redisKey.getExpireTime());
        }else{
            this.set(redisKey.getKeyStr(),value);
        }
    }

    /**
     * 获取指定key 的过期时间
     * @param redisKey
     * @return
     */
    public Long getExpire(RedisKey redisKey){
        return this.getExpire(redisKey.getKeyStr());
    }

    /**
     * 如果 key 存在则覆盖,并返回旧值.
     * 如果不存在,返回null 并添加
     * @param redisKey
     * @param value
     * @return
     */
    public  void getAndSet(RedisKey redisKey,String value){
        this.getAndSet(redisKey.getKeyStr(),value);
    }

    /**
     * 对一个 key-value 的值进行加减操作,
     * 如果该 key 不存在 将创建一个key 并赋值该 number
     * 如果 key 存在,但 value 不是长整型 ,将报错
     * @param redisKey
     * @param number
     */
    public Long increment(RedisKey redisKey,long number){
        return this.increment(redisKey.getKeyStr(),number);
    }

    /**
     * 删除 key-value
     * @param redisKey
     * @return
     */
    public void delete(RedisKey redisKey){
        this.delete(redisKey.getKeyStr());
    }

    /**
     * 模糊删除
     */
    public void _delete(RedisKey redisKey) throws Exception {
        Set<String> set = getKeys(redisKey);
        if(!CollectionUtils.isEmpty(set)){
            for (String keyStr : set) {
                delete(keyStr);
            }
        }
    }

}
