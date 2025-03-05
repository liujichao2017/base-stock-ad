package com.ruoyi.stock.otc.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShareCodeUtils;
import com.ruoyi.stock.stock.domain.Stock;
import com.ruoyi.stock.stock.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.otc.mapper.OtcMapper;
import com.ruoyi.stock.otc.domain.Otc;
import com.ruoyi.stock.otc.service.IOtcService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * otc(大宗)Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Service
public class OtcServiceImpl implements IOtcService
{
    @Autowired
    private OtcMapper otcMapper;
    @Autowired
    private IStockService iStockService;

    /**
     * 查询otc(大宗)
     *
     * @param id otc(大宗)主键
     * @return otc(大宗)
     */
    @Override
    public Otc selectOtcById(Long id) {
        return otcMapper.selectById(id);
    }

    /**
     * 查询otc(大宗)列表
     *
     * @param otc otc(大宗)
     * @return otc(大宗)
     */
    @Override
    public List<Otc> selectOtcList(Otc otc) {

        QueryWrapper<Otc> ew = new QueryWrapper<>();
        ew.setEntity(otc);
        ew.orderByDesc("create_time");
        return otcMapper.selectList(ew);
    }

    /**
     * 新增otc(大宗)
     *
     * @param otc otc(大宗)
     * @return 结果
     */
    @Override
    public AjaxResult insertOtc(Otc otc)
    {
        Stock stock = iStockService.selectStock(otc.getStockGid());

        otc.setStockName(stock.getName());
        otc.setStockCode(stock.getCode());
        otc.setStockGid(stock.getGid());
        otc.setPassword(ShareCodeUtils.getCodeNumber(6));
        otc.setCreateTime(DateUtils.getNowDate());
        otcMapper.insert(otc);
        return AjaxResult.success();
    }

    /**
     * 修改otc(大宗)
     *
     * @param otc otc(大宗)
     * @return 结果
     */
    @Override
    public AjaxResult updateOtc(Otc otc)
    {
        Stock stock = iStockService.selectStock(otc.getStockGid());
        if (stock == null) return AjaxResult.error("该股不存在");
        otc.setUpdateTime(DateUtils.getNowDate());
        otcMapper.updateById(otc);
        return AjaxResult.success();
    }

    /**
     * 批量删除otc(大宗)
     *
     * @param ids 需要删除的otc(大宗)主键
     * @return 结果
     */
    @Override
    public int deleteOtcByIds(List<Long> ids)
    {
        QueryWrapper<Otc> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return otcMapper.delete(ew);
    }

    /**
     * 删除otc(大宗)信息
     *
     * @param id otc(大宗)主键
     * @return 结果
     */
    @Override
    public int deleteOtcById(Long id)
    {
        return otcMapper.deleteById(id);
    }
}
