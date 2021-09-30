package com.jky.verify.modules.task.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author youzhian
 * @since 2021-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TaskInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物理主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 定时任务名称（中文名）
     */
    private String taskName;

    /**
     * 定时任务key，用于区分并查询定时任务
     */
    private String taskKey;

    /**
     * 定时任务执行周期,如 0/5 * * * * ?
     */
    private String cron;

    /**
     * 执行时可以传递某些参数
     */
    private String param;


}
