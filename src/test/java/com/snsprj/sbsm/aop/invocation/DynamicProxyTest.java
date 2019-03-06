package com.snsprj.sbsm.aop.invocation;

import com.snsprj.sbsm.aop.Calculator;
import com.snsprj.sbsm.aop.CalculatorImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * java動態代理实现AOP
 */
@Slf4j
public class DynamicProxyTest {

    @Test
    public void test(){

        int a = 10;
        int b = 2;

        Calculator calculator = (Calculator) new DynamicProxy().bind(new CalculatorImpl());
        calculator.calculate(a, b);
    }
}
