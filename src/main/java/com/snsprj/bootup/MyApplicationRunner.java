package com.snsprj.bootup;

import com.snsprj.mapper.QuartzJobMapper;
import com.snsprj.model.QuartzJob;
import com.snsprj.model.QuartzJobExample;
import com.snsprj.quartz.QuartzManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 伴随项目启动--需要执行的代码
 */
@Component
@Slf4j
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private QuartzJobMapper quartzJobMapper;

    @Autowired
    private QuartzManager quartzManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 从数据库中查询是否有定时任务
        QuartzJobExample quartzJobExample = new QuartzJobExample();
        quartzJobExample.createCriteria().andIdGreaterThan(0);

        List<QuartzJob> quartzJobList = quartzJobMapper.selectByExample(quartzJobExample);

        String jobName = null;
        String jobGroupName = null;
        String triggerName = null;
        String triggerGroupName = null;
        String referenceClass = null;
        String cronExpression = null;

        for (QuartzJob quartzJob : quartzJobList) {

            jobName = quartzJob.getJobName();
            jobGroupName = quartzJob.getJobGroupName();
            triggerName = jobName;
            referenceClass = quartzJob.getQuartzClass();
            cronExpression = quartzJob.getCronExpression();

            Class jobClass = Class.forName(referenceClass);

            quartzManager.addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cronExpression);

            log.info("====>add job, jobName is {}", jobName);
        }
    }
}
