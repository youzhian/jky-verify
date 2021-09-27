package com.jky.verify.modules.quartz;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * quartz Job执行器
 * @author youzhian
 */
@Component
public class CronSchedulerJob {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private void scheduleJob1(Scheduler scheduler) throws SchedulerException {

        JobDetail jobDetail = JobBuilder.newJob(ScheduledJob.class).withIdentity("job1group1").build();
        // 6的倍数秒执行
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(" 0/6 * * * * ?");

        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1","group1")
                .usingJobData("name","王智1").withSchedule(scheduleBuilder).build();

        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    private void scheduleJob2(Scheduler scheduler) throws SchedulerException {

        JobDetail jobDetail = JobBuilder.newJob(ScheduledJob.class).withIdentity("job2group2").build();
        // 6的倍数秒执行
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(" 0/12 * * * * ?");

        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger2","group2")
                .usingJobData("name","王智2").withSchedule(scheduleBuilder).build();

        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    /**
     * 同时启动两个定时任务
     * @throws SchedulerException
     */
    public void scheduleJobs() throws SchedulerException{
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduleJob1(scheduler);
        scheduleJob2(scheduler);
    }
}
