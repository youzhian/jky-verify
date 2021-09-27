package com.jky.verify.modules.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author youzhian
 */
public class ScheduledJob implements Job {

    private String name;

    public void setName(String name){
        this.name = name;
    }

    /**
     * 定时任务执行入口
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.println("cron -------> schedule job1 is running .... "+ name +" ------>"+dateFormat.format(new Date()));
    }
}
