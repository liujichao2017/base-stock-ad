package com.ruoyi.stock.sitemessage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.sitemessage.mapper.SiteMessageMemberMapper;
import com.ruoyi.stock.sitemessage.domain.SiteMessageMember;
import com.ruoyi.stock.sitemessage.service.ISiteMessageMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 用户通知Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-28
 */
@Service
public class SiteMessageMemberServiceImpl implements ISiteMessageMemberService
{
    @Autowired
    private SiteMessageMemberMapper siteMessageMemberMapper;

    /**
     * 查询用户通知
     *
     * @param id 用户通知主键
     * @return 用户通知
     */
    @Override
    public SiteMessageMember selectSiteMessageMemberById(Long id) {
        return siteMessageMemberMapper.selectById(id);
    }

    /**
     * 查询用户通知列表
     *
     * @param siteMessageMember 用户通知
     * @return 用户通知
     */
    @Override
    public List<SiteMessageMember> selectSiteMessageMemberList(SiteMessageMember siteMessageMember) {
        QueryWrapper<SiteMessageMember> ew = new QueryWrapper<>();
        ew.setEntity(siteMessageMember);
        ew.orderByDesc("create_time");
        return siteMessageMemberMapper.selectList(ew);
    }

    /**
     * 新增用户通知
     *
     * @param siteMessageMember 用户通知
     * @return 结果
     */
    @Override
    public int insertSiteMessageMember(SiteMessageMember siteMessageMember)
    {
        siteMessageMember.setCreateTime(DateUtils.getNowDate());
        return siteMessageMemberMapper.insert(siteMessageMember);
    }

    /**
     * 修改用户通知
     *
     * @param siteMessageMember 用户通知
     * @return 结果
     */
    @Override
    public int updateSiteMessageMember(SiteMessageMember siteMessageMember)
    {
        siteMessageMember.setUpdateTime(DateUtils.getNowDate());
        return siteMessageMemberMapper.updateById(siteMessageMember);
    }

    /**
     * 批量删除用户通知
     *
     * @param ids 需要删除的用户通知主键
     * @return 结果
     */
    @Override
    public int deleteSiteMessageMemberByIds(List<Long> ids)
    {
        QueryWrapper<SiteMessageMember> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return siteMessageMemberMapper.delete(ew);
    }

    /**
     * 删除用户通知信息
     *
     * @param id 用户通知主键
     * @return 结果
     */
    @Override
    public int deleteSiteMessageMemberById(Long id)
    {
        return siteMessageMemberMapper.deleteById(id);
    }
}
