package com.ruoyi.stock.fund.service;

import java.util.List;
import com.ruoyi.stock.fund.domain.FundLists;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基金产品Service接口
 *
 * @author ruoyi
 * @date 2024-11-27
 */
public interface IFundListsService
{
    /**
     * 查询基金产品
     *
     * @param id 基金产品主键
     * @return 基金产品
     */
    public FundLists selectFundListsById(Long id);

    /**
     * 查询基金产品列表
     *
     * @param fundLists 基金产品
     * @return 基金产品集合
     */
    public List<FundLists> selectFundListsList(FundLists fundLists);

    /**
     * 新增基金产品
     *
     * @param fundLists 基金产品
     * @return 结果
     */
    public int insertFundLists(FundLists fundLists);

    /**
     * 修改基金产品
     *
     * @param fundLists 基金产品
     * @return 结果
     */
    public int updateFundLists(FundLists fundLists);

    /**
     * 批量删除基金产品
     *
     * @param ids 需要删除的基金产品主键集合
     * @return 结果
     */
    public int deleteFundListsByIds(List<Long> ids);

    /**
     * 删除基金产品信息
     *
     * @param id 基金产品主键
     * @return 结果
     */
    public int deleteFundListsById(Long id);
}
