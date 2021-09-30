package com.jky.verify.modules.task.service.impl;

import com.jky.verify.modules.task.bean.TaskInfo;
import com.jky.verify.modules.task.mapper.TaskInfoMapper;
import com.jky.verify.modules.task.service.ITaskInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author youzhian
 * @since 2021-09-22
 */
@Service
public class TaskInfoServiceImpl extends ServiceImpl<TaskInfoMapper, TaskInfo> implements ITaskInfoService {

}
