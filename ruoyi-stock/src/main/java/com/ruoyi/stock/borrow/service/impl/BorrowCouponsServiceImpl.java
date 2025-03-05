package com.ruoyi.stock.borrow.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.stock.borrow.mapper.BorrowCouponsMapper;
import com.ruoyi.stock.borrow.domain.BorrowCoupons;
import com.ruoyi.stock.borrow.service.IBorrowCouponsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

/**
 * 存股借卷配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-11
 */
@Service
public class BorrowCouponsServiceImpl implements IBorrowCouponsService
{
    @Autowired
    private BorrowCouponsMapper borrowCouponsMapper;

    /**
     * 查询存股借卷配置
     *
     * @param id 存股借卷配置主键
     * @return 存股借卷配置
     */
    @Override
    public BorrowCoupons selectBorrowCouponsById(Long id) {
        return borrowCouponsMapper.selectById(id);
    }

    /**
     * 查询存股借卷配置列表
     *
     * @param borrowCoupons 存股借卷配置
     * @return 存股借卷配置
     */
    @Override
    public List<BorrowCoupons> selectBorrowCouponsList(BorrowCoupons borrowCoupons) {
        QueryWrapper<BorrowCoupons> ew = new QueryWrapper<>();
        ew.setEntity(borrowCoupons);
        ew.orderByDesc("create_time");
        return borrowCouponsMapper.selectList(ew);
    }

    /**
     * 新增存股借卷配置
     *
     * @param borrowCoupons 存股借卷配置
     * @return 结果
     */
    @Override
    public int insertBorrowCoupons(BorrowCoupons borrowCoupons)
    {
        borrowCoupons.setCreateTime(DateUtils.getNowDate());
        return borrowCouponsMapper.insert(borrowCoupons);
    }

    /**
     * 修改存股借卷配置
     *
     * @param borrowCoupons 存股借卷配置
     * @return 结果
     */
    @Override
    public int updateBorrowCoupons(BorrowCoupons borrowCoupons)
    {
        borrowCoupons.setUpdateTime(DateUtils.getNowDate());
        return borrowCouponsMapper.updateById(borrowCoupons);
    }

    /**
     * 批量删除存股借卷配置
     *
     * @param ids 需要删除的存股借卷配置主键
     * @return 结果
     */
    @Override
    public int deleteBorrowCouponsByIds(List<Long> ids)
    {
        QueryWrapper<BorrowCoupons> ew = new QueryWrapper<>();
        ew.in("id", ids);
        return borrowCouponsMapper.delete(ew);
    }

    /**
     * 删除存股借卷配置信息
     *
     * @param id 存股借卷配置主键
     * @return 结果
     */
    @Override
    public int deleteBorrowCouponsById(Long id)
    {
        return borrowCouponsMapper.deleteById(id);
    }
}
