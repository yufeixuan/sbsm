package com.snsprj.callback;

/**
 * @author SKH
 * @date 2018-10-26 10:53
 **/
public class Main {

    public static void main(String[] args) {
        People people = new People();

        Callback callback = new Callback() {
            @Override
            public void printFinished(String msg) {
                System.out.println("====>打印机的反馈是：" + msg);
            }
        };

        String content = "打印合同";

        people.doPrint(callback, content);

        System.out.println("====>等待打印机反馈。。。");
    }
}
