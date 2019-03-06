package com.snsprj.sbsm.callback;

import lombok.extern.slf4j.Slf4j;

/**
 * @author SKH
 * @date 2018-10-26 10:53
 **/
@Slf4j
public class CallbackTest {

    public static void main(String[] args) {
        People people = new People();

        Callback callback = new Callback() {
            @Override
            public void printFinished(String msg) {
                log.info("====>打印机的反馈是：{}", msg);
            }
        };

        String content = "打印合同";

        people.doPrint(callback, content);

        log.info("====>等待打印机反馈。。。");
    }
}
