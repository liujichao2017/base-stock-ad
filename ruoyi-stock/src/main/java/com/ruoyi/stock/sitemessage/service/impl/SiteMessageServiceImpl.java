package com.ruoyi.stock.sitemessage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.sitemessage.mapper.SiteMessageMapper;
import com.ruoyi.stock.sitemessage.domain.SiteMessage;
import com.ruoyi.stock.sitemessage.service.ISiteMessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 公告Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-28
 */
@Service
public class SiteMessageServiceImpl implements ISiteMessageService
{
    @Autowired
    private SiteMessageMapper siteMessageMapper;

    /**
     * 查询公告
     *
     * @param id 公告主键
     * @return 公告
     */
    @Override
    public SiteMessage selectSiteMessageById(Long id) {
        return siteMessageMapper.selectById(id);
    }

    /**
     * 查询公告列表
     *
     * @param siteMessage 公告
     * @return 公告
     */
    @Override
    public List<SiteMessage> selectSiteMessageList(SiteMessage siteMessage) {
        QueryWrapper<SiteMessage> ew = new QueryWrapper<>();
        ew.setEntity(siteMessage);
        ew.orderByDesc("create_time");
        return siteMessageMapper.selectList(ew);
    }

    /**
     * 新增公告
     *
     * @param siteMessage 公告
     * @return 结果
     */
    @Override
    public int insertSiteMessage(SiteMessage siteMessage)
    {
        siteMessage.setCreateTime(DateUtils.getNowDate());
        return siteMessageMapper.insert(siteMessage);
    }

    /**
     * 修改公告
     *
     * @param siteMessage 公告
     * @return 结果
     */
    @Override
    public int updateSiteMessage(SiteMessage siteMessage)
    {
        siteMessage.setUpdateTime(DateUtils.getNowDate());
        return siteMessageMapper.updateById(siteMessage);
    }

    /**
     * 批量删除公告
     *
     * @param ids 需要删除的公告主键
     * @return 结果
     */
    @Override
    public int deleteSiteMessageByIds(List<Long> ids)
    {
        QueryWrapper<SiteMessage> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return siteMessageMapper.delete(ew);
    }

    /**
     * 删除公告信息
     *
     * @param id 公告主键
     * @return 结果
     */
    @Override
    public int deleteSiteMessageById(Long id)
    {
        return siteMessageMapper.deleteById(id);
    }
}
