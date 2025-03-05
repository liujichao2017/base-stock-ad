package com.ruoyi.stock.fund.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.stock.fund.domain.FundInterestRate;
import com.ruoyi.stock.fund.domain.FundLists;
import com.ruoyi.stock.fund.domain.MemberFundRecord;
import com.ruoyi.stock.fund.mapper.FundInterestRateMapper;
import com.ruoyi.stock.fund.mapper.FundListsMapper;
import com.ruoyi.stock.fund.mapper.MemberFundRecordMapper;
import com.ruoyi.stock.fund.service.IFundListsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * 基金产品Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@Service
public class FundListsServiceImpl implements IFundListsService {
    @Autowired
    private FundListsMapper fundListsMapper;
    @Autowired
    private FundInterestRateMapper fundInterestRateMapper;
    @Autowired
    private MemberFundRecordMapper memberFundRecordMapper;

    /**
     * 查询基金产品
     *
     * @param id 基金产品主键
     * @return 基金产品
     */
    @Override
    public FundLists selectFundListsById(Long id) {
        return fundListsMapper.selectById(id);
    }

    /**
     * 查询基金产品列表
     *
     * @param fundLists 基金产品
     * @return 基金产品
     */
    @Override
    public List<FundLists> selectFundListsList(FundLists fundLists) {
        QueryWrapper<FundLists> ew = new QueryWrapper<>();
        ew.setEntity(fundLists);
        ew.orderByDesc("create_time");
        return fundListsMapper.selectList(ew);
    }

    /**
     * 新增基金产品
     *
     * @param fundLists 基金产品
     * @return 结果
     */
    @Override
    public int insertFundLists(FundLists fundLists) {
        fundLists.setCreateTime(DateUtils.getNowDate());
        int insert = fundListsMapper.insert(fundLists);
        genFundInterestRate(fundLists, null);
        return insert;
    }

    /**
     * 修改基金产品
     *
     * @param fundLists 基金产品
     * @return 结果
     */
    @Override
    public int updateFundLists(FundLists fundLists) {
        fundLists.setUpdateTime(DateUtils.getNowDate());
        FundLists db = fundListsMapper.selectById(fundLists.getId());
        LogicUtils.assertNotNull(db, "基金记录不存在");
        genFundInterestRate(fundLists, db);
        return fundListsMapper.updateById(fundLists);
    }


    private void genFundInterestRate(FundLists newRecord, FundLists oldRecord) {
        BigDecimal interestRate = newRecord.getInterestRate();
        long cycle = newRecord.getCycle();
        if (LogicUtils.isNotNull(oldRecord)) {
            if (oldRecord.getInterestRate().equals(interestRate) && oldRecord.getCycle().equals(cycle)) {
                return;
            }
            QueryWrapper<FundInterestRate> ew = new QueryWrapper<>();
            ew.eq("fund_id", oldRecord.getId());
            fundInterestRateMapper.delete(ew);
        }
        BigDecimal divide = interestRate.divide(BigDecimal.valueOf(cycle), 2, 4);

        for (long l = 1L; l <= cycle; l++) {
            BigDecimal rate = l == cycle ?
                    interestRate.subtract(divide.multiply(BigDecimal.valueOf(cycle - 1))) : divide;
            FundInterestRate fundInterestRate = new FundInterestRate();
            fundInterestRate.setFundId(newRecord.getId());

            fundInterestRate.setInterestRate(rate);
            fundInterestRate.setCycle(l);
            fundInterestRateMapper.insert(fundInterestRate);
        }
    }

    public static void main(String[] args) {
        BigDecimal totalInterestRate = BigDecimal.valueOf(5);
        int cycle = 5;
        BigDecimal divide = totalInterestRate.divide(BigDecimal.valueOf(cycle), 2, 4);

        for (Long l = 1L; l <= cycle; l++) {
            BigDecimal rate = l == cycle ?
                    totalInterestRate.subtract(divide.multiply(BigDecimal.valueOf(cycle - 1))) : divide;
            System.out.println(rate);
        }
    }

    /**
     * 批量删除基金产品
     *
     * @param ids 需要删除的基金产品主键
     * @return 结果
     */
    @Override
    public int deleteFundListsByIds(List<Long> ids) {
        LogicUtils.assertFalse(existsRecord(ids), "该基金已被用户申购无法删除");
        QueryWrapper<FundLists> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return fundListsMapper.delete(ew);
    }

    /**
     * 删除基金产品信息
     *
     * @param id 基金产品主键
     * @return 结果
     */
    @Override
    public int deleteFundListsById(Long id) {
        LogicUtils.assertFalse(existsRecord(Collections.singletonList(id)), "该基金已被用户申购无法删除");
        return fundListsMapper.deleteById(id);
    }

    public boolean existsRecord(List<Long> ids) {
        QueryWrapper<MemberFundRecord> ew = new QueryWrapper<>();
        ew.in("fund_id", ids);
        Long l = memberFundRecordMapper.selectCount(ew);
        return l > 0L;
    }
}
