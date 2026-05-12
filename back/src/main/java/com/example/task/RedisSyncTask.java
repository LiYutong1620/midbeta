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
import java.util.Set;

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

            // 阅读量
            Set<String> readKeys = redisCache.redisTemplate.keys("news:read_count:*");
            if (readKeys != null) {
                for (String key : readKeys) {
                    try {
                        Long newsId = Long.valueOf(key.split(":")[2]);
                        Object val = redisCache.redisTemplate.opsForValue().get(key);
                        if (val != null) {
                            RedisSyncTemp temp = new RedisSyncTemp();
                            temp.setNewsId(newsId);
                            temp.setType("view");
                            temp.setValue(val.toString());
                            tempList.add(temp);
                        }
                    } catch (Exception ignored) {}
                }
            }

            // 点赞量
            Set<String> likeKeys = redisCache.redisTemplate.keys("news:like_count:*");
            if (likeKeys != null) {
                for (String key : likeKeys) {
                    try {
                        Long newsId = Long.valueOf(key.split(":")[2]);
                        Object val = redisCache.redisTemplate.opsForValue().get(key);
                        if (val != null) {
                            RedisSyncTemp temp = new RedisSyncTemp();
                            temp.setNewsId(newsId);
                            temp.setType("like");
                            temp.setValue(val.toString());
                            tempList.add(temp);
                        }
                    } catch (Exception ignored) {}
                }
            }

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
