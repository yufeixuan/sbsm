package com.snsprj.pattern.adapter;

/**
 * 适配器类
 *
 * @author SKH
 * @date 2018-09-17 18:33
 **/
public class Adapter implements Target {

    /**
     * 持有需要被适配的接口对象
     */
    private Adaptee adaptee;

    /**
     * 构造方法，传入需要被适配的对象
     * @param adaptee adaptee
     */
    public Adapter(Adaptee adaptee){
        this.adaptee = adaptee;
    }

    @Override
    public void request() {
        adaptee.specificRequest();
    }
}
