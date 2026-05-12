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
import com.example.domain.NewsLike;
import com.example.service.INewsLikeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 点赞记录Controller
 * 
 * @author tong
 * @date 2026-05-08
 */
@RestController
@RequestMapping("/system/like")
public class NewsLikeController extends BaseController
{
    @Autowired
    private INewsLikeService newsLikeService;

    /**
     * 查询点赞记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:like:list')")
    @GetMapping("/list")
    public TableDataInfo list(NewsLike newsLike)
    {
        startPage();
        List<NewsLike> list = newsLikeService.selectNewsLikeList(newsLike);
        return getDataTable(list);
    }

    /**
     * 导出点赞记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:like:export')")
    @Log(title = "点赞记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NewsLike newsLike)
    {
        List<NewsLike> list = newsLikeService.selectNewsLikeList(newsLike);
        ExcelUtil<NewsLike> util = new ExcelUtil<NewsLike>(NewsLike.class);
        util.exportExcel(response, list, "点赞记录数据");
    }

    /**
     * 获取点赞记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:like:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(newsLikeService.selectNewsLikeById(id));
    }

    /**
     * 新增点赞记录
     */
    @Log(title = "点赞记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NewsLike newsLike) {
        newsLike.setUserId(SecurityUtils.getUserId());
        return toAjax(newsLikeService.insertNewsLike(newsLike));
    }

    /**
     * 修改点赞记录
     */
    @PreAuthorize("@ss.hasPermi('system:like:edit')")
    @Log(title = "点赞记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NewsLike newsLike)
    {
        return toAjax(newsLikeService.updateNewsLike(newsLike));
    }

    /**
     * 删除点赞记录
     */
    @PreAuthorize("@ss.hasPermi('system:like:remove')")
    @Log(title = "点赞记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(newsLikeService.deleteNewsLikeByIds(ids));
    }
}
