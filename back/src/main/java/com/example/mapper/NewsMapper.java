package com.example.mapper;

import java.util.List;
import com.example.domain.News;

/**
 * 新闻资讯Mapper接口
 * 
 * @author tong
 * @date 2026-05-08
 */
public interface NewsMapper 
{
    /**
     * 查询新闻资讯
     * 
     * @param id 新闻资讯主键
     * @return 新闻资讯
     */
    public News selectNewsById(Long id);

    /**
     * 查询新闻资讯列表
     * 
     * @param news 新闻资讯
     * @return 新闻资讯集合
     */
    public List<News> selectNewsList(News news);

    /**
     * 新增新闻资讯
     * 
     * @param news 新闻资讯
     * @return 结果
     */
    public int insertNews(News news);

    /**
     * 修改新闻资讯
     * 
     * @param news 新闻资讯
     * @return 结果
     */
    public int updateNews(News news);

    /**
     * 删除新闻资讯
     * 
     * @param id 新闻资讯主键
     * @return 结果
     */
    public int deleteNewsById(Long id);

    /**
     * 批量删除新闻资讯
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNewsByIds(Long[] ids);
}
