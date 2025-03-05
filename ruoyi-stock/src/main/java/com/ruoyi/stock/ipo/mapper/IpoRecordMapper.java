package com.ruoyi.stock.ipo.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.ipo.domain.IpoRecord;
import com.ruoyi.stock.ipo.domain.vo.IpoRecordVo;
import org.apache.ibatis.annotations.Param;

/**
 * IPO(新股)申购记录Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-21
 */
public interface IpoRecordMapper extends BaseMapper<IpoRecord> {


    List<IpoRecordVo> selectIpoRecordLiat(@Param("ew") QueryWrapper<IpoRecord> ew);
}
