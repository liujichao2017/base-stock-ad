package com.ruoyi.stock.fund.service;

import java.util.List;
import com.ruoyi.stock.fund.domain.FundStockRecord;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基金股票子项Service接口
 *
 * @author ruoyi
 * @date 2024-12-16
 */
public interface IFundStockRecordService
{
    /**
     * 查询基金股票子项
     *
     * @param id 基金股票子项主键
     * @return 基金股票子项
     */
    public FundStockRecord selectFundStockRecordById(Long id);

    /**
     * 查询基金股票子项列表
     *
     * @param fundStockRecord 基金股票子项
     * @return 基金股票子项集合
     */
    public List<FundStockRecord> selectFundStockRecordList(FundStockRecord fundStockRecord);

    /**
     * 新增基金股票子项
     *
     * @param fundStockRecord 基金股票子项
     * @return 结果
     */
    public int insertFundStockRecord(FundStockRecord fundStockRecord);

    /**
     * 修改基金股票子项
     *
     * @param fundStockRecord 基金股票子项
     * @return 结果
     */
    public int updateFundStockRecord(FundStockRecord fundStockRecord);

    /**
     * 批量删除基金股票子项
     *
     * @param ids 需要删除的基金股票子项主键集合
     * @return 结果
     */
    public int deleteFundStockRecordByIds(List<Long> ids);

    /**
     * 删除基金股票子项信息
     *
     * @param id 基金股票子项主键
     * @return 结果
     */
    public int deleteFundStockRecordById(Long id);
}
