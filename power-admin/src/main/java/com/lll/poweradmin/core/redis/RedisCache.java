package com.lll.poweradmin.core.redis;

import com.alibaba.fastjson2.JSON;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 */
@Component
public class RedisCache {
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    public void setStringCache(String key, Object value, Integer timeout, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, JSON.toJSONString(value),timeout,timeUnit);
    }

    public String getStringCache(String key){
        return redisTemplate.opsForValue().get(key);
    }


    public void delStringCache(String key){
        redisTemplate.delete(key);
    }
}
