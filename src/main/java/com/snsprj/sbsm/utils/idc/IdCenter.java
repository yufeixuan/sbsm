package com.snsprj.sbsm.utils.idc;

public class IdCenter {

    public static long getId(){
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        return idWorker.nextId();
    }
}
