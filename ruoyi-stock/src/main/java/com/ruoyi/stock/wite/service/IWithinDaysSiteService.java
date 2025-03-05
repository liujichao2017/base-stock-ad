package com.ruoyi.stock.wite.service;

import java.util.List;
import com.ruoyi.stock.wite.domain.WithinDaysSite;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 日内交易配置Service接口
 *
 * @author ruoyi
 * @date 2024-11-21
 */
public interface IWithinDaysSiteService
{
    /**
     * 查询日内交易配置
     *
     * @param id 日内交易配置主键
     * @return 日内交易配置
     */
    public WithinDaysSite selectWithinDaysSiteById(Long id);

    /**
     * 查询日内交易配置列表
     *
     * @param withinDaysSite 日内交易配置
     * @return 日内交易配置集合
     */
    public List<WithinDaysSite> selectWithinDaysSiteList(WithinDaysSite withinDaysSite);

    /**
     * 新增日内交易配置
     *
     * @param withinDaysSite 日内交易配置
     * @return 结果
     */
    public int insertWithinDaysSite(WithinDaysSite withinDaysSite);

    /**
     * 修改日内交易配置
     *
     * @param withinDaysSite 日内交易配置
     * @return 结果
     */
    public int updateWithinDaysSite(WithinDaysSite withinDaysSite);

    /**
     * 批量删除日内交易配置
     *
     * @param ids 需要删除的日内交易配置主键集合
     * @return 结果
     */
    public int deleteWithinDaysSiteByIds(List<Long> ids);

    /**
     * 删除日内交易配置信息
     *
     * @param id 日内交易配置主键
     * @return 结果
     */
    public int deleteWithinDaysSiteById(Long id);
}
