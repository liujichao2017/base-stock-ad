package com.ruoyi.stock.news.service;

import java.util.List;
import com.ruoyi.stock.news.domain.SiteNews;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 新闻资讯Service接口
 *
 * @author ruoyi
 * @date 2024-11-21
 */
public interface ISiteNewsService
{
    /**
     * 查询新闻资讯
     *
     * @param id 新闻资讯主键
     * @return 新闻资讯
     */
    public SiteNews selectSiteNewsById(Long id);

    /**
     * 查询新闻资讯列表
     *
     * @param siteNews 新闻资讯
     * @return 新闻资讯集合
     */
    public List<SiteNews> selectSiteNewsList(SiteNews siteNews);

    /**
     * 新增新闻资讯
     *
     * @param siteNews 新闻资讯
     * @return 结果
     */
    public int insertSiteNews(SiteNews siteNews);

    /**
     * 修改新闻资讯
     *
     * @param siteNews 新闻资讯
     * @return 结果
     */
    public int updateSiteNews(SiteNews siteNews);

    /**
     * 批量删除新闻资讯
     *
     * @param ids 需要删除的新闻资讯主键集合
     * @return 结果
     */
    public int deleteSiteNewsByIds(List<Long> ids);

    /**
     * 删除新闻资讯信息
     *
     * @param id 新闻资讯主键
     * @return 结果
     */
    public int deleteSiteNewsById(Long id);
}
