package com.ruoyi.stock.ipo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * IPO(新股)申购记录对象 ipo_record
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IpoRecordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 市场ID */
    @Excel(name = "市场ID")
    private Long marketId;

    /** 会员Id */
    @Excel(name = "会员Id")
    private Long memberId;

    /** 新股ID */
    @Excel(name = "新股ID")
    private Long ipoId;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 新股名称 */
    @Excel(name = "新股名称")
    private String name;

    /** 申购代码 */
    @Excel(name = "申购代码")
    private String code;

    /** 发行价格 */
    @Excel(name = "发行价格")
    private BigDecimal price;

    /** 申购数量 */
    @Excel(name = "申购数量")
    private Long nums;

    /** 状态: 1: 待审批, 2: 中签, 3: 未中签 */
    @Excel(name = "状态: 1: 待审批, 2: 中签, 3: 未中签")
    private String status;

    /** 通知状态: 1: 已通知, 0: 未通知 */
    @Excel(name = "通知状态: 1: 已通知, 0: 未通知")
    private String notifyStatus;

    /** 通知内容 */
    @Excel(name = "通知内容")
    private String notifyContext;

    /** 中签金额 */
    @Excel(name = "中签金额")
    private BigDecimal totalAmt;

    /** 已划转金额 */
    @Excel(name = "已划转金额")
    private BigDecimal transferAmt;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Long winNum;

    /** 温馨提示 */
    private String kindTips;

    /** 推送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;
}
