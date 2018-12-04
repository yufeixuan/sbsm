package com.snsprj.callback;

/**
 * 打印机
 *
 * @author SKH
 * @date 2018-10-26 10:04
 **/
public class Printer {

    public void print(Callback callback, String content){

        System.out.println("====>正在打印。。。");

        try {

            Thread.sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        callback.printFinished("打印完成");
    }
}
