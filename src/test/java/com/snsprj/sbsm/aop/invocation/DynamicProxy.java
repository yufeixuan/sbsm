package com.snsprj.sbsm.aop.invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import lombok.extern.slf4j.Slf4j;

/**
 * 1、Java类加载器
 *
 */
@Slf4j
public class DynamicProxy implements InvocationHandler {

    /**
     * 需要代理的目标类
     */
    private Object targetObject;

    public Object bind(Object targetObject){

        this.targetObject = targetObject;
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
          targetObject.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object result;

        log.info("====>方法執行之前");
        result = method.invoke(targetObject,args);
        log.info("====>result is {}", result);
        log.info("====>方法執行之后");

        return result;
    }
}
