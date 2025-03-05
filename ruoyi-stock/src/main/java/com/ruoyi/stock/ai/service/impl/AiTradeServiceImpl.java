package com.ruoyi.stock.ai.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.ai.mapper.AiTradeMapper;
import com.ruoyi.stock.ai.domain.AiTrade;
import com.ruoyi.stock.ai.service.IAiTradeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * AI交易Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-27
 */
@Service
public class AiTradeServiceImpl implements IAiTradeService
{
    @Autowired
    private AiTradeMapper aiTradeMapper;

    /**
     * 查询AI交易
     *
     * @param id AI交易主键
     * @return AI交易
     */
    @Override
    public AiTrade selectAiTradeById(Long id) {
        return aiTradeMapper.selectById(id);
    }

    /**
     * 查询AI交易列表
     *
     * @param aiTrade AI交易
     * @return AI交易
     */
    @Override
    public List<AiTrade> selectAiTradeList(AiTrade aiTrade) {
        QueryWrapper<AiTrade> ew = new QueryWrapper<>();
        ew.setEntity(aiTrade);
        ew.orderByDesc("create_time");
        return aiTradeMapper.selectList(ew);
    }

    /**
     * 新增AI交易
     *
     * @param aiTrade AI交易
     * @return 结果
     */
    @Override
    public int insertAiTrade(AiTrade aiTrade)
    {
        aiTrade.setCreateTime(DateUtils.getNowDate());
        return aiTradeMapper.insert(aiTrade);
    }

    /**
     * 修改AI交易
     *
     * @param aiTrade AI交易
     * @return 结果
     */
    @Override
    public int updateAiTrade(AiTrade aiTrade)
    {
        aiTrade.setUpdateTime(DateUtils.getNowDate());
        return aiTradeMapper.updateById(aiTrade);
    }

    /**
     * 批量删除AI交易
     *
     * @param ids 需要删除的AI交易主键
     * @return 结果
     */
    @Override
    public int deleteAiTradeByIds(List<Long> ids)
    {
        QueryWrapper<AiTrade> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return aiTradeMapper.delete(ew);
    }

    /**
     * 删除AI交易信息
     *
     * @param id AI交易主键
     * @return 结果
     */
    @Override
    public int deleteAiTradeById(Long id)
    {
        return aiTradeMapper.deleteById(id);
    }
}
