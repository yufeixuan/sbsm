package com.snsprj.pattern.singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;

/**
 * 单例模式--静态内部类实现单例模式
 */
@Slf4j
public class InnerClassSingleton implements Serializable {


    private static final long serialVersionUID = 1L;

    private static class SingletonHandler {
        private static InnerClassSingleton instance = new InnerClassSingleton();
    }

    private InnerClassSingleton(){}

    public static InnerClassSingleton getInstance(){
        return SingletonHandler.instance;
    }

    //该方法在反序列化时会被调用，该方法不是接口定义的方法，有点儿约定俗成的感觉
    protected Object readResolve() throws ObjectStreamException {
        log.info("====>调用了readResolve方法！");
        return SingletonHandler.instance;
    }

}
