package com.snsprj.sbsm.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


@Slf4j
public class Job2 implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {


        log.info("====>定时任务2 执行");
    }
}
