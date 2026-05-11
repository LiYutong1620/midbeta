package com.example.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapper.RedisSyncTempMapper;
import com.example.domain.RedisSyncTemp;
import com.example.service.IRedisSyncTempService;

/**
 * Redis同步中转Service业务层处理
 *
 * @author tong
 * @date 2026-05-08
 */
@Service
public class RedisSyncTempServiceImpl implements IRedisSyncTempService
{
    @Autowired
    private RedisSyncTempMapper redisSyncTempMapper;

    /**
     * 查询Redis同步中转
     *
     * @param newsId Redis同步中转主键
     * @return Redis同步中转
     */
    @Override
    public RedisSyncTemp selectRedisSyncTempByNewsId(Long newsId)
    {
        return redisSyncTempMapper.selectRedisSyncTempByNewsId(newsId);
    }

    /**
     * 查询Redis同步中转列表
     *
     * @param redisSyncTemp Redis同步中转
     * @return Redis同步中转
     */
    @Override
    public List<RedisSyncTemp> selectRedisSyncTempList(RedisSyncTemp redisSyncTemp)
    {
        return redisSyncTempMapper.selectRedisSyncTempList(redisSyncTemp);
    }

    /**
     * 新增Redis同步中转
     *
     * @param redisSyncTemp Redis同步中转
     * @return 结果
     */
    @Override
    public int insertRedisSyncTemp(RedisSyncTemp redisSyncTemp)
    {
        return redisSyncTempMapper.insertRedisSyncTemp(redisSyncTemp);
    }

    /**
     * 修改Redis同步中转
     *
     * @param redisSyncTemp Redis同步中转
     * @return 结果
     */
    @Override
    public int updateRedisSyncTemp(RedisSyncTemp redisSyncTemp)
    {
        return redisSyncTempMapper.updateRedisSyncTemp(redisSyncTemp);
    }

    /**
     * 批量删除Redis同步中转
     *
     * @param newsIds 需要删除的Redis同步中转主键
     * @return 结果
     */
    @Override
    public int deleteRedisSyncTempByNewsIds(Long[] newsIds)
    {
        return redisSyncTempMapper.deleteRedisSyncTempByNewsIds(newsIds);
    }

    /**
     * 删除Redis同步中转信息
     *
     * @param newsId Redis同步中转主键
     * @return 结果
     */
    @Override
    public int deleteRedisSyncTempByNewsId(Long newsId)
    {
        return redisSyncTempMapper.deleteRedisSyncTempByNewsId(newsId);
    }


    @Override
    public int insertRedisSyncTempBatch(List<RedisSyncTemp> list) {
        return redisSyncTempMapper.batchInsert(list);
    }

    @Override
    public int insertBatch(List<RedisSyncTemp> list) {
        if (list == null || list.isEmpty()) return 0;
        return redisSyncTempMapper.batchInsert(list);
    }

    @Override
    public void truncateAll() {
        redisSyncTempMapper.truncateAll();
    }
}
