package com.ruoyi.stock.dashboard.service;

import com.ruoyi.stock.dashboard.vo.CountParam;
import com.ruoyi.stock.dashboard.vo.CountResult;

public interface IDashboardService {
    CountResult countFunds(CountParam param);

    CountResult countMember(CountParam param);

    CountResult countRecharge(CountParam param);

    CountResult countWithdrawal(CountParam param);
}
