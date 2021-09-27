package com.jky.verify.modules.task.job;

import com.jky.verify.common.exception.VerifyErrorException;
import com.jky.verify.modules.task.bean.TaskInfo;
import com.jky.verify.modules.task.mapper.TaskInfoMapper;
import com.jky.verify.modules.task.service.ITaskInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 动态执行定时任务，从任务列表中获取数据
 * @author youzhian
 */
@Configuration
@EnableScheduling
public class DynamicScheduleTask implements SchedulingConfigurer {

    @Resource
    ITaskInfoService taskInfoService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        /**
         * 查询定时任务列表
         */
        List<TaskInfo> tasks = taskInfoService.list();

        if(tasks != null && !tasks.isEmpty()){
            tasks.forEach(t->{
                scheduledTaskRegistrar.addTriggerTask(
                        //添加任务执行内容
                        ()-> System.out.println("执行动态任务：["+t.getTaskName()+"("+t.getTaskKey()+")]" + LocalDateTime.now().toLocalTime()),
                        //设置执行周期（trigger）
                        triggerContext -> {
                            // 执行周期
                            String cron = t.getCron();
                            if(StringUtils.isBlank(cron)){
                                throw new VerifyErrorException("执行周期不能为空");
                            }
                            // 返回执行周期（Date）
                            return new CronTrigger(cron).nextExecutionTime(triggerContext);
                        }
                );
            });
        }

    }
}
