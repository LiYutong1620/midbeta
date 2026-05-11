package com.example.mapper;

import java.util.List;
import com.example.domain.NewsStatistics;
import org.apache.ibatis.annotations.Update;

/**
 * 新闻统计Mapper接口
 * 
 * @author tong
 * @date 2026-05-09
 */
public interface NewsStatisticsMapper 
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
     * 删除新闻统计
     * 
     * @param id 新闻统计主键
     * @return 结果
     */
    public int deleteNewsStatisticsById(Long id);

    /**
     * 批量删除新闻统计
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNewsStatisticsByIds(Long[] ids);

    /**
     * 调用存储过程：将 RedisSyncTemp 数据批量同步回统计表
     */
    @Update("CALL sync_news_statistics()")
    public void executeSyncProcedure();
}
