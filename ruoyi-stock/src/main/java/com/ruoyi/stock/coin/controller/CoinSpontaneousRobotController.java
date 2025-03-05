package com.ruoyi.stock.coin.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.stock.coin.domain.CoinSpontaneousRobot;
import com.ruoyi.stock.coin.service.ICoinSpontaneousRobotService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 机器人Controller
 *
 * @author ruoyi
 * @date 2024-12-21
 */
@RestController
@RequestMapping("/coin/robot")
public class CoinSpontaneousRobotController extends BaseController
{
    @Autowired
    private ICoinSpontaneousRobotService coinSpontaneousRobotService;

    /**
     * 查询机器人列表
     */
    @PreAuthorize("@ss.hasPermi('coin:robot:list')")
    @GetMapping("/list")
    public TableDataInfo list(CoinSpontaneousRobot coinSpontaneousRobot)
    {
        startPage();
        return getDataTable(coinSpontaneousRobotService.selectCoinSpontaneousRobotList(coinSpontaneousRobot));
    }

    /**
     * 导出机器人列表
     */
    @PreAuthorize("@ss.hasPermi('coin:robot:export')")
    @Log(title = "机器人", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CoinSpontaneousRobot coinSpontaneousRobot)
    {
        List<CoinSpontaneousRobot> list = coinSpontaneousRobotService.selectCoinSpontaneousRobotList(coinSpontaneousRobot);
        ExcelUtil<CoinSpontaneousRobot> util = new ExcelUtil<CoinSpontaneousRobot>(CoinSpontaneousRobot.class);
        util.exportExcel(response, list, "机器人数据");
    }

    /**
     * 获取机器人详细信息
     */
    @PreAuthorize("@ss.hasPermi('coin:robot:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(coinSpontaneousRobotService.selectCoinSpontaneousRobotById(id));
    }

    /**
     * 新增机器人
     */
    @PreAuthorize("@ss.hasPermi('coin:robot:add')")
    @Log(title = "机器人", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CoinSpontaneousRobot coinSpontaneousRobot)
    {
        return toAjax(coinSpontaneousRobotService.insertCoinSpontaneousRobot(coinSpontaneousRobot));
    }

    /**
     * 修改机器人
     */
    @PreAuthorize("@ss.hasPermi('coin:robot:edit')")
    @Log(title = "机器人", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CoinSpontaneousRobot coinSpontaneousRobot)
    {
        return toAjax(coinSpontaneousRobotService.updateCoinSpontaneousRobot(coinSpontaneousRobot));
    }

    /**
     * 删除机器人
     */
    @PreAuthorize("@ss.hasPermi('coin:robot:remove')")
    @Log(title = "机器人", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<Long> ids)
    {
        return toAjax(coinSpontaneousRobotService.deleteCoinSpontaneousRobotByIds(ids));
    }
}
