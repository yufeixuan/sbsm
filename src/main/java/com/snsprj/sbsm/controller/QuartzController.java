package com.snsprj.sbsm.controller;

import com.snsprj.sbsm.common.ServerResponse;
import com.snsprj.sbsm.quartz.QuartzManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 定时任务类
 */
@Slf4j
@Controller
@RequestMapping("/quartz")
public class QuartzController {

    @Autowired
    private QuartzManager quartzManager;

    /**
     * 添加定时任务
     *
     * @param jobName 任务名称
     * @param jobGroupName 任务组名称
     * @param jobClass job类路径
     * @param cronExpression 定时任务时间表达式
     * @throws ClassNotFoundException ClassNotFoundException
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse addJob(@RequestParam("jobName") String jobName,
        @RequestParam("jobGroupName") String jobGroupName,
        @RequestParam("jobClass") String jobClass,
        @RequestParam("cronExpression") String cronExpression) throws ClassNotFoundException {

        String jobInfo = quartzManager.getJobInfo(jobName, null);

        if (StringUtils.isNotBlank(jobInfo)){
            // 任务已存在
            log.info("====>addJob, job already exist! jobName is {}", jobName);
            return ServerResponse.createBySuccess();
        }
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("name", "xiaohb");
        Class jobClazz = Class.forName(jobClass);
        quartzManager.addJob(jobName, jobGroupName, jobName, null, jobClazz, cronExpression, jobDataMap);
        return ServerResponse.createBySuccess();
    }


    /**
     * 删除定时任务
     *
     * @param jobName 任务名称
     * @param jobGroupName 任务组名称
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse delJob(@RequestParam("jobName") String jobName,
        @RequestParam("jobGroupName") String jobGroupName) {

        quartzManager.removeJob(jobName, jobGroupName, jobName, null);
        return ServerResponse.createBySuccess();
    }

    /**
     * 修改定时任务的触发时间
     *
     * @param jobName 任务名称
     * @param jobGroupName 任务组名称
     * @param cronExpression 定时任务时间表达式
     */
    @RequestMapping("/update")
    @ResponseBody
    public ServerResponse updateJob(@RequestParam("jobName") String jobName,
        @RequestParam("jobGroupName") String jobGroupName,
        @RequestParam("cronExpression") String cronExpression) {

        quartzManager.modifyJobTime(jobName, jobGroupName, jobName, null, cronExpression);
        return ServerResponse.createBySuccess();
    }
}
