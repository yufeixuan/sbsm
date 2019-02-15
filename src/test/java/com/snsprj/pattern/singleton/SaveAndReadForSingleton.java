package com.snsprj.pattern.singleton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 序列化反序列化单例
 */
public class SaveAndReadForSingleton {

    public static void main(String[] args) {

        InnerClassSingleton singleton = InnerClassSingleton.getInstance();

        File file = new File("mySingleton.txt");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.write(singleton);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
