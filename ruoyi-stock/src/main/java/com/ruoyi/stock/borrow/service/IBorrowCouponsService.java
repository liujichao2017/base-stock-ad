package com.ruoyi.stock.borrow.service;

import java.util.List;
import com.ruoyi.stock.borrow.domain.BorrowCoupons;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 存股借卷配置Service接口
 *
 * @author ruoyi
 * @date 2024-12-11
 */
public interface IBorrowCouponsService
{
    /**
     * 查询存股借卷配置
     *
     * @param id 存股借卷配置主键
     * @return 存股借卷配置
     */
    public BorrowCoupons selectBorrowCouponsById(Long id);

    /**
     * 查询存股借卷配置列表
     *
     * @param borrowCoupons 存股借卷配置
     * @return 存股借卷配置集合
     */
    public List<BorrowCoupons> selectBorrowCouponsList(BorrowCoupons borrowCoupons);

    /**
     * 新增存股借卷配置
     *
     * @param borrowCoupons 存股借卷配置
     * @return 结果
     */
    public int insertBorrowCoupons(BorrowCoupons borrowCoupons);

    /**
     * 修改存股借卷配置
     *
     * @param borrowCoupons 存股借卷配置
     * @return 结果
     */
    public int updateBorrowCoupons(BorrowCoupons borrowCoupons);

    /**
     * 批量删除存股借卷配置
     *
     * @param ids 需要删除的存股借卷配置主键集合
     * @return 结果
     */
    public int deleteBorrowCouponsByIds(List<Long> ids);

    /**
     * 删除存股借卷配置信息
     *
     * @param id 存股借卷配置主键
     * @return 结果
     */
    public int deleteBorrowCouponsById(Long id);
}
