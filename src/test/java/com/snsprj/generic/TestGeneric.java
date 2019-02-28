package com.snsprj.generic;

import com.snsprj.model.User;

/**
 * 测试泛型类
 *
 * @author SKH
 * @date 2018-09-17 10:27
 **/
public class TestGeneric {

    /**
     * 泛型通配符，此处’？’是类型实参，而不是类型形参。
     * @param obj
     */
    public  void  showKeyValue(Generic<?> obj){

        System.out.println(obj.getKey());
    }


    public static void test(Class<?> instance){

        System.out.println(instance.getName());
    }

    public static void main(String[] args) throws ClassNotFoundException {

        Class<User> stringClass = User.class;
        test(stringClass);
    }
}
