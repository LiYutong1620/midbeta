package com.example.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapper.NewsMapper;
import com.example.domain.News;
import com.example.service.INewsService;

/**
 * 新闻资讯Service业务层处理
 *
 * @author tong
 * @date 2026-05-08
 */
@Service
public class NewsServiceImpl implements INewsService
{
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private NewsMapper newsMapper;

    private static final String CACHE_KEY_PREFIX = "news:detail:";
    private static final String HOME_LIST_KEY = "news:home_list";
    /**
     * 查询新闻资讯
     *
     * @param id 新闻资讯主键
     * @return 新闻资讯
     */
    /**
     * 获取新闻详情
     */
    @Override
    public News selectNewsById(Long id)
    {
        // 1. 获取当前用户ID（用于记录足迹）
        Long userId = null;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            // 未登录用户不记录足迹
        }

        // 2. 查询缓存（Redis 缓存应用 a: 缓解大文本压力）
        String cacheKey = CACHE_KEY_PREFIX + id;
        News news = redisCache.getCacheObject(cacheKey);

        if (news == null) {
            // 缓存失效，查询数据库
            news = newsMapper.selectNewsById(id);
            if (news != null) {
                // 存入缓存，设置2小时过期
                redisCache.setCacheObject(cacheKey, news, 2, TimeUnit.HOURS);
            }
        }

        if (news != null) {
            // 3. 阅读量 Redis 计数器自增 (Redis 缓存应用 b)
            redisCache.redisTemplate.opsForValue().increment("news:read_count:" + id);

            // 4. 用户行为记录：更新 Redis ZSET 历史记录 (Redis 缓存应用 d)
            if (userId != null) {
                String historyKey = "user:history:" + userId;
                // 以当前时间戳为 Score，新闻 ID 为 Value
                redisCache.redisTemplate.opsForZSet().add(historyKey, id, System.currentTimeMillis());
                // 只保留最近的 10 条（可选逻辑）
                redisCache.redisTemplate.opsForZSet().removeRange(historyKey, 0, -11);
            }
        }

        return news;
    }
    /**
     * 查询新闻资讯列表
     *
     * @param news 新闻资讯
     * @return 新闻资讯
     */
    @Override
    public List<News> selectNewsList(News news)
    {
        // 实现：首页新闻列表缓存 (Redis 缓存应用 c)
        // 判断是否为首页（无特定过滤条件时）
        boolean isHomePage = (news.getTitle() == null && news.getCategoryId() == null);

        if (isHomePage) {
            List<News> cacheList = redisCache.getCacheList(HOME_LIST_KEY);
            if (cacheList != null && !cacheList.isEmpty()) {
                return cacheList;
            }
        }

        List<News> list = newsMapper.selectNewsList(news);

        if (isHomePage && list != null) {
            redisCache.setCacheList(HOME_LIST_KEY, list);
            redisCache.expire(HOME_LIST_KEY, 3600); // 缓存1小时
        }

        return list;
    }

    /**
     * 新增新闻资讯
     *
     * @param news 新闻资讯
     * @return 结果
     */
    @Override
    public int insertNews(News news)
    {
        news.setPublishUserId(SecurityUtils.getUserId()); // 自动获取当前登录人
        news.setCreatedAt(DateUtils.getNowDate());
        int rows = newsMapper.insertNews(news);
        if (rows > 0) {
            redisCache.deleteObject(HOME_LIST_KEY); // 新闻更新清理首页缓存（Redis 缓存应用 c）
        }
        return rows;
    }

    /**
     * 修改新闻资讯
     *
     * @param news 新闻资讯
     * @return 结果
     */
    @Override
    public int updateNews(News news)
    {
        news.setUpdatedAt(DateUtils.getNowDate());
        int rows = newsMapper.updateNews(news);
        if (rows > 0) {
            redisCache.deleteObject(CACHE_KEY_PREFIX + news.getId());
            redisCache.deleteObject(HOME_LIST_KEY);
        }
        return rows;
    }

    /**
     * 批量删除新闻资讯
     *
     * @param ids 需要删除的新闻资讯主键
     * @return 结果
     */
    @Override
    public int deleteNewsByIds(Long[] ids)
    {
        int rows = newsMapper.deleteNewsByIds(ids);
        if (rows > 0) {
            for (Long id : ids) {
                redisCache.deleteObject(CACHE_KEY_PREFIX + id);
            }
            redisCache.deleteObject(HOME_LIST_KEY);
        }
        return rows;
    }

    /**
     * 删除新闻资讯信息
     *
     * @param id 新闻资讯主键
     * @return 结果
     */
    @Override
    public int deleteNewsById(Long id)
    {
        int rows = newsMapper.deleteNewsById(id);
        if (rows > 0) {
            redisCache.deleteObject(CACHE_KEY_PREFIX + id);
            redisCache.deleteObject(HOME_LIST_KEY);
        }
        return rows;
    }
}
