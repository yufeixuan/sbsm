package com.snsprj.sbsm.callback;

import lombok.extern.slf4j.Slf4j;

/**
 * 打印机
 *
 * @author SKH
 * @date 2018-10-26 10:04
 **/
@Slf4j
public class Printer {

    public void print(Callback callback, String content){

        log.info("====>正在打印。。。");

        try {

            Thread.sleep(3000);
            log.info("====>打印的内容为：{}", content);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        callback.printFinished("打印完成");
    }
}
