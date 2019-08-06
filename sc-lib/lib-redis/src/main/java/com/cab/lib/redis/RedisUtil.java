package com.cab.lib.redis;

import com.cab.lib.redis.obj.ListObj;
import com.cab.lib.redis.obj.MapObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作基础接口实现类
 */
public class RedisUtil{

    private final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

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


    /**
     * 尝试获取分布式锁
     * @param key 锁
     * @param requestId 请求标识  String requestId = UUID.randomUUID().toString();
     * @param expire 超期时间(单位是秒)
     * @return 是否获取成功
     */
    private boolean setLock(String key, String requestId, int expire) {
        return (Boolean) redisTemplate.execute((RedisCallback) connection -> {
            Boolean result = connection.set(key.getBytes(), requestId.getBytes(), Expiration.from(expire, TimeUnit.SECONDS), RedisStringCommands.SetOption.SET_IF_ABSENT);
            return result;
        });
//        try {
//            RedisCallback<String> callback = (connection) -> {
//                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
//                return commands.set(key, requestId, "NX", "EX", expire);// EX = seconds; PX = milliseconds
//            };
//            String result = redisTemplate.execute(callback);
//            return !StringUtils.isEmpty(result);
//        } catch (Exception e) {
//            logger.error("set redis occured an exception",e);
//        }
//        return false;
    }

    /**
     * 尝试分布式锁解锁
     * @param key 锁
     * @param requestId 请求标识  String requestId = UUID.randomUUID().toString();
     * @return 是否成功
     */
    private boolean releaseLock(String key,String requestId) {

        return (Boolean) redisTemplate.execute((RedisCallback) connection -> {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Boolean result = connection.eval(script.getBytes(), ReturnType.BOOLEAN, 1, key.getBytes(), requestId.getBytes());
            return result;
        });

//        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
//        try {
//            List<String> keys = new ArrayList<>();
//            keys.add(key);
//            List<String> args = new ArrayList<>();
//            args.add(requestId);
//
//            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
//            // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
//            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
//            RedisCallback<Long> callback = (connection) -> {
//                Object nativeConnection = connection.getNativeConnection();
//                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
//                if (nativeConnection instanceof JedisCluster) {// 集群模式
//                    return (Long) ((JedisCluster) nativeConnection).eval(script, keys, args);
//                }else if (nativeConnection instanceof Jedis) {// 单机模式
//                    return (Long) ((Jedis) nativeConnection).eval(script, keys, args);
//                }
//                return 0L;
//            };
//            Long result = redisTemplate.execute(callback);
//
//            return result != null && result > 0;
//        } catch (Exception e) {
//            logger.error("release lock occured an exception", e);
//        } finally {
//            // 清除掉ThreadLocal中的数据，避免内存溢出
//            //lockFlag.remove();
//        }
//        return false;
    }

    /**
     * 获取分布式锁
     * @param key
     * @param requestId String requestId = UUID.randomUUID().toString();
     */
    public void tryLock(RedisKeyEnum key,String requestId)  {
        String lockKey = key.getKey();
        int lockExpireTime = key.getExpireTime();
        try {
            while (true) {
                // 获取到锁
                if(setLock(lockKey,requestId,lockExpireTime)){
                    return;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    return;
                }
            }
        } catch (Exception e) {
            releaseLock(lockKey,requestId);
            return;
        }
    }






    //不常用----------------------------------------------------------------------------start
    public <T extends Serializable> List<T> getList(RedisKey redisKey) throws Exception {
        ListObj listObj = getObj(redisKey);
        if (null != listObj && listObj instanceof ListObj){
            return listObj.getList();
        }
        return null;
    }

    public <T extends Serializable> void setList(RedisKey redisKey,List<T> list) throws Exception {
        ListObj listObj = new ListObj(list);
        setObj(redisKey,listObj);
    }


    public Map getMap(RedisKey redisKey) throws Exception {
        MapObj mapObj = getObj(redisKey);
        if (null != mapObj && mapObj instanceof MapObj){
            return mapObj.getMap();
        }
        return null;
    }


    public void setMap(RedisKey redisKey,Map map) throws Exception {
        MapObj mapObj = new MapObj(map);
        setObj(redisKey,mapObj);
    }

    /**
     * 对象序列化存储
     *
     * @param redisKey
     * @param value
     * @throws Exception
     */
    public <T> void setObj(RedisKey redisKey, T value) throws Exception {
        byte[] keyBytes = redisTemplate.getStringSerializer().serialize(redisKey.getKeyStr());
        byte[] vBytes = SerializeUtil.serialize(value);
        Long expireTime = (long)redisKey.getExpireTime();
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) {
                connection.set(keyBytes, vBytes);
                if (expireTime >= 0) {
                    connection.expire(keyBytes, expireTime);
                }
                return null;
            }
        });
    }

    public <T extends Serializable> T getObj(RedisKey redisKey) throws Exception {
        byte[] keyBytes = redisTemplate.getStringSerializer().serialize(redisKey.getKeyStr());
        return redisTemplate.execute(new RedisCallback<T>() {
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                if (connection.exists(keyBytes)) {
                    byte[] valueBytes = connection.get(keyBytes);
                    T value = (T) SerializeUtil.unserialize(valueBytes);
                    return value;
                }
                return null;
            }
        });
    }
    //不常用----------------------------------------------------------------------------end


}
