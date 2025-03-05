package com.ruoyi.stock.otc.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Sensitive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtcRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 市场
     */
    @Excel(name = "市场")
    private Long marketId;

    /**
     * 会员Id
     */
    @Excel(name = "会员Id")
    private Long memberId;

    /**
     * 股票名称
     */
    @Excel(name = "股票名称")
    private String stockName;

    /**
     * 股票代码
     */
    @Excel(name = "股票代码")
    private String stockCode;

    /**
     * 股票GID
     */
    @Excel(name = "股票GID")
    private String stockGid;

    /**
     * 价格
     */
    @Excel(name = "价格")
    private BigDecimal price;

    /**
     * 类型：备用
     */
    @Excel(name = "类型：备用")
    private String type;

    /**
     * 申购数量
     */
    @Excel(name = "申购数量")
    private Long nums;

    /**
     * 购买金额
     */
    @Excel(name = "购买金额")
    private BigDecimal buyAmt;

    /**
     * 通过数量
     */
    @Excel(name = "通过数量")
    private Long adoptNum;

    /**
     * 通过金额
     */
    @Excel(name = "通过金额")
    private BigDecimal adoptAmt;

    /**
     * 购买方向
     */
    @Excel(name = "购买方向")
    private String direction;

    /**
     * 杠杆
     */
    @Excel(name = "杠杆")
    private Long lever;

    /**
     * 状态
     */
    @Excel(name = "状态")
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


    private String userId;

    private String userName;

    private String accountType;

    private String memberRealName;

    @Sensitive
    private String memberPhone;
}
