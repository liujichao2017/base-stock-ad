package com.ruoyi.stock.otc.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.io.Serializable;

/**
 * otc(大宗)对象 otc
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Otc implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 市场 */
    @Excel(name = "市场")
    private Long marketId;

    /** 股票名称 */
    @Excel(name = "股票名称")
    private String stockName;

    /** 股票代码 */
    @Excel(name = "股票代码")
    private String stockCode;

    /** 股票GID */
    @Excel(name = "股票GID")
    private String stockGid;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 类型：备用 */
    @Excel(name = "类型：备用")
    private String type;

    /** 交易密码 */
    @Excel(name = "交易密码")
    private String password;

    /** 开始购买时间: 09:00 */
    @Excel(name = "开始购买时间: 09:00")
    private String startBuyTime;

    /** 购买结束时间 15:30 */
    @Excel(name = "购买结束时间 15:30")
    private String endBuyTime;

    /** 上架状态 */
    @Excel(name = "上架状态")
    private Integer status;

    /** 存放展示性字段 */
    @Excel(name = "存放展示性字段")
    private String extra;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 平仓时间限制: 分钟
     */
    @Excel(name = "平仓时间限制: 分钟")
    private Integer sellTime;
}
