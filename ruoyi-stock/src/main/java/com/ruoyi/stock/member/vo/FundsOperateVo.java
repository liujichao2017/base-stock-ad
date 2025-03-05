package com.ruoyi.stock.member.vo;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.enums.FundsOperateTypeEnum;
import com.ruoyi.common.enums.FundsSourceEnum;
import com.ruoyi.stock.market.domain.Market;
import com.ruoyi.stock.member.domain.Member;
import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class FundsOperateVo {
    private FundsSourceEnum source;

    private FundsOperateTypeEnum operateType;

    /**
     * 用户ID与用户对象填一个即可
     */
    private Long memberId;

    private Member member;

    /**
     * 市场对象与市场Id填一个即可
     */
    private Market market;
    private Long marketId;


    private BigDecimal amt;


    /**
     * 是否能扣为负数, 只会扣除可用资金
     */
    private boolean negative = false;

    /**
     * 源记录ID: 由哪条记录操作的资金
     */
    private Long sourceId;
    /**
     * 源记录名称: 股票名称, 基金名称, 新股名称...无则留空
     */
    private String name;
    /**
     * 源记录名称: 股票代码, 基金代码, 新股代码...无则留空
     */
    private String code;

    /**
     * 操作记录用户可见, 为null 则跟随 FundsSourceEnum 中的设置
     */
    private String visible;

    private String contentJson;

    private final Map<String, Object> fundsInfoMap = new LinkedHashMap<>();

    public FundsOperateVo set(String key, Object value) {
        this.fundsInfoMap.put(key, value);
        return this;
    }

    public void build() {
        this.contentJson = JSONObject.toJSONString(fundsInfoMap);
    }

    public static class FundsInfoKey {
        // 股票名称
        public static final String STOCK_NAME = "stockName";
        // 股票代码
        public static final String STOCK_CODE = "stockCode";
        // 产品名称
        public static final String PRODUCT_NAME = "productName";
        // 产品代码
        public static final String PRODUCT_CODE = "productCode";
        // 放款
        public static final String LOANS_AMT = "loansAmt";
        // 还款
        public static final String LOAN_RECYCLE_AMT = "loanRecycleAmt";
        // 占用本金
        public static final String OCCUPANCY_AMT = "occupancyAmt";
        // 冻结本金
        public static final String FREEZE_AMT = "freezeAmt";
        // 退还本金
        public static final String ROLLBACK_AMT = "rollbackAmt";
        // 补缴本金
        public static final String BACK_PAYMENT_AMT = "backPaymentAmt";
        // 总盈亏金额
        public static final String ALL_PROFIT_LOSS_AMT = "allProfitLossAmt";
        // 盈亏金额
        public static final String PROFIT_LOSS_AMT = "profitLossAmt";
        // 币名
        public static final String COIN_NAME = "coinName";
        // 手续费
        public static final String FEE_AMT = "feeAmt";
        // 申请金额
        public static final String APPLY_AMT = "applyAmt";
        // 通过金额
        public static final String PASS_AMT = "passAmt";
        // 认缴金额
        public static final String SUBSCRIPTION_AMT = "subscriptionAmt";
        // 利息
        public static final String INTEREST_AMT = "interestAmt";


    }
}
