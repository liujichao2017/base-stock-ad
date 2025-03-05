package com.ruoyi.stock.fund.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.stock.fund.domain.FundStockRecord;
import com.ruoyi.stock.fund.mapper.FundStockRecordMapper;
import com.ruoyi.stock.fund.service.IFundStockRecordService;
import com.ruoyi.stock.malaysiawarrants.domain.StockMlMalaysiawarrants;
import com.ruoyi.stock.malaysiawarrants.mapper.StockMlMalaysiawarrantsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基金股票子项Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-16
 */
@Service
public class FundStockRecordServiceImpl implements IFundStockRecordService {
    @Autowired
    private FundStockRecordMapper fundStockRecordMapper;
    @Autowired
    private StockMlMalaysiawarrantsMapper stockMlMalaysiawarrantsMapper;

    /**
     * 查询基金股票子项
     *
     * @param id 基金股票子项主键
     * @return 基金股票子项
     */
    @Override
    public FundStockRecord selectFundStockRecordById(Long id) {
        return fundStockRecordMapper.selectById(id);
    }

    /**
     * 查询基金股票子项列表
     *
     * @param fundStockRecord 基金股票子项
     * @return 基金股票子项
     */
    @Override
    public List<FundStockRecord> selectFundStockRecordList(FundStockRecord fundStockRecord) {
        QueryWrapper<FundStockRecord> ew = new QueryWrapper<>();
        ew.like(LogicUtils.isNotBlank(fundStockRecord.getCode()), "code", fundStockRecord.getCode());
        ew.eq(LogicUtils.isNotBlank(fundStockRecord.getStatus()), "status", fundStockRecord.getStatus());
        ew.eq(LogicUtils.isNotNull(fundStockRecord.getFundId()), "fund_id", fundStockRecord.getFundId());
        ew.orderByDesc("create_time");
        List<FundStockRecord> fundStockRecords = fundStockRecordMapper.selectByList(ew);
        if (LogicUtils.isEmpty(fundStockRecords)) {
            return fundStockRecords;
        }
        Map<String, FundStockRecord> fundStockRecordMap = fundStockRecords.stream().filter(f -> "ml".equals(f.getType()))
                .collect(Collectors.toMap(FundStockRecord::getCode, f -> f));
        if (LogicUtils.isEmpty(fundStockRecordMap)) {
            return fundStockRecords;
        }
        QueryWrapper<StockMlMalaysiawarrants> ewM = new QueryWrapper<>();
        ewM.in("dw_symbol", fundStockRecordMap.keySet());
        List<StockMlMalaysiawarrants> stockMlMalaysiawarrants = stockMlMalaysiawarrantsMapper.selectList(ewM);
        for (StockMlMalaysiawarrants stockMlMalaysiawarrant : stockMlMalaysiawarrants) {
            FundStockRecord f = fundStockRecordMap.get(stockMlMalaysiawarrant.getDwSymbol());
            if (LogicUtils.isNull(f)) {
                continue;
            }
            f.setStockCode(stockMlMalaysiawarrant.getDwSymbol());
            f.setStockName(stockMlMalaysiawarrant.getUnderlyingName());
        }
        return fundStockRecords;
    }

    /**
     * 新增基金股票子项
     *
     * @param fundStockRecord 基金股票子项
     * @return 结果
     */
    @Override
    public int insertFundStockRecord(FundStockRecord fundStockRecord) {
        fundStockRecord.setCreateTime(DateUtils.getNowDate());
        return fundStockRecordMapper.insert(fundStockRecord);
    }

    /**
     * 修改基金股票子项
     *
     * @param fundStockRecord 基金股票子项
     * @return 结果
     */
    @Override
    public int updateFundStockRecord(FundStockRecord fundStockRecord) {
        fundStockRecord.setUpdateTime(DateUtils.getNowDate());
        return fundStockRecordMapper.updateById(fundStockRecord);
    }

    /**
     * 批量删除基金股票子项
     *
     * @param ids 需要删除的基金股票子项主键
     * @return 结果
     */
    @Override
    public int deleteFundStockRecordByIds(List<Long> ids) {
        QueryWrapper<FundStockRecord> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return fundStockRecordMapper.delete(ew);
    }

    /**
     * 删除基金股票子项信息
     *
     * @param id 基金股票子项主键
     * @return 结果
     */
    @Override
    public int deleteFundStockRecordById(Long id) {
        return fundStockRecordMapper.deleteById(id);
    }
}
