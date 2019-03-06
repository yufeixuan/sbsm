package com.snsprj.sbsm.aop;

import org.junit.Test;

public class CalculatorTest {

    @Test
    public void testCalculate(){

        int a = 10;
        int b = 0;

        Calculator calculator = new CalculatorImpl();

        CalculatorProxy proxy = new CalculatorProxy(calculator);
        proxy.calculate(a, b);
    }
}
