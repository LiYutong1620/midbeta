package com.example.task;

import com.example.domain.RedisSyncTemp;
import com.example.mapper.NewsStatisticsMapper;
import com.example.service.IRedisSyncTempService;
import com.ruoyi.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Redis 数据定时同步任务
 */
@Component
public class RedisSyncTask {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IRedisSyncTempService tempService;

    @Autowired
    private NewsStatisticsMapper statisticsMapper;

    /**
     * 每5分钟同步一次计数器 (Redis 缓存应用 b)
     */
    @Scheduled(fixedDelay = 300000)
    public void syncNewsCounters() {
        try {
            List<RedisSyncTemp> tempList = new ArrayList<>();

            // 1. 获取所有阅读量 Key: news:read_count:{id}
            Collection<String> readKeys = redisCache.keys("news:read_count:*");
            for (String key : readKeys) {
                Long newsId = Long.valueOf(key.split(":")[2]);
                Object val = redisCache.redisTemplate.opsForValue().get(key);
                if (val != null) {
                    RedisSyncTemp temp = new RedisSyncTemp();
                    temp.setNewsId(newsId);
                    temp.setType("view");
                    temp.setValue(val.toString());
                    tempList.add(temp);
                }
            }

            // 2. 获取所有点赞量 Key: news:like_count:{id}
            Collection<String> likeKeys = redisCache.keys("news:like_count:*");
            for (String key : likeKeys) {
                Long newsId = Long.valueOf(key.split(":")[2]);
                Object val = redisCache.redisTemplate.opsForValue().get(key);
                if (val != null) {
                    RedisSyncTemp temp = new RedisSyncTemp();
                    temp.setNewsId(newsId);
                    temp.setType("like");
                    temp.setValue(val.toString());
                    tempList.add(temp);
                }
            }

            // 3. 执行同步
            if (!tempList.isEmpty()) {
                tempService.truncateAll();
                tempService.insertRedisSyncTempBatch(tempList);
                statisticsMapper.executeSyncProcedure();
                System.out.println(">>> [RedisSyncTask] 成功同步 " + tempList.size() + " 条计数数据。");
            }
        } catch (Exception e) {
            System.err.println(">>> [RedisSyncTask] 同步失败: " + e.getMessage());
        }
    }
}
