package com.snsprj.pattern.factory.impl;

import com.snsprj.pattern.factory.Shape;

/**
 * @author SKH
 * @date 2018-09-07 10:46
 **/
public class Rectangle implements Shape {

    @Override
    public void draw() {
        System.out.println("draw rectangle");
    }
}
