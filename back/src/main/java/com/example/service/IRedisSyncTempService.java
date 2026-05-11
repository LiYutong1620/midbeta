package com.example.service;

import java.util.List;
import com.example.domain.RedisSyncTemp;

/**
 * Redis同步中转Service接口
 *
 * @author tong
 * @date 2026-05-08
 */
public interface IRedisSyncTempService
{
    /**
     * 查询Redis同步中转
     *
     * @param newsId Redis同步中转主键
     * @return Redis同步中转
     */
    public RedisSyncTemp selectRedisSyncTempByNewsId(Long newsId);

    /**
     * 查询Redis同步中转列表
     *
     * @param redisSyncTemp Redis同步中转
     * @return Redis同步中转集合
     */
    public List<RedisSyncTemp> selectRedisSyncTempList(RedisSyncTemp redisSyncTemp);

    /**
     * 新增Redis同步中转
     *
     * @param redisSyncTemp Redis同步中转
     * @return 结果
     */
    public int insertRedisSyncTemp(RedisSyncTemp redisSyncTemp);

    /**
     * 修改Redis同步中转
     *
     * @param redisSyncTemp Redis同步中转
     * @return 结果
     */
    public int updateRedisSyncTemp(RedisSyncTemp redisSyncTemp);

    /**
     * 批量删除Redis同步中转
     *
     * @param newsIds 需要删除的Redis同步中转主键集合
     * @return 结果
     */
    public int deleteRedisSyncTempByNewsIds(Long[] newsIds);

    /**
     * 删除Redis同步中转信息
     *
     * @param newsId Redis同步中转主键
     * @return 结果
     */
    public int deleteRedisSyncTempByNewsId(Long newsId);

    public int insertRedisSyncTempBatch(List<RedisSyncTemp> list);
    public int insertBatch(List<RedisSyncTemp> list);

    /**
     * 清空同步中转表
     */
    public void truncateAll();
}
