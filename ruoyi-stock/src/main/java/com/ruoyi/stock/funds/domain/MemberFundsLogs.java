package com.ruoyi.stock.funds.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.annotation.Sensitive;
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
 * 资金动态对象 member_funds_logs
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberFundsLogs implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 会员Id */
    @Excel(name = "会员Id")
    private Long memberId;

    /** 市场ID */
    @Excel(name = "市场ID")
    private Long marketId;

    /** 操作金额 */
    @Excel(name = "操作金额")
    private BigDecimal amt;

    /** 会员可见 see YNEnum, 0: 否(后台上下分). 1: 是(其他) */
    @Excel(name = "会员可见 see YNEnum, 0: 否(后台上下分). 1: 是(其他)")
    private String visible;

    /** 操作源: see FundsSourceEnum 1: 后台上下分 2: 充值, 3: 提现, 4: 货币兑换, 5之后按照业务定义 */
    @Excel(name = "操作源: see FundsSourceEnum 1: 后台上下分 2: 充值, 3: 提现, 4: 货币兑换, 5之后按照业务定义")
    private String source;

    /** 操作类型: see FundsOperateTypeEnum 0: 其他 1: 买入, 2: 卖出, 3: 盈亏 */
    @Excel(name = "操作类型: see FundsOperateTypeEnum 0: 其他 1: 买入, 2: 卖出, 3: 盈亏")
    private String operateType;

    /** 可用余额,操作后台的资金变化 */
    @Excel(name = "可用余额,操作后台的资金变化")
    private BigDecimal enableAmt;

    /** 占用金额,操作后台的资金变化 */
    @Excel(name = "占用金额,操作后台的资金变化")
    private BigDecimal occupancyAmt;

    /** 冻结金额,操作后台的资金变化 */
    @Excel(name = "冻结金额,操作后台的资金变化")
    private BigDecimal freezeAmt;

    /** 盈利金额,操作后台的资金变化 */
    @Excel(name = "盈利金额,操作后台的资金变化")
    private BigDecimal profitAmt;

    /** 操作的内容,根据交易的名称,记录ID等信息拼接字符串, 用于后端展示 */
    @Excel(name = "操作的内容,根据交易的名称,记录ID等信息拼接字符串, 用于后端展示")
    private String content;

    /** 操作的内容存放JSON,根据交易的名称,产品代码,等信息拼接JSON, 用于前端展示, 标准格式定义 name code  */
    @Excel(name = "操作的内容存放JSON,根据交易的名称,产品代码,等信息拼接JSON, 用于前端展示, 标准格式定义 name code ")
    private String contentJson;

    /** 源记录ID: 由哪条记录操作的资金 */
    @Excel(name = "源记录ID: 由哪条记录操作的资金")
    private Long sourceId;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(exist = false)
    private String userId;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String accountType;

    @TableField(exist = false)
    private String memberRealName;

    @TableField(exist = false)
    @Sensitive
    private String memberPhone;
}
