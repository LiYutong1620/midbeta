package com.example.mapper;

import java.util.List;
import com.example.domain.RedisSyncTemp;

/**
 * Redis同步中转Mapper接口
 *
 * @author tong
 * @date 2026-05-08
 */
public interface RedisSyncTempMapper
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
     * 删除Redis同步中转
     *
     * @param newsId Redis同步中转主键
     * @return 结果
     */
    public int deleteRedisSyncTempByNewsId(Long newsId);

    /**
     * 批量删除Redis同步中转
     *
     * @param newsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRedisSyncTempByNewsIds(Long[] newsIds);

    /**
     * 批量新增同步中转数据
     */
    public int batchInsert(List<RedisSyncTemp> list);

    /**
     * 清空同步中转表
     */
    public void truncateAll();
}
