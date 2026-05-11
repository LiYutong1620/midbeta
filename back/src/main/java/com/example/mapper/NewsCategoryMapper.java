package com.example.mapper;

import java.util.List;
import com.example.domain.NewsCategory;

/**
 * 新闻分类Mapper接口
 * 
 * @author tong
 * @date 2026-05-08
 */
public interface NewsCategoryMapper 
{
    /**
     * 查询新闻分类
     * 
     * @param id 新闻分类主键
     * @return 新闻分类
     */
    public NewsCategory selectNewsCategoryById(Long id);

    /**
     * 查询新闻分类列表
     * 
     * @param newsCategory 新闻分类
     * @return 新闻分类集合
     */
    public List<NewsCategory> selectNewsCategoryList(NewsCategory newsCategory);

    /**
     * 新增新闻分类
     * 
     * @param newsCategory 新闻分类
     * @return 结果
     */
    public int insertNewsCategory(NewsCategory newsCategory);

    /**
     * 修改新闻分类
     * 
     * @param newsCategory 新闻分类
     * @return 结果
     */
    public int updateNewsCategory(NewsCategory newsCategory);

    /**
     * 删除新闻分类
     * 
     * @param id 新闻分类主键
     * @return 结果
     */
    public int deleteNewsCategoryById(Long id);

    /**
     * 批量删除新闻分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNewsCategoryByIds(Long[] ids);
}
