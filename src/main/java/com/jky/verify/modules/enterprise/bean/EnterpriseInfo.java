package com.jky.verify.modules.enterprise.bean;

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
 * @since 2021-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EnterpriseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物理主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 企业名称
     */
    private String enterpriseName;

    /**
     * 企业关键字，便于程序查找
     */
    private String enterpriseKey;

    /**
     * 应用编号，调用吉客云时需要用到
     */
    private String appKey;

    /**
     * 加密秘钥
     */
    private String appSecret;

    /**
     * 描述信息
     */
    private String epDesc;


}
