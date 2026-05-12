package com.example.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.example.domain.UserBrowse;
import com.example.service.IUserBrowseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 浏览历史Controller
 * 
 * @author tong
 * @date 2026-05-08
 */
@RestController
@RequestMapping("/system/browse")
public class UserBrowseController extends BaseController
{
    @Autowired
    private IUserBrowseService userBrowseService;

    /**
     * 查询浏览历史列表
     */
    @PreAuthorize("@ss.hasPermi('system:browse:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserBrowse userBrowse)
    {
        startPage();
        List<UserBrowse> list = userBrowseService.selectUserBrowseList(userBrowse);
        return getDataTable(list);
    }

    /**
     * 导出浏览历史列表
     */
    @PreAuthorize("@ss.hasPermi('system:browse:export')")
    @Log(title = "浏览历史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserBrowse userBrowse)
    {
        List<UserBrowse> list = userBrowseService.selectUserBrowseList(userBrowse);
        ExcelUtil<UserBrowse> util = new ExcelUtil<UserBrowse>(UserBrowse.class);
        util.exportExcel(response, list, "浏览历史数据");
    }

    /**
     * 获取浏览历史详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:browse:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(userBrowseService.selectUserBrowseById(id));
    }

    /**
     * 新增浏览历史
     */
    @Log(title = "浏览历史", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserBrowse userBrowse) {
        userBrowse.setUserId(SecurityUtils.getUserId());          // 自动填入登录用户
        userBrowse.setBrowseTime(new java.util.Date());           // 设置浏览时间
        return toAjax(userBrowseService.insertUserBrowse(userBrowse));
    }

    /**
     * 修改浏览历史
     */
    @PreAuthorize("@ss.hasPermi('system:browse:edit')")
    @Log(title = "浏览历史", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserBrowse userBrowse)
    {
        return toAjax(userBrowseService.updateUserBrowse(userBrowse));
    }

    /**
     * 删除浏览历史
     */
    @PreAuthorize("@ss.hasPermi('system:browse:remove')")
    @Log(title = "浏览历史", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userBrowseService.deleteUserBrowseByIds(ids));
    }
}
