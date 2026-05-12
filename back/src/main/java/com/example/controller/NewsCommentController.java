package com.example.controller;

import com.ruoyi.common.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import com.example.domain.NewsComment;
import com.example.service.INewsCommentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 评论审核Controller
 *
 * @author tong
 * @date 2026-05-08
 */
@Tag(name = "评论管理", description = "评论列表、审核、删除等")
@RestController
@RequestMapping("/system/comment")
public class NewsCommentController extends BaseController
{
    @Autowired
    private INewsCommentService newsCommentService;

    /**
     * 查询评论审核列表
     */
    @GetMapping("/list")
    public TableDataInfo list(NewsComment newsComment)
    {
        startPage();
        List<NewsComment> list = newsCommentService.selectNewsCommentList(newsComment);
        return getDataTable(list);
    }

    /**
     * 导出评论审核列表
     */
    @PreAuthorize("@ss.hasPermi('system:comment:export')")
    @Log(title = "评论审核", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NewsComment newsComment)
    {
        List<NewsComment> list = newsCommentService.selectNewsCommentList(newsComment);
        ExcelUtil<NewsComment> util = new ExcelUtil<NewsComment>(NewsComment.class);
        util.exportExcel(response, list, "评论审核数据");
    }

    /**
     * 获取评论审核详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:comment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(newsCommentService.selectNewsCommentById(id));
    }

    /**
     * 新增评论审核
     */
    @Log(title = "评论审核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NewsComment newsComment) {
        newsComment.setUserId(SecurityUtils.getUserId());
        return toAjax(newsCommentService.insertNewsComment(newsComment));
    }

    /**
     * 修改评论审核
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Log(title = "评论审核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NewsComment newsComment)
    {
        return toAjax(newsCommentService.updateNewsComment(newsComment));
    }

    /**
     * 删除评论审核
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Log(title = "评论审核", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(newsCommentService.deleteNewsCommentByIds(ids));
    }

    /**
     * 审核通过/隐藏评论
     */
    @Operation(summary = "审核/状态变更", description = "修改评论的审核状态或隐藏状态")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/audit/{id}/{status}")
    public AjaxResult audit(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        NewsComment comment = new NewsComment();
        comment.setId(id);
        comment.setAuditStatus(status);
        return toAjax(newsCommentService.updateNewsComment(comment));
    }
}
