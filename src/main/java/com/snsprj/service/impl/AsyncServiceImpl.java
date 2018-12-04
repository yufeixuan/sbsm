package com.snsprj.service.impl;

import com.snsprj.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author SKH
 * @date 2018-09-04 16:47
 **/
@Service
public class AsyncServiceImpl implements AsyncService{

    private Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);

    /**
     * 执行异步任务
     */
    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {

        logger.info("====>start executeAsync");
        try{
            Thread.sleep(5000);
        }catch(Exception e){
            e.printStackTrace();
        }
        logger.info("====>end executeAsync");
    }
}
