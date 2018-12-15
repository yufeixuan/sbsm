package com.snsprj.service.impl;

import com.snsprj.service.TestService;
import com.snsprj.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用于测试
 */
@Service
@Transactional
@Slf4j
public class TestServiceImpl implements TestService {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 测试使用redis作为计数器实现秒杀功能，并发线程安全
     */
    @Override
    public void testRedis() {

        log.info("====>接收到请求");
        // 库存
        Integer stock = 10;

        String key = "stock";

        Integer currentStock = (Integer) redisUtil.get(key);

        if ( currentStock == null){
            redisUtil.setNX(key, stock);
        }else {

            if (currentStock > 0){
                long currentStockByDecrease = redisUtil.decr(key, 1);
                if (currentStockByDecrease < 0) {
                    // 减库存超限
                    log.info("====>减库存超限！");
                }else {
                    log.info("====>正常减库存，当前库存为{}", currentStockByDecrease);
                }
            }
        }
    }
}
