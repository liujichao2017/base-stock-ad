package com.ruoyi.stock.experience.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.annotation.Sensitive;
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
 * 体验金使用记录对象 experience_record
 *
 * @author ruoyi
 * @date 2024-12-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 客户ID */
    @Excel(name = "客户ID")
    private Long memberId;

    /** 模块 */
    @Excel(name = "模块")
    private String source;

    /** 记录ID */
    @Excel(name = "记录ID")
    private Long sourceId;

    /** 已使用金额 */
    @Excel(name = "已使用金额")
    private BigDecimal amt;

    /** 市场ID */
    @Excel(name = "市场ID")
    private Long marketId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    @TableField(exist = false)
    private String userId;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String accountType;

    @TableField(exist = false)
    private String memberRealName;

    @TableField(exist = false)
    @Sensitive
    private String memberPhone;
}
