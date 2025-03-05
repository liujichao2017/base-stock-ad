package com.ruoyi.stock.wite.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Sensitive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户日内交易记录对象 within_days_record
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithinDaysRecordVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 市场ID
     */
    @Excel(name = "市场ID")
    private Long marketId;

    /**
     * 会员Id
     */
    @Excel(name = "会员Id")
    private Long memberId;

    /**
     * 申请金额
     */
    @Excel(name = "申请金额")
    private BigDecimal amt;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    private String userId;


    private String userName;


    private String accountType;


    private String memberRealName;


    @Sensitive
    private String memberPhone;
}
