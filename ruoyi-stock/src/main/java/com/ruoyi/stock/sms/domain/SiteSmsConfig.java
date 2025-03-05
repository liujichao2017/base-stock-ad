package com.ruoyi.stock.sms.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.io.Serializable;

/**
 * 短信配置对象 site_sms_config
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteSmsConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 短信类型 */
    @Excel(name = "短信类型")
    private String type;

    /** 国家 */
    @Excel(name = "国家")
    private String country;

    /** 区号 */
    @Excel(name = "区号")
    private String areaCode;

    /** 短信配置 */
    @Excel(name = "短信配置")
    private String json;

    /** 启用状态 */
    @Excel(name = "启用状态")
    private String status;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
