package com.ruoyi.stock.member.domain;

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
 * 用户银行卡对象 member_bank
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberBank implements Serializable {
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 会员ID */
    @Excel(name = "会员ID")
    private Long memberId;

    /** 银行名称 */
    @Excel(name = "银行名称")
    private String bank1;

    /** 银行卡号 */
    @Excel(name = "银行卡号")
    private String bank2;

    /** 银行代码 */
    @Excel(name = "银行代码")
    private String bank3;

    /** 开户人名称 */
    @Excel(name = "开户人名称")
    private String bank4;

    /** 预留字段,根据项目需要使用 */
    @Excel(name = "预留字段,根据项目需要使用")
    private String bank5;

    /** 预留字段,根据项目需要使用 */
    @Excel(name = "预留字段,根据项目需要使用")
    private String bank6;

    /** 预留字段,根据项目需要使用 */
    @Excel(name = "预留字段,根据项目需要使用")
    private String bank7;

    /** 预留字段,根据项目需要使用 */
    @Excel(name = "预留字段,根据项目需要使用")
    private String bank8;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
