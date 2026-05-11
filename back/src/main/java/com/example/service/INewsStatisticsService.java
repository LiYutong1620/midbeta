package com.example.service;

import java.util.List;
import com.example.domain.NewsStatistics;

/**
 * 新闻统计Service接口
 * 
 * @author tong
 * @date 2026-05-09
 */
public interface INewsStatisticsService 
{
    /**
     * 查询新闻统计
     * 
     * @param id 新闻统计主键
     * @return 新闻统计
     */
    public NewsStatistics selectNewsStatisticsById(Long id);

    /**
     * 查询新闻统计列表
     * 
     * @param newsStatistics 新闻统计
     * @return 新闻统计集合
     */
    public List<NewsStatistics> selectNewsStatisticsList(NewsStatistics newsStatistics);

    /**
     * 新增新闻统计
     * 
     * @param newsStatistics 新闻统计
     * @return 结果
     */
    public int insertNewsStatistics(NewsStatistics newsStatistics);

    /**
     * 修改新闻统计
     * 
     * @param newsStatistics 新闻统计
     * @return 结果
     */
    public int updateNewsStatistics(NewsStatistics newsStatistics);

    /**
     * 批量删除新闻统计
     * 
     * @param ids 需要删除的新闻统计主键集合
     * @return 结果
     */
    public int deleteNewsStatisticsByIds(Long[] ids);

    /**
     * 删除新闻统计信息
     * 
     * @param id 新闻统计主键
     * @return 结果
     */
    public int deleteNewsStatisticsById(Long id);
}
