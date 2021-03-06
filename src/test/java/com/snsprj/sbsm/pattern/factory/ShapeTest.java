package com.snsprj.sbsm.pattern.factory;

import com.snsprj.sbsm.pattern.factory.impl.RectangleFactory;

/**
 * @author SKH
 * @date 2018-09-07 11:23
 **/
public class ShapeTest {

    public static void main(String[] args) {

        ShapeFactory shapeFactory = new RectangleFactory();
        Shape shape = shapeFactory.getShape();

        shape.draw();
    }
}
