package com.ruoyi.stock.loan.domain;

import java.math.BigDecimal;

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
 * 贷款配置对象 loan_configuration
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanConfiguration implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @Excel(name = "ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 市场ID */
    @Excel(name = "市场ID")
    private Long marketId;

    /** 最低借款金额 */
    @Excel(name = "最低借款金额")
    private BigDecimal minAmt;

    /** 最高借款金额 */
    @Excel(name = "最高借款金额")
    private BigDecimal maxAmt;

    /** 日利率 */
    @Excel(name = "日利率")
    private BigDecimal interestRate;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
