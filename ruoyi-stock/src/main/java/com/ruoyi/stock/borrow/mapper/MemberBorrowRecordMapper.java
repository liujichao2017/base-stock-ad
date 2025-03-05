package com.ruoyi.stock.borrow.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.borrow.domain.MemberBorrowRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 借劵记录Mapper接口
 *
 * @author ruoyi
 * @date 2024-12-11
 */
public interface MemberBorrowRecordMapper extends BaseMapper<MemberBorrowRecord> {

    List<MemberBorrowRecord> selectByList(@Param("ew")QueryWrapper<MemberBorrowRecord> ew);
}
