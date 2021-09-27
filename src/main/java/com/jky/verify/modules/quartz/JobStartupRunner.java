package com.jky.verify.modules.quartz;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 项目启动时启动定时任务
 * @author youzhian
 */
@Component
public class JobStartupRunner implements CommandLineRunner {

    @Resource
    CronSchedulerJob cronSchedulerJob;

    @Override
    public void run(String... args) throws Exception {
        cronSchedulerJob.scheduleJobs();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>开始启动定时任务<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
}
