package com.jky.verify.modules.task.job;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * 静态定时任务
 * @author youzhian
 */
@Configuration
@EnableScheduling
public class StaticScheduleTask {

    @Scheduled(cron = " 0/30 * * * * ?")
    private void configurationTask(){
        System.out.println("==============执行静态定时任务=============="+ LocalDateTime.now());
    }
}
