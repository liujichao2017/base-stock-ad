package com.ruoyi.stock.sitemessage.service;

import java.util.List;
import com.ruoyi.stock.sitemessage.domain.SiteMessage;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 公告Service接口
 *
 * @author ruoyi
 * @date 2024-11-28
 */
public interface ISiteMessageService
{
    /**
     * 查询公告
     *
     * @param id 公告主键
     * @return 公告
     */
    public SiteMessage selectSiteMessageById(Long id);

    /**
     * 查询公告列表
     *
     * @param siteMessage 公告
     * @return 公告集合
     */
    public List<SiteMessage> selectSiteMessageList(SiteMessage siteMessage);

    /**
     * 新增公告
     *
     * @param siteMessage 公告
     * @return 结果
     */
    public int insertSiteMessage(SiteMessage siteMessage);

    /**
     * 修改公告
     *
     * @param siteMessage 公告
     * @return 结果
     */
    public int updateSiteMessage(SiteMessage siteMessage);

    /**
     * 批量删除公告
     *
     * @param ids 需要删除的公告主键集合
     * @return 结果
     */
    public int deleteSiteMessageByIds(List<Long> ids);

    /**
     * 删除公告信息
     *
     * @param id 公告主键
     * @return 结果
     */
    public int deleteSiteMessageById(Long id);
}
