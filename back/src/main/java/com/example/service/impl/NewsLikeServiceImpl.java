package com.example.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapper.NewsLikeMapper;
import com.example.domain.NewsLike;
import com.example.service.INewsLikeService;

/**
 * 点赞记录Service业务层处理
 *
 * @author tong
 * @date 2026-05-08
 */
@Service
public class NewsLikeServiceImpl implements INewsLikeService
{
    @Autowired
    private NewsLikeMapper newsLikeMapper;

    @Autowired
    private RedisCache redisCache;
    /**
     * 查询点赞记录
     *
     * @param id 点赞记录主键
     * @return 点赞记录
     */
    @Override
    public NewsLike selectNewsLikeById(Long id)
    {
        return newsLikeMapper.selectNewsLikeById(id);
    }

    /**
     * 查询点赞记录列表
     *
     * @param newsLike 点赞记录
     * @return 点赞记录
     */
    @Override
    public List<NewsLike> selectNewsLikeList(NewsLike newsLike)
    {
        return newsLikeMapper.selectNewsLikeList(newsLike);
    }

    /**
     * 新增点赞记录
     *
     * @param newsLike 点赞记录
     * @return 结果
     */
    @Override
    public int insertNewsLike(NewsLike newsLike) {
        NewsLike exist = new NewsLike();
        exist.setUserId(newsLike.getUserId());
        exist.setNewsId(newsLike.getNewsId());
        List<NewsLike> existing = newsLikeMapper.selectNewsLikeList(exist);

        if (existing != null && !existing.isEmpty()) {
            NewsLike record = existing.get(0);
            int oldStatus = record.getLikeStatus() != null ? record.getLikeStatus() : 0;
            record.setLikeStatus(newsLike.getLikeStatus());
            record.setUpdatedAt(new Date());
            int rows = newsLikeMapper.updateNewsLike(record);
            if (rows > 0) {
                // 根据状态变化更新统计表
                if (newsLike.getLikeStatus() != null && newsLike.getLikeStatus() == 1 && oldStatus == 0) {
                    newsLikeMapper.incrementLikeCount(newsLike.getNewsId());
                    redisCache.redisTemplate.opsForValue().increment("news:like_count:" + newsLike.getNewsId());
                } else if (newsLike.getLikeStatus() != null && newsLike.getLikeStatus() == 0 && oldStatus == 1) {
                    newsLikeMapper.decrementLikeCount(newsLike.getNewsId());
                    redisCache.redisTemplate.opsForValue().decrement("news:like_count:" + newsLike.getNewsId());
                }
            }
            return rows;
        } else {
            int rows = newsLikeMapper.insertNewsLike(newsLike);
            if (rows > 0) {
                newsLikeMapper.incrementLikeCount(newsLike.getNewsId());
                redisCache.redisTemplate.opsForValue().increment("news:like_count:" + newsLike.getNewsId());
                redisCache.deleteObject("news:detail:" + newsLike.getNewsId());
                redisCache.deleteObject("news:home_list");

            }
            return rows;
        }
    }
    /**
     * 修改点赞记录
     *
     * @param newsLike 点赞记录
     * @return 结果
     */
    @Override
    public int updateNewsLike(NewsLike newsLike)
    {
        return newsLikeMapper.updateNewsLike(newsLike);
    }

    /**
     * 批量删除点赞记录
     *
     * @param ids 需要删除的点赞记录主键
     * @return 结果
     */
    @Override
    public int deleteNewsLikeByIds(Long[] ids)
    {
        for (Long id : ids) {
            deleteNewsLikeById(id);
        }
        return ids.length;
    }

    /**
     * 删除点赞记录信息
     *
     * @param id 点赞记录主键
     * @return 结果
     */
    @Override
    public int deleteNewsLikeById(Long id)
    {
        NewsLike like = newsLikeMapper.selectNewsLikeById(id);
        int rows = newsLikeMapper.deleteNewsLikeById(id);
        if (rows > 0 && like != null) {
            String key = "news:like_count:" + like.getNewsId();
            redisCache.redisTemplate.opsForValue().decrement(key);
        }
        return rows;
    }
}
