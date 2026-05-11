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
import com.example.domain.NewsTagRel;
import com.example.service.INewsTagRelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 新闻标签关联Controller
 * 
 * @author tong
 * @date 2026-05-09
 */
@RestController
@RequestMapping("/system/tagRel")
public class NewsTagRelController extends BaseController
{
    @Autowired
    private INewsTagRelService newsTagRelService;

    /**
     * 查询新闻标签关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:tagRel:list')")
    @GetMapping("/list")
    public TableDataInfo list(NewsTagRel newsTagRel)
    {
        startPage();
        List<NewsTagRel> list = newsTagRelService.selectNewsTagRelList(newsTagRel);
        return getDataTable(list);
    }

    /**
     * 导出新闻标签关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:tagRel:export')")
    @Log(title = "新闻标签关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NewsTagRel newsTagRel)
    {
        List<NewsTagRel> list = newsTagRelService.selectNewsTagRelList(newsTagRel);
        ExcelUtil<NewsTagRel> util = new ExcelUtil<NewsTagRel>(NewsTagRel.class);
        util.exportExcel(response, list, "新闻标签关联数据");
    }

    /**
     * 获取新闻标签关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:tagRel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(newsTagRelService.selectNewsTagRelById(id));
    }

    /**
     * 新增新闻标签关联
     */
    @PreAuthorize("@ss.hasPermi('system:tagRel:add')")
    @Log(title = "新闻标签关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NewsTagRel newsTagRel)
    {
        return toAjax(newsTagRelService.insertNewsTagRel(newsTagRel));
    }

    /**
     * 修改新闻标签关联
     */
    @PreAuthorize("@ss.hasPermi('system:tagRel:edit')")
    @Log(title = "新闻标签关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NewsTagRel newsTagRel)
    {
        return toAjax(newsTagRelService.updateNewsTagRel(newsTagRel));
    }

    /**
     * 删除新闻标签关联
     */
    @PreAuthorize("@ss.hasPermi('system:tagRel:remove')")
    @Log(title = "新闻标签关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(newsTagRelService.deleteNewsTagRelByIds(ids));
    }
}
