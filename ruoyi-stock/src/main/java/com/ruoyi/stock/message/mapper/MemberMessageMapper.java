package com.ruoyi.stock.message.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.message.domain.MemberMessage;
import org.apache.ibatis.annotations.Param;

/**
 * 客户消息Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-28
 */
public interface MemberMessageMapper extends BaseMapper<MemberMessage> {

    List<MemberMessage> selectByList(@Param("ew") QueryWrapper<MemberMessage> ew);
}
