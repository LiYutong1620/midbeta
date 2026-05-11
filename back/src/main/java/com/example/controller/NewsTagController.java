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
import com.example.domain.NewsTag;
import com.example.service.INewsTagService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 标签管理Controller
 * 
 * @author tong
 * @date 2026-05-09
 */
@RestController
@RequestMapping("/system/tag")
public class NewsTagController extends BaseController
{
    @Autowired
    private INewsTagService newsTagService;

    /**
     * 查询标签管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:tag:list')")
    @GetMapping("/list")
    public TableDataInfo list(NewsTag newsTag)
    {
        startPage();
        List<NewsTag> list = newsTagService.selectNewsTagList(newsTag);
        return getDataTable(list);
    }

    /**
     * 导出标签管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:tag:export')")
    @Log(title = "标签管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NewsTag newsTag)
    {
        List<NewsTag> list = newsTagService.selectNewsTagList(newsTag);
        ExcelUtil<NewsTag> util = new ExcelUtil<NewsTag>(NewsTag.class);
        util.exportExcel(response, list, "标签管理数据");
    }

    /**
     * 获取标签管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:tag:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(newsTagService.selectNewsTagById(id));
    }

    /**
     * 新增标签管理
     */
    @PreAuthorize("@ss.hasPermi('system:tag:add')")
    @Log(title = "标签管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NewsTag newsTag)
    {
        return toAjax(newsTagService.insertNewsTag(newsTag));
    }

    /**
     * 修改标签管理
     */
    @PreAuthorize("@ss.hasPermi('system:tag:edit')")
    @Log(title = "标签管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NewsTag newsTag)
    {
        return toAjax(newsTagService.updateNewsTag(newsTag));
    }

    /**
     * 删除标签管理
     */
    @PreAuthorize("@ss.hasPermi('system:tag:remove')")
    @Log(title = "标签管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(newsTagService.deleteNewsTagByIds(ids));
    }
}
