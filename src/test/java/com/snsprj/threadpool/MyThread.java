package com.snsprj.threadpool;

public class MyThread extends Thread {

    private static int num = 0;

    public MyThread(){
        num ++;
    }
    @Override
    public void run() {
        System.out.println("主动创建第个" + num + "线程");
    }
}
