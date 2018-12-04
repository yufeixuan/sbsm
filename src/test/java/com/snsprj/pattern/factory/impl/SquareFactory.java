package com.snsprj.pattern.factory.impl;

import com.snsprj.pattern.factory.Shape;
import com.snsprj.pattern.factory.ShapeFactory;

/**
 * @author SKH
 * @date 2018-09-07 11:22
 **/
public class SquareFactory implements ShapeFactory {

    @Override
    public Shape getShape() {
        return new Square();
    }
}
