package com.ruoyi.stock.loan.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.loan.domain.MemberLoanRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 用户贷款申请Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-27
 */
public interface MemberLoanRecordMapper extends BaseMapper<MemberLoanRecord> {

    List<MemberLoanRecord> selectByList(@Param("ew") QueryWrapper<MemberLoanRecord> ew);
}
