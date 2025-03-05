package com.ruoyi.stock.funds.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.funds.domain.MemberFundsLogs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 资金动态Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-19
 */
public interface MemberFundsLogsMapper extends BaseMapper<MemberFundsLogs> {

    @Update("update member_funds_logs set visible = 1 where source = #{source} and operate_type = #{operateType}" +
            " and source_id = #{sourceId}")
    int updateVisible(@Param("source") String source, @Param("operateType") String operateType,
                      @Param("sourceId") Long sourceId);

    List<MemberFundsLogs> selectByList(@Param("ew") QueryWrapper<MemberFundsLogs> ew);
}
