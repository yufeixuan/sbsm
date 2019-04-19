package com.snsprj.sbsm.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadTest {

    public static void main(String[] args) {

//        MyThread myThread = new MyThread();
//        myThread.start();

//        Runnable myThead2 = new MyThread2();
//        Thread thread2 = new Thread(myThead2);
//        thread2.start();

//        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MICROSECONDS,
//            new ArrayBlockingQueue<>(5));

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++){
            MyThread2 myThread2 = new MyThread2(i);
            executor.execute(myThread2);
            System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
                executor.getQueue().size()+"，已执行完毕的任务数目："+executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }
}
