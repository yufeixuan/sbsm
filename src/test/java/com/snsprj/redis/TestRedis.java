package com.snsprj.redis;

import com.snsprj.utils.RedisUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

    private Logger logger  = LoggerFactory.getLogger(TestRedis.class);

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testExpire(){

        String key = "test:set";
        long time = 300;

        redisUtil.expire(key, time);

        long expireTime = redisUtil.getExpire(key);

        logger.info("====>expireTime is {}",expireTime);

    }
}
