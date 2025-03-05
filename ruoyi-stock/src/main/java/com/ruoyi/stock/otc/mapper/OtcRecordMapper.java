package com.ruoyi.stock.otc.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.otc.domain.OtcRecord;
import com.ruoyi.stock.otc.domain.vo.OtcRecordVo;
import org.apache.ibatis.annotations.Param;

/**
 * otc(大宗)记录Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-21
 */
public interface OtcRecordMapper extends BaseMapper<OtcRecord> {

    List<OtcRecordVo> selectOtcRecordList(@Param("ew") QueryWrapper<OtcRecord> ew);

}
