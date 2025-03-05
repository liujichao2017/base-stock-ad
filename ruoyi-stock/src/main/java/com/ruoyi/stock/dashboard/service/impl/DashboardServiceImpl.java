package com.ruoyi.stock.dashboard.service.impl;

import com.ruoyi.common.utils.LogicUtils;
import com.ruoyi.stock.dashboard.mapper.DashboardMapper;
import com.ruoyi.stock.dashboard.service.IDashboardService;
import com.ruoyi.stock.dashboard.vo.CountParam;
import com.ruoyi.stock.dashboard.vo.CountResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements IDashboardService {

    @Autowired
    private DashboardMapper dashboardMapper;

    @Override
    public CountResult countFunds(CountParam param) {
        CountResult countResult = dashboardMapper.countFunds(param);
        countResult = LogicUtils.isNotNull(countResult) ? countResult : new CountResult();
        return countResult;
    }

    @Override
    public CountResult countMember(CountParam param) {
        CountResult countResult = dashboardMapper.countMember(param);
        if (LogicUtils.isNull(countResult)) {
            countResult = new CountResult();
        }
        countResult.setRechargeMemberNum(dashboardMapper.countMemberRecharge(param));
        return countResult;
    }

    @Override
    public CountResult countRecharge(CountParam param) {
        CountResult countResult = dashboardMapper.countRecharge(param);
        countResult = LogicUtils.isNotNull(countResult) ? countResult : new CountResult();
        return countResult;
    }

    @Override
    public CountResult countWithdrawal(CountParam param) {
        CountResult countResult = dashboardMapper.countWithdrawal(param);
        countResult = LogicUtils.isNotNull(countResult) ? countResult : new CountResult();
        return countResult;
    }
}
