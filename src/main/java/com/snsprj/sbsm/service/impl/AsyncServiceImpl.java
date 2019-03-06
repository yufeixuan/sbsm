package com.snsprj.sbsm.service.impl;

import com.snsprj.sbsm.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author SKH
 * @date 2018-09-04 16:47
 **/
@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService{

    /**
     * 执行异步任务
     */
    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {

        log.info("====>start executeAsync");
        try{
            Thread.sleep(5000);
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("====>end executeAsync");
    }
}
