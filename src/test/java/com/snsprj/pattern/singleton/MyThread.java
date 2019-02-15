package com.snsprj.pattern.singleton;

public class MyThread extends Thread {

    @Override
    public void run() {
//        System.out.println(HungrySingleton.getInstance().hashCode());

//        System.out.println(LazySingleton.getInstance().hashCode());

        System.out.println(InnerClassSingleton.getInstance().hashCode());
    }

    public static void main(String[] args) {
        MyThread[] threads = new MyThread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MyThread();
        }

        for (MyThread thread : threads) {
            thread.start();
        }
    }
}
