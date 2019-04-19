package com.snsprj.sbsm.threadpool;

public class MyThread2 implements Runnable {

    private int taskNum;

    public MyThread2(int taskNum){
        this.taskNum = taskNum;
    }

    @Override
    public void run() {
        System.out.println("implement Runnable...");
        System.out.println("正在执行task" + taskNum);

        try {
            Thread.currentThread().sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task "+taskNum+"执行完毕");
    }
}
