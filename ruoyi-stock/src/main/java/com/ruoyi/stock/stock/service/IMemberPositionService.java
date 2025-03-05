package com.ruoyi.stock.stock.service;

import com.ruoyi.stock.stock.domain.MemberPosition;

import java.util.List;

/**
 * 用户持仓Service接口
 *
 * @author ruoyi
 * @date 2024-11-20
 */
public interface IMemberPositionService {
    /**
     * 查询用户持仓
     *
     * @param id 用户持仓主键
     * @return 用户持仓
     */
    public MemberPosition selectMemberPositionById(Long id);

    /**
     * 查询用户持仓列表
     *
     * @param memberPosition 用户持仓
     * @return 用户持仓集合
     */
    public List<MemberPosition> selectMemberPositionList(MemberPosition memberPosition);

    /**
     * 新增用户持仓
     *
     * @param memberPosition 用户持仓
     * @return 结果
     */
    public int insertMemberPosition(MemberPosition memberPosition);

    /**
     * 修改用户持仓
     *
     * @param memberPosition 用户持仓
     * @return 结果
     */
    public int updateMemberPosition(MemberPosition memberPosition);

    /**
     * 批量删除用户持仓
     *
     * @param ids 需要删除的用户持仓主键集合
     * @return 结果
     */
    public int deleteMemberPositionByIds(List<Long> ids);

    /**
     * 删除用户持仓信息
     *
     * @param id 用户持仓主键
     * @return 结果
     */
    public int deleteMemberPositionById(Long id);

    boolean sell(MemberPosition memberPosition);
}
