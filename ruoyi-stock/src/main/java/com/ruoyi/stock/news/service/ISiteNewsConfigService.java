package com.ruoyi.stock.news.service;

import java.util.List;
import com.ruoyi.stock.news.domain.SiteNewsConfig;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 新闻配置Service接口
 *
 * @author ruoyi
 * @date 2024-11-21
 */
public interface ISiteNewsConfigService
{
    /**
     * 查询新闻配置
     *
     * @param id 新闻配置主键
     * @return 新闻配置
     */
    public SiteNewsConfig selectSiteNewsConfigById(Long id);

    /**
     * 查询新闻配置列表
     *
     * @param siteNewsConfig 新闻配置
     * @return 新闻配置集合
     */
    public List<SiteNewsConfig> selectSiteNewsConfigList(SiteNewsConfig siteNewsConfig);

    /**
     * 新增新闻配置
     *
     * @param siteNewsConfig 新闻配置
     * @return 结果
     */
    public int insertSiteNewsConfig(SiteNewsConfig siteNewsConfig);

    /**
     * 修改新闻配置
     *
     * @param siteNewsConfig 新闻配置
     * @return 结果
     */
    public int updateSiteNewsConfig(SiteNewsConfig siteNewsConfig);

    /**
     * 批量删除新闻配置
     *
     * @param ids 需要删除的新闻配置主键集合
     * @return 结果
     */
    public int deleteSiteNewsConfigByIds(List<Long> ids);

    /**
     * 删除新闻配置信息
     *
     * @param id 新闻配置主键
     * @return 结果
     */
    public int deleteSiteNewsConfigById(Long id);
}
