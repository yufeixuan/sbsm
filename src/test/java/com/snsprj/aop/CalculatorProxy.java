package com.snsprj.aop;

/**
 * 设计模式，代理模式实现AOP
 */
public class CalculatorProxy implements Calculator {

    private Calculator calculator;

    public CalculatorProxy(Calculator calculator){

        this.calculator = calculator;
    }

    @Override
    public int calculate(int a, int b) {

        if (b == 0){
            throw new RuntimeException();
        }
        return calculator.calculate(a, b);
    }
}
