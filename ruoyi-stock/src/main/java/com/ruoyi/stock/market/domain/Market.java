package com.ruoyi.stock.market.domain;

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
 * 市场对象 market
 *
 * @author ruoyi
 * @date 2024-11-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Market implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名字
     */
    @Excel(name = "名字")
    private String name;
    /**
     * 国家缩写
     */
    @Excel(name = "国家缩写")
    private String country;

    /**
     * 市场类型: stock:股票市场,coin_usdt:U本位虚拟币
     */
    private String type;

    /**
     * 货币缩写
     */
    @Excel(name = "货币缩写")
    private String currency;

    /**
     * 国旗图片
     */
    @Excel(name = "国旗图片")
    private String countryImg;

    /**
     * 主市场 see YNEnum, 0: 否. 1: 是
     */
    @Excel(name = "主市场 see YNEnum, 0: 否. 1: 是")
    private String isMaster;

    /**
     * 主市场对本市场货币汇率, 主市场填1
     */
    @Excel(name = "主市场对本市场货币汇率, 主市场填1")
    private BigDecimal mainExchangeRate;

    /**
     * 从市场(本市场)对主市场货币汇率, 主市场填1
     */
    @Excel(name = "从市场(本市场)对主市场货币汇率, 主市场填1")
    private BigDecimal slaveExchangeRate;

    /**
     * 数据源标记 see StockDataSourceEnum
     */
    @Excel(name = "数据源标记 see StockDataSourceEnum")
    private String dataSourceMark;

    /**
     * 数据源配置JSON
     */
    @Excel(name = "数据源配置JSON")
    private String dataSourceJson;

    /**
     * 时区
     */
    @Excel(name = "时区")
    private String timeZone;

    /**
     * 上午开始交易时间
     */
    @Excel(name = "上午开始交易时间")
    private String transAmBegin;

    /**
     * 上午结束交易时间
     */
    @Excel(name = "上午结束交易时间")
    private String transAmEnd;

    /**
     * 下午开始交易时间
     */
    @Excel(name = "下午开始交易时间")
    private String transPmBegin;

    /**
     * 下午结束交易时间
     */
    @Excel(name = "下午结束交易时间")
    private String transPmEnd;

    /**
     * 买入手续费
     */
    @Excel(name = "买入手续费")
    private BigDecimal buyFee;

    /**
     * 卖出手续费
     */
    @Excel(name = "卖出手续费")
    private BigDecimal sellFee;

    /**
     * 其他手续费(买入印花税)
     */
    @Excel(name = "其他手续费(买入印花税)")
    private BigDecimal otherFee;

    /**
     * 其他手续费(卖出印花税)
     */
    @Excel(name = "其他手续费(卖出印花税)")
    private BigDecimal sellOtherFee;

    /**
     * 股票最小购买数量
     */
    @Excel(name = "股票最小购买数量")
    private Long minNum;

    /**
     * 股票最大购买数量
     */
    @Excel(name = "股票最大购买数量")
    private Long maxNum;

    /**
     * 最少持有时间(分钟)
     */
    @Excel(name = "最少持有时间(分钟)")
    private Long sellTime;

    /**
     * 涨停
     */
    @Excel(name = "涨停")
    private BigDecimal chgPctLimit;

    /**
     * 充值 see YNEnum, 0: 否. 1: 是
     */
    @Excel(name = "充值 see YNEnum, 0: 否. 1: 是")
    private String isRecharge;

    /**
     * 提现 see YNEnum, 0: 否. 1: 是
     */
    @Excel(name = "提现 see YNEnum, 0: 否. 1: 是")
    private String isWithdraw;

    @Excel(name = "排序")
    private Integer sort;

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

    /**
     * 上线: 1.是,0否
     */
    @Excel(name = "上线: 1.是,0否")
    private String status;

    /**
     * 同步节假日名称
     */
    @Excel(name = "同步节假日名称")
    private String syncName;
}
