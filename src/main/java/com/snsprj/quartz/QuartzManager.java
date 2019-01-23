package com.snsprj.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuartzManager {

    @Autowired
    private Scheduler scheduler;

    /**
     * 添加一个定时任务
     *
     * @param jobName 任务名称
     * @param jobGroupName 任务组名称
     * @param triggerName 触发器名称
     * @param triggerGroupName 触发器组名称
     * @param jobClass 任务
     * @param cron 定时任务表达式 0/1 * * * * ?
     */
    public void addJob(String jobName, String jobGroupName,
        String triggerName, String triggerGroupName, Class jobClass, String cron, JobDataMap jobDataMap) {

        try {

            JobKey jobKey = new JobKey(jobName, jobGroupName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            if (null != jobDetail) {
                // 该定时任务已存在
                log.info("====>addJob, job already exist! jobName is {}, jobGroupName is {}", jobName, jobGroupName);
                return;
            }

            // 任务名、任务组名、任务执行类
            jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName)
                .usingJobData(jobDataMap).build();

            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();

            // 触发器名、触发器组名
            triggerBuilder.withIdentity(triggerName, triggerGroupName);

            triggerBuilder.startNow();

            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));

            // 创建trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();

            // 调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(jobDetail, trigger);

            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改定时任务触发时间
     *
     * @param jobName 任务名称
     * @param jobGroupName 任务组名称
     * @param triggerName 触发器名称
     * @param triggerGroupName 触发器组名称
     * @param cron 定时任务表达式
     */
    public void modifyJobTime(String jobName,
        String jobGroupName, String triggerName, String triggerGroupName, String cron) {

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            if (trigger == null) {

                log.info("====>trigger is null, triggerName is {}, triggerGroupName is {}", triggerName,
                    triggerGroupName);
            } else {
                String oldTime = trigger.getCronExpression();

                if (!oldTime.equalsIgnoreCase(cron)) {

                    // 方式1：调用rescheduleJob -- start
                    // 触发器
                    TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();

                    // 触发器名、触发器组名
                    triggerBuilder.withIdentity(triggerName, triggerGroupName);

                    // 触发器时间设定
                    triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));

                    // 创建Trigger对象
                    trigger = (CronTrigger) triggerBuilder.build();

                    // 修改任务的触发时间
                    scheduler.rescheduleJob(triggerKey, trigger);
                    // 方式1：调用rescheduleJob -- end

                    // 方式2： 先删除，然后创建一个新的job -- start
                    JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                    Class<? extends Job> jobClass = jobDetail.getJobClass();

                    // 方式2： 先删除，然后创建一个新的job -- end
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除一个定时任务
     *
     * @param jobName 任务名称
     * @param jobGroupName 任务组名称
     * @param triggerName 触发器名称
     * @param triggerGroupName 触发器组名称
     */
    public void removeJob(String jobName, String jobGroupName,
        String triggerName, String triggerGroupName) {

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            // 停止触发器
            scheduler.pauseTrigger(triggerKey);

            // 移除触发器
            scheduler.unscheduleJob(triggerKey);

            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取任务详情
     *
     * @param triggerName 触发器名称
     * @param triggerGroupName 触发器组名称
     * @return String
     */
    public String getJobInfo(String triggerName, String triggerGroupName) {

        try {
            TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroupName);

            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            if (null == cronTrigger) {
                // 定时任务不存在
                return null;
            } else {
                return String.format("time:%s,state:%s", cronTrigger.getCronExpression(),
                    scheduler.getTriggerState(triggerKey).name());
            }

        } catch (SchedulerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 启动所有定时任务
     */
    public void startJobs() {

        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭所有定时任务
     */
    public void shutdownJobs() {

        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
