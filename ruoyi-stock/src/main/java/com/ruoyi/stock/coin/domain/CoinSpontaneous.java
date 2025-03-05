package com.ruoyi.stock.coin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 自发币对象 coin_spontaneous
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinSpontaneous implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 交易对
     */
    @Excel(name = "交易对")
    private String symbol;

    /**
     * 币名
     */
    @Excel(name = "币名")
    private String bcdn;

    /**
     * 计价币名
     */
    @Excel(name = "计价币名")
    private String qcdn;

    /**
     * 计价币名
     */
    @Excel(name = "机器人状态1:启用,0:禁用")
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
}
