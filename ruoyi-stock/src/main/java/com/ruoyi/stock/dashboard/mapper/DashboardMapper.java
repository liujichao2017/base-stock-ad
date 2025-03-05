package com.ruoyi.stock.dashboard.mapper;


import com.ruoyi.stock.dashboard.vo.CountParam;
import com.ruoyi.stock.dashboard.vo.CountResult;

public interface DashboardMapper {

    CountResult countFunds(CountParam param);

    CountResult countMember(CountParam param);

    Long countMemberRecharge(CountParam param);

    CountResult countRecharge(CountParam param);

    CountResult countWithdrawal(CountParam param);
}
