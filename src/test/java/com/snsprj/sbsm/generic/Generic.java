package com.snsprj.sbsm.generic;

/**
 * 泛型类
 *
 * @author SKH
 * @date 2018-09-17 10:22
 **/
public class Generic<T> {

    private T key;

    public Generic(T key){
        this.key = key;
    }

    public T getKey(){
        return key;
    }
}
