package com.ruoyi.stock.member.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.stock.member.domain.Member;
import org.apache.ibatis.annotations.Param;

/**
 * 会员Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-19
 */
public interface MemberMapper extends BaseMapper<Member> {

    List<Member> selectByList(@Param("ew") QueryWrapper<Member> ew);
}
