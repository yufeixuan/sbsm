package com.snsprj.sbsm.pattern.singleton;

/**
 * 枚举enum和静态代码块的特性相似，在使用枚举时，构造方法会被自动调用，利用这一特性也可以实现单例
 */
public class EnumSingleton {

    public enum MyEnumSingleton{

        singletonFactory;

        private MySingleton instance;

        //枚举类的构造方法在类加载是被实例化
        MyEnumSingleton(){
            instance = new MySingleton();
        }

        public MySingleton getInstance(){
            return instance;
        }
    }

    public static MySingleton getInstance(){
        return MyEnumSingleton.singletonFactory.getInstance();
    }
}

class MySingleton{
    public MySingleton(){}
}