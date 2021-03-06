package com.snsprj.sbsm.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestRedis {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testExpire(){

        String key = "test:set";
        long time = 300;

        redisUtil.expire(key, time);

        long expireTime = redisUtil.getExpire(key);

        log.info("====>expireTime is {}",expireTime);
    }

    /**
     * 测试get方法
     */
    @Test
    public void getTest(){

        String key = "notExistKey";
//        Object value = redisUtil.get(key);
        Integer value = (Integer) redisUtil.get(key);

        Assert.assertNull(value);
    }

    @Test
    public void setTest(){

        try {
            boolean isSuccess = redisUtil.set("name", null);

            if (isSuccess){
                log.info("====>保存成功");
            }else {
                log.info("====>保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
