package com.snsprj.utils;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public final class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间，单位秒
     * @param key key
     * @param time 时间，秒
     * @return true/false
     */
    public boolean expire(String key, long time){

        try {
            if (time > 0){
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key获取过期时间，单位秒，返回0表示永久有效
     *
     * @param key key不可为null
     * @return 时间（秒），返回0表示永久有效
     */
    public long getExpire(String key){

        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key key
     * @return true/false
     */
    public boolean hasKey(String key){

        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入
     *
     * @param key key
     * @param value value
     * @return true/false
     */
    public boolean set(String key, Object value){

        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置过期时间（秒）
     * @param key key
     * @param value value
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true/false
     */
    public boolean set(String key, Object value, long time){

        try {
            if (time > 0){
                redisTemplate.opsForValue().set(key, value ,time, TimeUnit.SECONDS);
            }else {
                set(key, value);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key key可以传一个或多个
     */
    @SuppressWarnings("unchecked")
    public void  del(String ... key){

        if (key != null && key.length > 0){
            if (key.length == 1){
                redisTemplate.delete(key[0]);
            }else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * HashSet
     *
     * @param key key
     * @param map map
     * @return true/false
     */
    public boolean hmset(String key, Map<String, Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet并设置时间
     *
     * @param key key
     * @param map map
     * @param time 时间（秒）
     * @return true/false
     */
    public boolean hmset(String key, Map<String, Object> map, long time){

        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0){
                expire(key, time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
