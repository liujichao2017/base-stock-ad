package com.ruoyi.stock.sitemessage.service;

import java.util.List;
import com.ruoyi.stock.sitemessage.domain.SiteMessageMember;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户通知Service接口
 *
 * @author ruoyi
 * @date 2024-11-28
 */
public interface ISiteMessageMemberService
{
    /**
     * 查询用户通知
     *
     * @param id 用户通知主键
     * @return 用户通知
     */
    public SiteMessageMember selectSiteMessageMemberById(Long id);

    /**
     * 查询用户通知列表
     *
     * @param siteMessageMember 用户通知
     * @return 用户通知集合
     */
    public List<SiteMessageMember> selectSiteMessageMemberList(SiteMessageMember siteMessageMember);

    /**
     * 新增用户通知
     *
     * @param siteMessageMember 用户通知
     * @return 结果
     */
    public int insertSiteMessageMember(SiteMessageMember siteMessageMember);

    /**
     * 修改用户通知
     *
     * @param siteMessageMember 用户通知
     * @return 结果
     */
    public int updateSiteMessageMember(SiteMessageMember siteMessageMember);

    /**
     * 批量删除用户通知
     *
     * @param ids 需要删除的用户通知主键集合
     * @return 结果
     */
    public int deleteSiteMessageMemberByIds(List<Long> ids);

    /**
     * 删除用户通知信息
     *
     * @param id 用户通知主键
     * @return 结果
     */
    public int deleteSiteMessageMemberById(Long id);
}
