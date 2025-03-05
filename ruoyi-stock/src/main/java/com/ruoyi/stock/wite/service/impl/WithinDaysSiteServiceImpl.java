package com.ruoyi.stock.wite.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.wite.mapper.WithinDaysSiteMapper;
import com.ruoyi.stock.wite.domain.WithinDaysSite;
import com.ruoyi.stock.wite.service.IWithinDaysSiteService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 日内交易配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Service
public class WithinDaysSiteServiceImpl implements IWithinDaysSiteService
{
    @Autowired
    private WithinDaysSiteMapper withinDaysSiteMapper;

    /**
     * 查询日内交易配置
     *
     * @param id 日内交易配置主键
     * @return 日内交易配置
     */
    @Override
    public WithinDaysSite selectWithinDaysSiteById(Long id) {
        return withinDaysSiteMapper.selectById(id);
    }

    /**
     * 查询日内交易配置列表
     *
     * @param withinDaysSite 日内交易配置
     * @return 日内交易配置
     */
    @Override
    public List<WithinDaysSite> selectWithinDaysSiteList(WithinDaysSite withinDaysSite) {
        QueryWrapper<WithinDaysSite> ew = new QueryWrapper<>();
        ew.setEntity(withinDaysSite);
        ew.orderByDesc("create_time");
        return withinDaysSiteMapper.selectList(ew);
    }

    /**
     * 新增日内交易配置
     *
     * @param withinDaysSite 日内交易配置
     * @return 结果
     */
    @Override
    public int insertWithinDaysSite(WithinDaysSite withinDaysSite)
    {
        withinDaysSite.setCreateTime(DateUtils.getNowDate());
        return withinDaysSiteMapper.insert(withinDaysSite);
    }

    /**
     * 修改日内交易配置
     *
     * @param withinDaysSite 日内交易配置
     * @return 结果
     */
    @Override
    public int updateWithinDaysSite(WithinDaysSite withinDaysSite)
    {
        withinDaysSite.setUpdateTime(DateUtils.getNowDate());
        return withinDaysSiteMapper.updateById(withinDaysSite);
    }

    /**
     * 批量删除日内交易配置
     *
     * @param ids 需要删除的日内交易配置主键
     * @return 结果
     */
    @Override
    public int deleteWithinDaysSiteByIds(List<Long> ids)
    {
        QueryWrapper<WithinDaysSite> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return withinDaysSiteMapper.delete(ew);
    }

    /**
     * 删除日内交易配置信息
     *
     * @param id 日内交易配置主键
     * @return 结果
     */
    @Override
    public int deleteWithinDaysSiteById(Long id)
    {
        return withinDaysSiteMapper.deleteById(id);
    }
}
