package com.ruoyi.stock.ai.service;

import java.util.List;
import com.ruoyi.stock.ai.domain.AiTrade;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * AI交易Service接口
 *
 * @author ruoyi
 * @date 2024-11-27
 */
public interface IAiTradeService
{
    /**
     * 查询AI交易
     *
     * @param id AI交易主键
     * @return AI交易
     */
    public AiTrade selectAiTradeById(Long id);

    /**
     * 查询AI交易列表
     *
     * @param aiTrade AI交易
     * @return AI交易集合
     */
    public List<AiTrade> selectAiTradeList(AiTrade aiTrade);

    /**
     * 新增AI交易
     *
     * @param aiTrade AI交易
     * @return 结果
     */
    public int insertAiTrade(AiTrade aiTrade);

    /**
     * 修改AI交易
     *
     * @param aiTrade AI交易
     * @return 结果
     */
    public int updateAiTrade(AiTrade aiTrade);

    /**
     * 批量删除AI交易
     *
     * @param ids 需要删除的AI交易主键集合
     * @return 结果
     */
    public int deleteAiTradeByIds(List<Long> ids);

    /**
     * 删除AI交易信息
     *
     * @param id AI交易主键
     * @return 结果
     */
    public int deleteAiTradeById(Long id);
}
