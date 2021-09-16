package com.jky.verify.modules.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jky.verify.modules.common.bean.SysAccount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author youzhian
 * @since 2020-11-25
 */
public interface SysAccountMapper extends BaseMapper<SysAccount> {
    /**
     * 根据用户ID查询其系统来源
     * @param userId
     * @return
     */
    @Select("select DISTINCT(sa.name) name from sys_account sa,user_relate ur where ur.user_id = #{userId} and ur.app_id = sa.app_id")
    List<String> querySourceNameByUserId(@Param("userId") Long userId);

}
