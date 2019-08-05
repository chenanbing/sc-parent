package com.cab.lib.redis;

import com.cab.lib.redis.obj.ListObj;
import com.cab.lib.redis.obj.MapObj;
import com.cab.lib.redis.obj.SetObj;
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



    public <T extends Serializable> List<T> getList(RedisKey redisKey, Class<T> cls) throws Exception {
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

    public Set getSet(RedisKey redisKey) throws Exception {
        SetObj setObj = getObj(redisKey);
        if (null != setObj && setObj instanceof SetObj){
            return setObj.getSet();
        }
        return null;
    }

    public void setSet(RedisKey redisKey,Set set) throws Exception {
        SetObj setObj = new SetObj(set);
        setObj(redisKey,setObj);
    }

    /**
     * 对象序列化存储
     *
     * @param redisKey
     * @param value
     * @throws Exception
     */
    public <T> void setObj(RedisKey redisKey, T value) throws Exception {
        final String keyStr = redisKey.getKeyStr();
        final Long expireTime = (long)redisKey.getExpireTime();
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

    public <T extends Serializable> T getObj(RedisKey redisKey) throws Exception {
        final String keyStr = redisKey.getKeyStr();
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

    public void set(RedisKey redisKey, V value) throws Exception {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps((K) redisKey.getKeyStr());
        if (!(value instanceof String)) {
            value = (V) value.toString();
        }

        valueOper.set(value);
        if (redisKey.getExpireTime() > 0) {
            valueOper.expire(redisKey.getExpireTime(), TimeUnit.SECONDS);
        }
    }

    public V get(RedisKey redisKey) throws Exception {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps((K) redisKey.getKeyStr());
        return valueOper.get();
    }

    public String getString(RedisKey redisKey) throws Exception {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps((K) redisKey.getKeyStr());
        V val = valueOper.get();
        if (null == val){
            return null;
        }
        return (String) valueOper.get();
    }

    public Long getNumber(RedisKey redisKey) throws Exception {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps((K) redisKey.getKeyStr());
        V val = valueOper.get();
        if (null == val){
            return null;
        }
        return Long.parseLong((String)valueOper.get());
    }

    private void rm(String keyStr) throws Exception {
        redisTemplate.delete((K) keyStr);
    }

    public void rm(RedisKey redisKey) throws Exception {
        redisTemplate.delete((K) redisKey.getKeyStr());
    }

    public Long getExpire(RedisKey redisKey) throws Exception {
        return redisTemplate.getExpire((K) redisKey.getKeyStr());
    }

    public Map hMGet(final RedisKey redisKey) throws Exception {
        return redisTemplate.execute(new RedisCallback<Map>() {
            public Map doInRedis(RedisConnection connection){
                Map<byte[],byte[]> byteMaps = connection.hGetAll(redisKey.getKeyStr().getBytes());
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

    public void hMSet(final RedisKey redisKey, Map<Object, Object> map) throws Exception {
        final Map<byte[], byte[]> byteMaps = new HashMap<byte[], byte[]>();
        for (Map.Entry<Object, Object> e : map.entrySet()) {
            byteMaps.put(SerializeUtil.serialize(e.getKey()), SerializeUtil.serialize(e.getValue()));
        }
        redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) {
                connection.hMSet(redisKey.getKeyStr().getBytes(), byteMaps);
                return "";
            }
        });
    }

    public V hGet(final RedisKey redisKey, final Object hashKey) throws Exception {
        return redisTemplate.execute(new RedisCallback<V>() {
            public V doInRedis(RedisConnection connection) {
                byte[] value = connection.hGet(redisKey.getKeyStr().getBytes(), SerializeUtil.serialize(hashKey));
                if (null == value){
                    return null;
                }
                V valueObj = (V) SerializeUtil.unserialize(value);
                return valueObj;
            }
        });
    }

    public boolean hSet(final RedisKey redisKey, final Object hashKey, final Object hashValue) throws Exception {
        final Long expireTime = (long)redisKey.getExpireTime();
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) {
                boolean value = connection.hSet(redisKey.getKeyStr().getBytes(), SerializeUtil.serialize(hashKey), SerializeUtil.serialize(hashValue));
                if (expireTime >= 0) {
                    connection.expire(redisKey.getKeyStr().getBytes(), expireTime);
                }
                return value;
            }
        });
    }

    /**
     * 模糊删除
     */
    public void _rms(RedisKey redisKey) throws Exception {
        Set<String> set = getKeys(redisKey.getKeyStr() + "*");
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


    public Set<String> getKeys(final RedisKey redisKey) throws Exception {
        return getKeys(redisKey.getKeyStr() + "*");
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

    public Long incrGetLong(final  RedisKey redisKey) throws Exception{
        final String keyStr = redisKey.getKeyStr();
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
        final String keyStr = key.getKeyStr();
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

    private boolean setIfAbsent(K valueKey, V value, long expiredTime, TimeUnit timeUnit) {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(valueKey);
        boolean isOk = valueOper.setIfAbsent(value);
        if (isOk && expiredTime > 0) {
            valueOper.expire(expiredTime, timeUnit);
        }
        return isOk;
    }


}
