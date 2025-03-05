package com.ruoyi.stock.member.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.member.mapper.MemberBankMapper;
import com.ruoyi.stock.member.domain.MemberBank;
import com.ruoyi.stock.member.service.IMemberBankService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 用户银行卡Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@Service
public class MemberBankServiceImpl implements IMemberBankService
{
    @Autowired
    private MemberBankMapper memberBankMapper;

    /**
     * 查询用户银行卡
     *
     * @param id 用户银行卡主键
     * @return 用户银行卡
     */
    @Override
    public MemberBank selectMemberBankById(Long id) {
        return memberBankMapper.selectById(id);
    }

    /**
     * 查询用户银行卡列表
     *
     * @param memberBank 用户银行卡
     * @return 用户银行卡
     */
    @Override
    public List<MemberBank> selectMemberBankList(MemberBank memberBank) {
        QueryWrapper<MemberBank> ew = new QueryWrapper<>();
        ew.setEntity(memberBank);
        return memberBankMapper.selectList(ew);
    }

    /**
     * 新增用户银行卡
     *
     * @param memberBank 用户银行卡
     * @return 结果
     */
    @Override
    public int insertMemberBank(MemberBank memberBank)
    {
        memberBank.setCreateTime(DateUtils.getNowDate());
        return memberBankMapper.insert(memberBank);
    }

    /**
     * 修改用户银行卡
     *
     * @param memberBank 用户银行卡
     * @return 结果
     */
    @Override
    public int updateMemberBank(MemberBank memberBank)
    {
        memberBank.setUpdateTime(DateUtils.getNowDate());
        return memberBankMapper.updateById(memberBank);
    }

    /**
     * 批量删除用户银行卡
     *
     * @param ids 需要删除的用户银行卡主键
     * @return 结果
     */
    @Override
    public int deleteMemberBankByIds(List<Long> ids)
    {
        QueryWrapper<MemberBank> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return memberBankMapper.delete(ew);
    }

    /**
     * 删除用户银行卡信息
     *
     * @param id 用户银行卡主键
     * @return 结果
     */
    @Override
    public int deleteMemberBankById(Long id)
    {
        return memberBankMapper.deleteById(id);
    }
}
