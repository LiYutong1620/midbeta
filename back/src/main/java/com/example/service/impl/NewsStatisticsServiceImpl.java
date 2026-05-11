package com.example.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapper.NewsStatisticsMapper;
import com.example.domain.NewsStatistics;
import com.example.service.INewsStatisticsService;

/**
 * 新闻统计Service业务层处理
 * 
 * @author tong
 * @date 2026-05-09
 */
@Service
public class NewsStatisticsServiceImpl implements INewsStatisticsService 
{
    @Autowired
    private NewsStatisticsMapper newsStatisticsMapper;

    /**
     * 查询新闻统计
     * 
     * @param id 新闻统计主键
     * @return 新闻统计
     */
    @Override
    public NewsStatistics selectNewsStatisticsById(Long id)
    {
        return newsStatisticsMapper.selectNewsStatisticsById(id);
    }

    /**
     * 查询新闻统计列表
     * 
     * @param newsStatistics 新闻统计
     * @return 新闻统计
     */
    @Override
    public List<NewsStatistics> selectNewsStatisticsList(NewsStatistics newsStatistics)
    {
        return newsStatisticsMapper.selectNewsStatisticsList(newsStatistics);
    }

    /**
     * 新增新闻统计
     * 
     * @param newsStatistics 新闻统计
     * @return 结果
     */
    @Override
    public int insertNewsStatistics(NewsStatistics newsStatistics)
    {
        return newsStatisticsMapper.insertNewsStatistics(newsStatistics);
    }

    /**
     * 修改新闻统计
     * 
     * @param newsStatistics 新闻统计
     * @return 结果
     */
    @Override
    public int updateNewsStatistics(NewsStatistics newsStatistics)
    {
        return newsStatisticsMapper.updateNewsStatistics(newsStatistics);
    }

    /**
     * 批量删除新闻统计
     * 
     * @param ids 需要删除的新闻统计主键
     * @return 结果
     */
    @Override
    public int deleteNewsStatisticsByIds(Long[] ids)
    {
        return newsStatisticsMapper.deleteNewsStatisticsByIds(ids);
    }

    /**
     * 删除新闻统计信息
     * 
     * @param id 新闻统计主键
     * @return 结果
     */
    @Override
    public int deleteNewsStatisticsById(Long id)
    {
        return newsStatisticsMapper.deleteNewsStatisticsById(id);
    }
}
