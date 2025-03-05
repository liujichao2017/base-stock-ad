package com.ruoyi.stock.malaysiawarrants.domain;

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
 * malaysiawarrants对象 stock_ml_malaysiawarrants
 *
 * @author ruoyi
 * @date 2025-01-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockMlMalaysiawarrants implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 认股权证名称 */
    @TableId
    private String dwSymbol;

    /** SPEE_tc.KL */
    @Excel(name = "SPEE_tc.KL")
    private String ric;

    /** 元数据 */
    @Excel(name = "元数据")
    private String underlyingSymbol;

    /** 名称 */
    @Excel(name = "名称")
    private String underlyingName;

    /** SPEE.KL */
    @Excel(name = "SPEE.KL")
    private String underlyingRic;

    /** 类型 C/P */
    @Excel(name = "类型 C/P")
    private String type;

    /** 行使价 */
    @Excel(name = "行使价")
    private String exercisePrice;

    /** 行使比率 */
    @Excel(name = "行使比率")
    private String convRatio;

    /** 0.200 */
    @Excel(name = "0.200")
    private String dwps;

    /** Macquarie */
    @Excel(name = "Macquarie")
    private String issuer;

    /** 增量(%) */
    @Excel(name = "增量(%)")
    private String delta;

    /** 隐含波动率(%) */
    @Excel(name = "隐含波动率(%)")
    private String impliedVolalitiy;

    /** 敏感度 */
    @Excel(name = "敏感度")
    private String sensitivity;

    /** -0.52 */
    @Excel(name = "-0.52")
    private String theta;

    /** 有效杠杆 */
    @Excel(name = "有效杠杆")
    private String effectiveGearing;

    /** 28 Apr 25 */
    @Excel(name = "28 Apr 25")
    private String ltDate;

    /** OTM */
    @Excel(name = "OTM")
    private String moneyness;

    /** 4 */
    @Excel(name = "4")
    private String moneynessPercent;

    /** 4% OTM */
    @Excel(name = "4% OTM")
    private String moneynessC;

    /** 买入量 */
    @Excel(name = "买入量")
    private String bidVolume;

    /** 311 */
    @Excel(name = "311")
    private String bidVolumeF;

    /** 买入价 */
    @Excel(name = "买入价")
    private String bidPrice;

    /** 0.040 */
    @Excel(name = "0.040")
    private String bidPriceF;

    /** 价格变化(%) */
    @Excel(name = "价格变化(%)")
    private String priceChange;

    /** -11.1 */
    @Excel(name = "-11.1")
    private String priceChangeF;

    /** 卖价 */
    @Excel(name = "卖价")
    private String askPrice;

    /** 0.045 */
    @Excel(name = "0.045")
    private String askPriceF;

    /** 卖出量 */
    @Excel(name = "卖出量")
    private String askVolume;

    /** 85 */
    @Excel(name = "85")
    private String askVolumeF;

    /** 交易量 */
    @Excel(name = "交易量")
    private String tradeVolume;

    /** 4 */
    @Excel(name = "4")
    private String tradeVolumeF;

    /** 最高流动性 */
    @Excel(name = "最高流动性")
    private String highresp;

    /** 低时间衰减 */
    @Excel(name = "低时间衰减")
    private String lowtimedecay;

    /** 17 Sep 24 */
    @Excel(name = "17 Sep 24")
    private String listDate;

    /** false */
    @Excel(name = "false")
    private String appchexpry;

    /** 新授权 */
    @Excel(name = "新授权")
    private String newwarrant;

    /** 库存售罄 */
    @Excel(name = "库存售罄")
    private String soldout;

    /** 最快响应 */
    @Excel(name = "最快响应")
    private String moreresponsive;

    /** 到期 */
    @Excel(name = "到期")
    private String maturity;

    /** 5326CE */
    @Excel(name = "5326CE")
    private String ticker;

    /** 基础价格 */
    @Excel(name = "基础价格")
    private String underlyingPrice;

    /** 99SMART-CE */
    @Excel(name = "99SMART-CE")
    private String dsplyNmll;

    /** 高效杠杆 */
    @Excel(name = "高效杠杆")
    private String higheffectivegearing;
}
