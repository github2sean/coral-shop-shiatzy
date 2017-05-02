package com.dookay.coral.common.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.dookay.coral.common.utils.ProtoStuffSerializerUtil;

import java.util.List;
import java.util.Set;


/**
 * redis缓存
 * @author Luxor
 * @version v0.0.1
 * @since 2016/11/13
 */
@Component
public class RedisCache {

    public final static String CAHCENAME="cache";//缓存名
    public final static int CAHCETIME=60;//默认缓存时间

    @Autowired
	private RedisTemplate<String, String> redisTemplate;

    /**
     * 存入单个对象
     * @param key
     * @param obj
     * @return
     * @since : 2016年11月17日
     * @author : kezhan
     */
    public <T> boolean putCache(String key, T obj) {
        final byte[] bkey = key.getBytes();
        final byte[] bvalue = ProtoStuffSerializerUtil.serialize(obj);
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setNX(bkey, bvalue);
            }
        });
        return result;
    }

    /**
     * 存入单个对象并设定 有效时间
     * @param key
     * @param obj
     * @param expireTime
     * @since : 2016年11月17日
     * @author : kezhan
     */
    public <T> void putCacheWithExpireTime(String key, T obj, final long expireTime) {
        final byte[] bkey = key.getBytes();
        final byte[] bvalue = ProtoStuffSerializerUtil.serialize(obj);
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(bkey, expireTime, bvalue);
                return true;
            }
        });
    }

    /**
     * 存入一个集合
     * @param key
     * @param objList
     * @return
     * @since : 2016年11月17日
     * @author : kezhan
     */
    public <T> boolean putListCache(String key, List<T> objList) {
        final byte[] bkey = key.getBytes();
        final byte[] bvalue = ProtoStuffSerializerUtil.serializeList(objList);
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setNX(bkey, bvalue);
            }
        });
        return result;
    }
    
    /**
     * 存入一个集合并设定 有效时间
     * @param key
     * @param objList
     * @param expireTime
     * @return
     * @since : 2016年11月17日
     * @author : kezhan
     */
    public <T> boolean putListCacheWithExpireTime(String key, List<T> objList, final long expireTime) {
        final byte[] bkey = key.getBytes();
        final byte[] bvalue = ProtoStuffSerializerUtil.serializeList(objList);
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(bkey, expireTime, bvalue);
                return true;
            }
        });
        return result;
    }

    /**
     * 获取单个对象
     * @param key
     * @param targetClass
     * @return
     * @since : 2016年11月17日
     * @author : kezhan
     */
    public <T> T getCache(final String key, Class<T> targetClass) {
        byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(key.getBytes());
            }
        });
        if (result == null) {
            return null;
        }
        return ProtoStuffSerializerUtil.deserialize(result, targetClass);
    }
    
    /**
     * 获取一个集合
     * @param key
     * @param targetClass
     * @return
     * @since : 2016年11月17日
     * @author : kezhan
     */
    public <T> List<T> getListCache(final String key, Class<T> targetClass) {
        byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(key.getBytes());
            }
        });
        if (result == null) {
            return null;
        }
        return ProtoStuffSerializerUtil.deserializeList(result, targetClass);
    }

    /**
     * 精确删除key
     * @param key
     * @since : 2016年11月17日
     * @author : kezhan
     */
    public void deleteCache(String key) {
        redisTemplate.delete(key);
    }

    
    /**
     * 模糊删除key
     * @param pattern
     * @since : 2016年11月17日
     * @author : kezhan
     */
    public void deleteCacheWithPattern(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

    
    /**
     * 清空所有缓存
     * 
     * @since : 2016年11月17日
     * @author : kezhan
     */
    public void clearCache() {
        deleteCacheWithPattern(RedisCache.CAHCENAME+"|*");
    }

}

