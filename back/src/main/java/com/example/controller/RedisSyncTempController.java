package com.example.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
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
import com.example.domain.RedisSyncTemp;
import com.example.service.IRedisSyncTempService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * Redis同步中转Controller
 * 
 * @author tong
 * @date 2026-05-08
 */
@RestController
@RequestMapping("/system/sync")
public class RedisSyncTempController extends BaseController
{
    @Autowired
    private IRedisSyncTempService redisSyncTempService;

    /**
     * 查询Redis同步中转列表
     */
    @PreAuthorize("@ss.hasPermi('system:sync:list')")
    @GetMapping("/list")
    public TableDataInfo list(RedisSyncTemp redisSyncTemp)
    {
        startPage();
        List<RedisSyncTemp> list = redisSyncTempService.selectRedisSyncTempList(redisSyncTemp);
        return getDataTable(list);
    }

    /**
     * 导出Redis同步中转列表
     */
    @PreAuthorize("@ss.hasPermi('system:sync:export')")
    @Log(title = "Redis同步中转", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RedisSyncTemp redisSyncTemp)
    {
        List<RedisSyncTemp> list = redisSyncTempService.selectRedisSyncTempList(redisSyncTemp);
        ExcelUtil<RedisSyncTemp> util = new ExcelUtil<RedisSyncTemp>(RedisSyncTemp.class);
        util.exportExcel(response, list, "Redis同步中转数据");
    }

    /**
     * 获取Redis同步中转详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:sync:query')")
    @GetMapping(value = "/{newsId}")
    public AjaxResult getInfo(@PathVariable("newsId") Long newsId)
    {
        return success(redisSyncTempService.selectRedisSyncTempByNewsId(newsId));
    }

    /**
     * 新增Redis同步中转
     */
    @PreAuthorize("@ss.hasPermi('system:sync:add')")
    @Log(title = "Redis同步中转", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RedisSyncTemp redisSyncTemp)
    {
        return toAjax(redisSyncTempService.insertRedisSyncTemp(redisSyncTemp));
    }

    /**
     * 修改Redis同步中转
     */
    @PreAuthorize("@ss.hasPermi('system:sync:edit')")
    @Log(title = "Redis同步中转", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RedisSyncTemp redisSyncTemp)
    {
        return toAjax(redisSyncTempService.updateRedisSyncTemp(redisSyncTemp));
    }

    /**
     * 删除Redis同步中转
     */
    @PreAuthorize("@ss.hasPermi('system:sync:remove')")
    @Log(title = "Redis同步中转", businessType = BusinessType.DELETE)
	@DeleteMapping("/{newsIds}")
    public AjaxResult remove(@PathVariable Long[] newsIds)
    {
        return toAjax(redisSyncTempService.deleteRedisSyncTempByNewsIds(newsIds));
    }
}
