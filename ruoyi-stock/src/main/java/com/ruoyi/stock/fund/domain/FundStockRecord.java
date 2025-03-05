package com.ruoyi.stock.fund.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 基金股票子项对象 fund_stock_record
 *
 * @author ruoyi
 * @date 2024-12-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundStockRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 基金ID
     */
    @Excel(name = "基金ID")
    private Long fundId;

    /**
     * 关联的code
     */
    @Excel(name = "关联的code")
    private String code;

    /**
     * 股票类型
     */
    @Excel(name = "股票类型")
    private String type;

    /**
     * 是否显示
     */
    @Excel(name = "是否显示")
    private String status;


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

    @TableField(exist = false)
    private String fundName;

    @TableField(exist = false)
    private String fundCode;

    @TableField(exist = false)
    private String stockName;

    @TableField(exist = false)
    private String stockCode;

    @TableField(exist = false)
    private String stockType;
}
