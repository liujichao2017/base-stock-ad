package com.ruoyi.stock.borrow.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.borrow.domain.BorrowCoupons;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 存股借卷配置Mapper接口
 *
 * @author ruoyi
 * @date 2024-12-11
 */
public interface BorrowCouponsMapper extends BaseMapper<BorrowCoupons> {

    @Update("update borrow_coupons set borrow_number = borrow_number + #{applyNum} where id = #{id}")
    int addBorrowNumber(@Param("id") Long id, @Param("applyNum") Long applyNum);
}
