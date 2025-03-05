package com.ruoyi.stock.news.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.news.mapper.SiteNewsConfigMapper;
import com.ruoyi.stock.news.domain.SiteNewsConfig;
import com.ruoyi.stock.news.service.ISiteNewsConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 新闻配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-21
 */
@Service
public class SiteNewsConfigServiceImpl implements ISiteNewsConfigService
{
    @Autowired
    private SiteNewsConfigMapper siteNewsConfigMapper;

    /**
     * 查询新闻配置
     *
     * @param id 新闻配置主键
     * @return 新闻配置
     */
    @Override
    public SiteNewsConfig selectSiteNewsConfigById(Long id) {
        return siteNewsConfigMapper.selectById(id);
    }

    /**
     * 查询新闻配置列表
     *
     * @param siteNewsConfig 新闻配置
     * @return 新闻配置
     */
    @Override
    public List<SiteNewsConfig> selectSiteNewsConfigList(SiteNewsConfig siteNewsConfig) {
        QueryWrapper<SiteNewsConfig> ew = new QueryWrapper<>();
        ew.setEntity(siteNewsConfig);
        ew.orderByDesc("create_time");
        return siteNewsConfigMapper.selectList(ew);
    }

    /**
     * 新增新闻配置
     *
     * @param siteNewsConfig 新闻配置
     * @return 结果
     */
    @Override
    public int insertSiteNewsConfig(SiteNewsConfig siteNewsConfig)
    {
        siteNewsConfig.setCreateTime(DateUtils.getNowDate());
        return siteNewsConfigMapper.insert(siteNewsConfig);
    }

    /**
     * 修改新闻配置
     *
     * @param siteNewsConfig 新闻配置
     * @return 结果
     */
    @Override
    public int updateSiteNewsConfig(SiteNewsConfig siteNewsConfig)
    {
        siteNewsConfig.setUpdateTime(DateUtils.getNowDate());
        return siteNewsConfigMapper.updateById(siteNewsConfig);
    }

    /**
     * 批量删除新闻配置
     *
     * @param ids 需要删除的新闻配置主键
     * @return 结果
     */
    @Override
    public int deleteSiteNewsConfigByIds(List<Long> ids)
    {
        QueryWrapper<SiteNewsConfig> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return siteNewsConfigMapper.delete(ew);
    }

    /**
     * 删除新闻配置信息
     *
     * @param id 新闻配置主键
     * @return 结果
     */
    @Override
    public int deleteSiteNewsConfigById(Long id)
    {
        return siteNewsConfigMapper.deleteById(id);
    }
}
