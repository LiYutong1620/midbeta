package com.example.service;

import java.util.List;
import com.example.domain.News;

/**
 * 新闻资讯Service接口
 * 
 * @author tong
 * @date 2026-05-08
 */
public interface INewsService 
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
     * 批量删除新闻资讯
     * 
     * @param ids 需要删除的新闻资讯主键集合
     * @return 结果
     */
    public int deleteNewsByIds(Long[] ids);

    /**
     * 删除新闻资讯信息
     * 
     * @param id 新闻资讯主键
     * @return 结果
     */
    public int deleteNewsById(Long id);
}
