package com.example.controller;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import com.example.domain.News;
import com.example.service.INewsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 新闻资讯Controller
 *
 * @author tong
 * @date 2026-05-08
 */
@Tag(name = "新闻资讯", description = "新闻发布、查询、推荐等接口")
@RestController
@RequestMapping("/system/news")
public class NewsController extends BaseController
{
    @Autowired
    private INewsService newsService;

    /**
     * 查询新闻资讯列表
     */
    @Operation(summary = "查询新闻列表(管理端)")
    @PreAuthorize("@ss.hasPermi('system:news:list')")
    @GetMapping("/list")
    public TableDataInfo list(News news)
    {
        startPage();
        List<News> list = newsService.selectNewsList(news);
        return getDataTable(list);
    }

    /**
     * 前台查询可用新闻列表
     */
    @Operation(summary = "前台新闻列表(分页)")
    @GetMapping("/public/list")
    public TableDataInfo publicList(News news)
    {
        news.setPublishStatus(1); // 已发布
        news.setShelfStatus(1);   // 上架
        news.setIsDeleted(0);     // 未删除
        startPage();
        List<News> list = newsService.selectNewsList(news);
        return getDataTable(list);
    }

    /**
     * 导出新闻资讯列表
     */
    @PreAuthorize("@ss.hasPermi('system:news:export')")
    @Log(title = "新闻资讯", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, News news)
    {
        List<News> list = newsService.selectNewsList(news);
        ExcelUtil<News> util = new ExcelUtil<News>(News.class);
        util.exportExcel(response, list, "新闻资讯数据");
    }

    /**
     * 获取新闻资讯详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:news:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(newsService.selectNewsById(id));
    }

    /**
     * 新增新闻资讯
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Log(title = "新闻资讯", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody News news)
    {
        return toAjax(newsService.insertNews(news));
    }

    /**
     * 修改新闻资讯
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Log(title = "新闻资讯", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody News news)
    {
        return toAjax(newsService.updateNews(news));
    }

    /**
     * 删除新闻资讯
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Log(title = "新闻资讯", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(newsService.deleteNewsByIds(ids));
    }

    @Autowired
    private com.ruoyi.common.core.redis.RedisCache redisCache;

    /**
     * 增强推荐：同分类推荐 + 基于 ZSET 历史记录的兴趣推荐
     */
    @Operation(summary = "获取推荐新闻", description = "包含同分类推荐和基于用户历史的兴趣推荐")
    @GetMapping("/recommend/{newsId}")
    public AjaxResult getRecommendList(@PathVariable("newsId") Long newsId)
    {
        // 1. 获取当前新闻详情
        News currentNews = newsService.selectNewsById(newsId);
        if (currentNews == null) {
            return AjaxResult.error("新闻不存在");
        }

        // 2. 获取用户历史记录 (Redis 缓存应用 d)
        Long userId = null;
        try {
            userId = com.ruoyi.common.utils.SecurityUtils.getUserId();
        } catch (Exception e) {}

        List<News> finalRecommendList = new java.util.ArrayList<>();

        // 策略 A: 同分类推荐 (题目核心要求)
        News catQuery = new News();
        catQuery.setCategoryId(currentNews.getCategoryId());
        catQuery.setPublishStatus(1);
        catQuery.setShelfStatus(1);
        List<News> catList = newsService.selectNewsList(catQuery);
        List<News> sameCatRecommended = catList.stream()
                .filter(n -> !n.getId().equals(newsId))
                .limit(3)
                .collect(Collectors.toList());
        finalRecommendList.addAll(sameCatRecommended);

        // 策略 B: 基于历史足迹计算 (体现 ZSET 价值)
        if (userId != null && finalRecommendList.size() < 10) {
            String historyKey = "user:history:" + userId;
            // 获取最近看过的 10 个 ID
            java.util.Set<Object> historyIds = redisCache.redisTemplate.opsForZSet().reverseRange(historyKey, 0, 9);
            if (historyIds != null && !historyIds.isEmpty()) {
                // 找到历史新闻所属的分类
                java.util.Set<Long> interestedCategoryIds = new java.util.HashSet<>();
                for (Object hId : historyIds) {
                    Long hidLong = Long.valueOf(hId.toString());
                    News hNews = newsService.selectNewsById(hidLong);
                    if (hNews != null) {
                        interestedCategoryIds.add(hNews.getCategoryId());
                    }
                }

                // 从这些感兴趣的分类中抽取非当前分类的新闻
                for (Long catId : interestedCategoryIds) {
                    if (finalRecommendList.size() >= 8) break;
                    if (catId.equals(currentNews.getCategoryId())) continue;

                    News interestQuery = new News();
                    interestQuery.setCategoryId(catId);
                    interestQuery.setPublishStatus(1);
                    interestQuery.setShelfStatus(1);
                    List<News> interestList = newsService.selectNewsList(interestQuery);

                    interestList.stream()
                            .filter(n -> !n.getId().equals(newsId) && finalRecommendList.stream().noneMatch(r -> r.getId().equals(n.getId())))
                            .limit(2)
                            .forEach(finalRecommendList::add);
                }
            }
        }

        // 3. 补足兜底逻辑：如果还不够，取最新发布的
        if (finalRecommendList.size() < 5) {
            News latestQuery = new News();
            latestQuery.setPublishStatus(1);
            latestQuery.setShelfStatus(1);
            List<News> latestList = newsService.selectNewsList(latestQuery);
            latestList.stream()
                    .filter(n -> !n.getId().equals(newsId) && finalRecommendList.stream().noneMatch(r -> r.getId().equals(n.getId())))
                    .limit(5 - finalRecommendList.size())
                    .forEach(finalRecommendList::add);
        }

        return AjaxResult.success(finalRecommendList);
    }

}
