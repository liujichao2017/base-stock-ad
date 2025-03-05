package com.ruoyi.stock.wite.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.wite.domain.WithinDaysRecord;
import com.ruoyi.stock.wite.domain.vo.WithinDaysRecordVo;
import org.apache.ibatis.annotations.Param;

/**
 * 用户日内交易记录Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-21
 */
public interface WithinDaysRecordMapper extends BaseMapper<WithinDaysRecord> {

    List<WithinDaysRecordVo> selectByList(@Param("ew") QueryWrapper<WithinDaysRecord> ew);

}
