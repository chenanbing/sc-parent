package com.cab.lib.redis;

import com.cab.common.base.key.ehchace.EhcacheConfig;
import com.cab.common.base.key.ehchace.EhcacheKeyEnum;
import com.cab.common.base.key.redis.RedisKey;
import com.cab.common.base.key.redis.RedisKeyEnum;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis操作基础接口实现类
 */
public class RedisUtils<K, V extends Serializable>{

    private RedisTemplate<K, V> redisTemplate;

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T extends Serializable> List<T> getList(RedisKeyEnum key, Class<T> cls) throws Exception {
        ListObj listObj = getObj(key);
        if (null != listObj && listObj instanceof ListObj){
            return listObj.getList();
        }
        return null;
    }

    public Map getMap(RedisKeyEnum key) throws Exception {
        MapObj mapObj = getObj(key);
        if (null != mapObj && mapObj instanceof MapObj){
            return mapObj.getMap();
        }
        return null;
    }

    public void setMap(RedisKeyEnum key,Map map) throws Exception {
        MapObj mapObj = new MapObj(map);
        setObj(key,mapObj);
    }

    public Set getSet(RedisKeyEnum key) throws Exception {
        SetObj setObj = getObj(key);
        if (null != setObj && setObj instanceof SetObj){
            return setObj.getSet();
        }
        return null;
    }

    public void setSet(RedisKeyEnum key,Set set) throws Exception {
        SetObj setObj = new SetObj(set);
        setObj(key,setObj);
    }

    public <T extends Serializable> void setList(RedisKeyEnum key,List<T> list) throws Exception {
        ListObj listObj = new ListObj(list);
        setObj(key,listObj);
    }

    /**
     * 对象序列化存储
     *
     * @param key
     * @param value
     * @throws Exception
     */
    public <T> void setObj(RedisKeyEnum key, T value) throws Exception {
        final String keyStr = key.getKey();
        final Long expireTime = (long)key.getExpireTime();
        final byte[] vBytes = SerializeUtil.serialize(value);
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                connection.set(keyStr.getBytes(), vBytes);
                if (expireTime >= 0) {
                    connection.expire(keyStr.getBytes(), expireTime);
                }
                return null;
            }
        });
    }

    public <T> void setObj(RedisKey key, T value) throws Exception {
        final String keyStr = key.get();
        final Long expireTime = (long)key.getExpireTime();
        final byte[] vBytes = SerializeUtil.serialize(value);
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                connection.set(keyStr.getBytes(), vBytes);
                if (expireTime >= 0) {
                    connection.expire(keyStr.getBytes(), expireTime);
                }
                return null;
            }
        });
    }


    public <T extends Serializable> T getObj(RedisKeyEnum key) throws Exception {
        final String keyStr = key.getKey();
        return redisTemplate.execute(new RedisCallback<T>() {
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyBytes = keyStr.getBytes();
                if (connection.exists(keyBytes)) {
                    byte[] valueBytes = connection.get(keyBytes);
                    T value = (T) SerializeUtil.unserialize(valueBytes);
                    return value;
                }
                return null;
            }
        });
    }

    public <T extends Serializable> T getObj(RedisKey key) throws Exception {
        final String keyStr = key.get();
        return redisTemplate.execute(new RedisCallback<T>() {
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyBytes = keyStr.getBytes();
                if (connection.exists(keyBytes)) {
                    byte[] valueBytes = connection.get(keyBytes);
                    T value = (T) SerializeUtil.unserialize(valueBytes);
                    return value;
                }
                return null;
            }
        });
    }


    public void set(RedisKeyEnum key, V value) throws Exception {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps((K) key.getKey());
        if (!(value instanceof String)) {
            value = (V) value.toString();
        }

        valueOper.set(value);
        if (key.getExpireTime() > 0) {
            valueOper.expire(key.getExpireTime(), TimeUnit.SECONDS);
        }
    }

    //RedisKeyEnum.USER_INFO.get( 3333)
    public void set(RedisKey key, V value) throws Exception {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps((K) key.get());
        if (!(value instanceof String)) {
            value = (V) value.toString();
        }

        valueOper.set(value);
        if (key.getExpireTime() > 0) {
            valueOper.expire(key.getExpireTime(), TimeUnit.SECONDS);
        }
    }


    public V get(RedisKeyEnum key) throws Exception {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps((K) key.getKey());
        return valueOper.get();
    }

    //RedisKeyEnum.USER_INFO.get( 3333)
    public V get(RedisKey key) throws Exception {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps((K) key.get());
        return valueOper.get();
    }

    public String getString(RedisKeyEnum key) throws Exception {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps((K) key.getKey());
        V val = valueOper.get();
        if (null == val){
            return null;
        }
        return (String) valueOper.get();
    }

    public Long getNumber(RedisKeyEnum key) throws Exception {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps((K) key.getKey());
        V val = valueOper.get();
        if (null == val){
            return null;
        }
        return Long.parseLong((String)valueOper.get());
    }

    private void rm(String redisKey) throws Exception {
        redisTemplate.delete((K) redisKey);
    }

    public void rm(RedisKeyEnum redisKey) throws Exception {
        redisTemplate.delete((K) redisKey.getKey());
    }

    //RedisKeyEnum.USER_INFO.get( 3333)
    public void rm(RedisKey key) throws Exception {
        redisTemplate.delete((K) key.get());
    }

    public Long getExpire(RedisKeyEnum key) throws Exception {
        return redisTemplate.getExpire((K) key.getKey());
    }

    public Map hMGet(final RedisKeyEnum key) throws Exception {
        return redisTemplate.execute(new RedisCallback<Map>() {
            public Map doInRedis(RedisConnection connection){
                Map<byte[],byte[]> byteMaps = connection.hGetAll(key.getKey().getBytes());
                if (null == byteMaps){
                    return null;
                }
                Map<Object, Object> map = new HashMap<Object, Object>();
                for (Map.Entry<byte[],byte[]> e : byteMaps.entrySet()) {
                    map.put(SerializeUtil.unserialize(e.getKey()), SerializeUtil.unserialize(e.getValue()));
                }
                return map;
            }
        });
    }

    public void hMSet(final RedisKeyEnum key, Map<Object, Object> map) throws Exception {
        final Map<byte[], byte[]> byteMaps = new HashMap<byte[], byte[]>();
        for (Map.Entry<Object, Object> e : map.entrySet()) {
            byteMaps.put(SerializeUtil.serialize(e.getKey()), SerializeUtil.serialize(e.getValue()));
        }
        redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) {
                connection.hMSet(key.getKey().getBytes(), byteMaps);
                return "";
            }
        });
    }

    public V hGet(final RedisKeyEnum key, final Object hashKey) throws Exception {
        return redisTemplate.execute(new RedisCallback<V>() {
            public V doInRedis(RedisConnection connection) {
                byte[] value = connection.hGet(key.getKey().getBytes(), SerializeUtil.serialize(hashKey));
                if (null == value){
                    return null;
                }
                V valueObj = (V) SerializeUtil.unserialize(value);
                return valueObj;
            }
        });
    }

    public boolean hSet(final RedisKeyEnum key, final Object hashKey, final Object hashValue) throws Exception {
        final Long expireTime = (long)key.getExpireTime();
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) {
                boolean value = connection.hSet(key.getKey().getBytes(), SerializeUtil.serialize(hashKey), SerializeUtil.serialize(hashValue));
                if (expireTime >= 0) {
                    connection.expire(key.getKey().getBytes(), expireTime);
                }
                return value;
            }
        });
    }

    /**
     * 模糊删除
     */
    public void _rms(RedisKeyEnum key) throws Exception {
        Set<String> set = getKeys(key);
        for (String getKey : set) {
            rm(getKey);
        }
    }

    //RedisKeyEnum.USER_INFO.get( 3333)
    public void _rms(RedisKey key) throws Exception {
        Set<String> set = getKeys(key.get() + "*");
        for (String getKey : set) {
            rm(getKey);
        }
    }



    public void _rms(String key) throws Exception{
        Set<String> set = getKeys(key + "*");
        for (String getKey : set) {
            rm(getKey);
        }
    }

    public void _rms(RedisKeyEnum key, Object... args) throws Exception {
        String keyStr = key.getKey(args);
        _rms(keyStr);
    }

    public V mGet(final RedisKeyEnum... keys) throws Exception {
        V values = redisTemplate.execute(new RedisCallback<V>() {
            public V doInRedis(RedisConnection connection)  {
                List<byte[]> list = new ArrayList<byte[]>();
                for (RedisKeyEnum key : keys) {
                    key.getKey().getBytes();
                    list.add(key.getKey().getBytes());
                }
                byte[][] byteKeys = (byte[][]) list.toArray();
                V value = (V) connection.mGet(byteKeys);
                return value;
            }
        });
        return null;
    }

    public void mSet(final Map<RedisKeyEnum, Object> data) throws Exception {
        if (data.size() <= 0) {
            return;
        }
        redisTemplate.execute(new RedisCallback<V>() {
            public V doInRedis(RedisConnection connection) {
                Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
                for (Map.Entry<RedisKeyEnum, Object> e : data.entrySet()) {
                    byte[] byteArrs = SerializeUtil.serialize(e.getValue());
                    map.put(e.getKey().getKey().getBytes(), byteArrs);
                }
                connection.mSet(map);
                return null;
            }
        });
    }

    public Long incrGetLong(final  RedisKeyEnum key) throws Exception{
        final String keyStr = key.getKey();
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyBytes = keyStr.getBytes();
                if (connection.exists(keyBytes)) {
                    Long value = connection.incr(keyBytes);
                    return value;
                }
                return null;
            }
        });
    }

    public Long decrGetLong(RedisKey key,Long step) throws Exception{
        final String keyStr = key.get();
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyBytes = keyStr.getBytes();
                if (connection.exists(keyBytes)) {
                    Long value = connection.decrBy(keyBytes,step);
                    return value;
                }
                return null;
            }
        });
    }

    public Set<String> getKeys(final RedisKeyEnum key) throws Exception {
        return getKeys(key.getKey() + "*");
    }

    public Set<String> getKeys(final String key) throws Exception {
        Set<byte[]> keys = (Set<byte[]>) redisTemplate.execute(new RedisCallback() {
            public V doInRedis(RedisConnection connection)  {
                byte[] keybytes = key.getBytes();
                V value = (V) connection.keys(keybytes);
                return value;
            }
        });

        Set<String> sets = new HashSet<String>();
        for (byte[] k : keys) {
            sets.add(new String(k));
        }
        return sets;
    }

    private boolean setIfAbsent(K valueKey, V value, long expiredTime, TimeUnit timeUnit) {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(valueKey);
        boolean isOk = valueOper.setIfAbsent(value);
        if (isOk && expiredTime > 0) {
            valueOper.expire(expiredTime, timeUnit);
        }
        return isOk;
    }

    public void tryLock(RedisKeyEnum key, int lockExpireTime, TimeUnit timeUnit) throws Exception {
        String lockKey = key.getKey();
        long timeOut = System.currentTimeMillis() + timeUnit.toMillis(lockExpireTime);
        try {
            while (true) {
                // 获取到锁
                if (setIfAbsent((K) lockKey, (V) String.valueOf(timeOut), lockExpireTime + 1, timeUnit)) {
                    return;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    return;
                }
                if (System.currentTimeMillis() > timeOut) {
                    unlock(key);
                    break;
                }
            }
        } catch (Exception e) {
            unlock(key);
            return;
        }
        return;
    }

    public void unlock(RedisKeyEnum key) {
        String lockKey = key.getKey() + ".LOCK";
        try {
            rm(lockKey);
        } catch (Exception e) {
            try {
                rm(lockKey);
            } catch (Exception e1) {
            }
        } finally {
            try {
                rm(lockKey);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 删除本地ehcacheKey
     * @param ehcacheConfig
     * @param message
     * @throws Exception
     */
    public void pubEhCache(EhcacheConfig ehcacheConfig, String message) throws Exception {
        redisTemplate.convertAndSend(ehcacheConfig.getCacheKey(), message);
    }

    public void updateEhCache(EhcacheConfig ehcacheConfig, EhcacheKeyEnum ehcacheKey, Object ... args) throws Exception{
        pubEhCache(ehcacheConfig,ehcacheKey.getKey(args));
    }

	public <T extends Serializable> void setList(RedisKey key, List<T> list) throws Exception {
		ListObj listObj = new ListObj(list);
        setObj(key,listObj);
	}

	public <T extends Serializable> List<T> getList(RedisKey key, Class<T> cls) throws Exception {
		ListObj listObj = getObj(key);
        if (null != listObj && listObj instanceof ListObj){
            return listObj.getList();
        }
        return null;
	}



}
