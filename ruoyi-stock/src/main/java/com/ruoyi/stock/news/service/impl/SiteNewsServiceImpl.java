package com.ruoyi.stock.news.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.news.mapper.SiteNewsMapper;
import com.ruoyi.stock.news.domain.SiteNews;
import com.ruoyi.stock.news.service.ISiteNewsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 新闻资讯Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Service
public class SiteNewsServiceImpl implements ISiteNewsService
{
    @Autowired
    private SiteNewsMapper siteNewsMapper;

    /**
     * 查询新闻资讯
     *
     * @param id 新闻资讯主键
     * @return 新闻资讯
     */
    @Override
    public SiteNews selectSiteNewsById(Long id) {
        return siteNewsMapper.selectById(id);
    }

    /**
     * 查询新闻资讯列表
     *
     * @param siteNews 新闻资讯
     * @return 新闻资讯
     */
    @Override
    public List<SiteNews> selectSiteNewsList(SiteNews siteNews) {
        QueryWrapper<SiteNews> ew = new QueryWrapper<>();
        ew.setEntity(siteNews);
        ew.orderByDesc("create_time");
        return siteNewsMapper.selectList(ew);
    }

    /**
     * 新增新闻资讯
     *
     * @param siteNews 新闻资讯
     * @return 结果
     */
    @Override
    public int insertSiteNews(SiteNews siteNews)
    {
        siteNews.setCreateTime(DateUtils.getNowDate());
        return siteNewsMapper.insert(siteNews);
    }

    /**
     * 修改新闻资讯
     *
     * @param siteNews 新闻资讯
     * @return 结果
     */
    @Override
    public int updateSiteNews(SiteNews siteNews)
    {
        siteNews.setUpdateTime(DateUtils.getNowDate());
        return siteNewsMapper.updateById(siteNews);
    }

    /**
     * 批量删除新闻资讯
     *
     * @param ids 需要删除的新闻资讯主键
     * @return 结果
     */
    @Override
    public int deleteSiteNewsByIds(List<Long> ids)
    {
        QueryWrapper<SiteNews> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return siteNewsMapper.delete(ew);
    }

    /**
     * 删除新闻资讯信息
     *
     * @param id 新闻资讯主键
     * @return 结果
     */
    @Override
    public int deleteSiteNewsById(Long id)
    {
        return siteNewsMapper.deleteById(id);
    }
}
