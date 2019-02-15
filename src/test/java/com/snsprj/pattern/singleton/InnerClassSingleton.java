package com.snsprj.pattern.singleton;

import java.io.Serializable;

/**
 * 单例模式--静态内部类实现单例模式
 */
public class InnerClassSingleton implements Serializable {


    private static final long serialVersionUID = 1L;

    private static class SingletonHandler {
        private static InnerClassSingleton instance = new InnerClassSingleton();
    }

    private InnerClassSingleton(){}

    public static InnerClassSingleton getInstance(){
        return SingletonHandler.instance;
    }
}
