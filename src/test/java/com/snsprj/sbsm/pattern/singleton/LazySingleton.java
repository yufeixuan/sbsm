package com.snsprj.sbsm.pattern.singleton;

/**
 * 单例模式--懒汉模式
 */
public class LazySingleton {

    volatile private static LazySingleton instance = null;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {

        try {
            if (instance == null) {
                Thread.sleep(300);
                synchronized (LazySingleton.class) {
                    if (instance == null) {
                        instance = new LazySingleton();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return instance;
    }
}
