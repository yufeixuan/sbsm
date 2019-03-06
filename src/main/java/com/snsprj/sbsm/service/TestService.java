package com.snsprj.sbsm.service;

public interface TestService {

    /**
     * 测试使用redis作为计数器的功能，并发线程安全
     */
    void testRedis();
}
