package com.snsprj.sbsm.quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzTest {

    public static void main(String[] args) {

        try {

            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // 定义Trigger
            Trigger trigger = newTrigger().withIdentity("triggerName", "triggerGroup")
                    .startNow()
                    .withSchedule(simpleSchedule().withIntervalInSeconds(1).repeatForever())
                    .build();

            // 定义一个jobDetail
            JobDetail job = newJob(HelloQuartz.class)
                    .withIdentity("jobName", "groupName")
                    .usingJobData("name", "quartz")
                    .build();

            scheduler.scheduleJob(job, trigger);

            scheduler.start();

            //运行一段时间后关闭
            Thread.sleep(10000);
            scheduler.shutdown(true);

        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }
}
