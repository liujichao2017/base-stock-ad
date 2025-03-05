package com.ruoyi.stock.ai.service;

import java.util.List;
import com.ruoyi.stock.ai.domain.AiTradeRecord;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.stock.ai.vo.AiTradeVo;

/**
 * AI交易记录Service接口
 *
 * @author ruoyi
 * @date 2024-11-27
 */
public interface IAiTradeRecordService
{
    /**
     * 查询AI交易记录
     *
     * @param id AI交易记录主键
     * @return AI交易记录
     */
    public AiTradeRecord selectAiTradeRecordById(Long id);

    /**
     * 查询AI交易记录列表
     *
     * @param aiTradeRecord AI交易记录
     * @return AI交易记录集合
     */
    public List<AiTradeRecord> selectAiTradeRecordList(AiTradeRecord aiTradeRecord);

    /**
     * 新增AI交易记录
     *
     * @param aiTradeRecord AI交易记录
     * @return 结果
     */
    public int insertAiTradeRecord(AiTradeRecord aiTradeRecord);

    /**
     * 修改AI交易记录
     *
     * @param aiTradeRecord AI交易记录
     * @return 结果
     */
    public int updateAiTradeRecord(AiTradeRecord aiTradeRecord);

    /**
     * 批量删除AI交易记录
     *
     * @param ids 需要删除的AI交易记录主键集合
     * @return 结果
     */
    public int deleteAiTradeRecordByIds(List<Long> ids);

    /**
     * 删除AI交易记录信息
     *
     * @param id AI交易记录主键
     * @return 结果
     */
    public int deleteAiTradeRecordById(Long id);

    boolean position(AiTradeVo body);
}
