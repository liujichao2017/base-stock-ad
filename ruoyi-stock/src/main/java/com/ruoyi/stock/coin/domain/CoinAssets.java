package com.ruoyi.stock.coin.domain;

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
 * 虚拟币资金对象 coin_assets
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinAssets implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 绑定的市场 */
    @Excel(name = "绑定的市场")
    private Long marketId;

    /** 客户ID */
    @Excel(name = "客户ID")
    private Long memberId;

    /** 币类型: 1:合约 */
    @Excel(name = "币类型: 1:合约")
    private String type;

    /** 币 */
    @Excel(name = "币")
    private String coin;

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal num;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
