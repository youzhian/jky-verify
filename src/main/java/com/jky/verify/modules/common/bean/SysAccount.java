package com.jky.verify.modules.common.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author youzhian
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物理主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账户名称，一般为各个系统的名称
     */
    private String name;

    /**
     * 系统账户的应用ID
     */
    private String appId;

    /**
     * 账户秘钥，用于生成签名
     */
    private String appSecret;
    /**
     * 账户状态，1为可用，0为不可用，默认为1
     */
    private String status;
    /**
     * 系统标识，如房信为fangxin，我房为wofang
     * 用于查找适配器，默认值为default
     */
    private String systemKey;

    /**
     * 账户的描述信息
     */
    private String remarks;

    /**
     * 创建时间
     */
    private Date createOn;

    /**
     * 修改时间
     */
    private Date modifyOn;


}
