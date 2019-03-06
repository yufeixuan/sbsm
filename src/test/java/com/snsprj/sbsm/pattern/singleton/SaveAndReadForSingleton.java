package com.snsprj.sbsm.pattern.singleton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import lombok.extern.slf4j.Slf4j;

/**
 * 序列化反序列化单例
 */
@Slf4j
public class SaveAndReadForSingleton {

    public static void main(String[] args) {

        InnerClassSingleton singleton = InnerClassSingleton.getInstance();

        File file = new File("mySingleton.txt");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(singleton);

            fileOutputStream.close();
            objectOutputStream.close();

            log.info("====>序列化前：{}",singleton.hashCode());

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            InnerClassSingleton rSingleton = (InnerClassSingleton) objectInputStream.readObject();

            fileInputStream.close();
            objectInputStream.close();

            log.info("====>序列化后：{}", rSingleton.hashCode());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
