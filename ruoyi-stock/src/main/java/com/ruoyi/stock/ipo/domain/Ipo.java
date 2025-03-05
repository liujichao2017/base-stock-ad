package com.ruoyi.stock.ipo.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * IPO(新股)对象 ipo
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ipo implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 新股名称 */
    @Excel(name = "新股名称")
    private String name;

    /** 申购代码 */
    @Excel(name = "申购代码")
    private String code;

    /** 发行价格 */
    @Excel(name = "发行价格")
    private BigDecimal price;

    /** 绑定GID */
    @Excel(name = "绑定GID")
    private String stockGid;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 存放展示性字段 */
    @Excel(name = "存放展示性字段")
    private String extra;

    /** 申购日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申购日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /** 公布日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "公布日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishTime;

    /** 开始购买时间: 09:00 */
    @Excel(name = "开始购买时间: 09:00")
    private String startBuyTime;

    /** 购买结束时间 15:30 */
    @Excel(name = "购买结束时间 15:30")
    private String endBuyTime;

    /** 市场ID */
    @Excel(name = "市场ID")
    private Long marketId;

    /** 上架状态 */
    @Excel(name = "上架状态")
    private Integer status;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
