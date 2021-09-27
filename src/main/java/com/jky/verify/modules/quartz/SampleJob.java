package com.jky.verify.modules.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 实现一个样例的job
 * @author youzhian
 */
public class SampleJob extends QuartzJobBean {

    private String name;

    public void setName(String name){
        this.name = name;
    }
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Quartz --------> Hello, " + this.name);
    }
}
