package com.ruoyi.stock.dashboard.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.stock.dashboard.service.IDashboardService;
import com.ruoyi.stock.dashboard.vo.CountParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard/count")
public class DashboardController extends BaseController {

    @Autowired
    private IDashboardService dashboardService;

    @GetMapping("/member")
    public AjaxResult countMember(CountParam param){
        return success(dashboardService.countMember(param));
    }

    @GetMapping("/funds")
    public AjaxResult countFunds(CountParam param){
        return success(dashboardService.countFunds(param));
    }

    @GetMapping("/recharge")
    public AjaxResult countRecharge(CountParam param){
        return success(dashboardService.countRecharge(param));
    }

    @GetMapping("/withdrawal")
    public AjaxResult countWithdrawal(CountParam param){
        return success(dashboardService.countWithdrawal(param));
    }


}
