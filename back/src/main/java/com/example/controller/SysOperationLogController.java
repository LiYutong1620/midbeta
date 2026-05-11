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
import com.example.domain.SysOperationLog;
import com.example.service.ISysOperationLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 操作日志Controller
 * 
 * @author tong
 * @date 2026-05-08
 */
@RestController
@RequestMapping("/system/operlog")
public class SysOperationLogController extends BaseController
{
    @Autowired
    private ISysOperationLogService sysOperationLogService;

    /**
     * 查询操作日志列表
     */
    @PreAuthorize("@ss.hasPermi('system:operlog:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysOperationLog sysOperationLog)
    {
        startPage();
        List<SysOperationLog> list = sysOperationLogService.selectSysOperationLogList(sysOperationLog);
        return getDataTable(list);
    }

    /**
     * 导出操作日志列表
     */
    @PreAuthorize("@ss.hasPermi('system:operlog:export')")
    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysOperationLog sysOperationLog)
    {
        List<SysOperationLog> list = sysOperationLogService.selectSysOperationLogList(sysOperationLog);
        ExcelUtil<SysOperationLog> util = new ExcelUtil<SysOperationLog>(SysOperationLog.class);
        util.exportExcel(response, list, "操作日志数据");
    }

    /**
     * 获取操作日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:operlog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysOperationLogService.selectSysOperationLogById(id));
    }

    /**
     * 新增操作日志
     */
    @PreAuthorize("@ss.hasPermi('system:operlog:add')")
    @Log(title = "操作日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysOperationLog sysOperationLog)
    {
        return toAjax(sysOperationLogService.insertSysOperationLog(sysOperationLog));
    }

    /**
     * 修改操作日志
     */
    @PreAuthorize("@ss.hasPermi('system:operlog:edit')")
    @Log(title = "操作日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysOperationLog sysOperationLog)
    {
        return toAjax(sysOperationLogService.updateSysOperationLog(sysOperationLog));
    }

    /**
     * 删除操作日志
     */
    @PreAuthorize("@ss.hasPermi('system:operlog:remove')")
    @Log(title = "操作日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysOperationLogService.deleteSysOperationLogByIds(ids));
    }
}
