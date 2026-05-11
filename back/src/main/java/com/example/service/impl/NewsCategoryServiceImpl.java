package com.example.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapper.NewsCategoryMapper;
import com.example.domain.NewsCategory;
import com.example.service.INewsCategoryService;

/**
 * 新闻分类Service业务层处理
 * 
 * @author tong
 * @date 2026-05-08
 */
@Service
public class NewsCategoryServiceImpl implements INewsCategoryService 
{
    @Autowired
    private NewsCategoryMapper newsCategoryMapper;

    /**
     * 查询新闻分类
     * 
     * @param id 新闻分类主键
     * @return 新闻分类
     */
    @Override
    public NewsCategory selectNewsCategoryById(Long id)
    {
        return newsCategoryMapper.selectNewsCategoryById(id);
    }

    /**
     * 查询新闻分类列表
     * 
     * @param newsCategory 新闻分类
     * @return 新闻分类
     */
    @Override
    public List<NewsCategory> selectNewsCategoryList(NewsCategory newsCategory)
    {
        return newsCategoryMapper.selectNewsCategoryList(newsCategory);
    }

    /**
     * 新增新闻分类
     * 
     * @param newsCategory 新闻分类
     * @return 结果
     */
    @Override
    public int insertNewsCategory(NewsCategory newsCategory)
    {
        return newsCategoryMapper.insertNewsCategory(newsCategory);
    }

    /**
     * 修改新闻分类
     * 
     * @param newsCategory 新闻分类
     * @return 结果
     */
    @Override
    public int updateNewsCategory(NewsCategory newsCategory)
    {
        return newsCategoryMapper.updateNewsCategory(newsCategory);
    }

    /**
     * 批量删除新闻分类
     * 
     * @param ids 需要删除的新闻分类主键
     * @return 结果
     */
    @Override
    public int deleteNewsCategoryByIds(Long[] ids)
    {
        return newsCategoryMapper.deleteNewsCategoryByIds(ids);
    }

    /**
     * 删除新闻分类信息
     * 
     * @param id 新闻分类主键
     * @return 结果
     */
    @Override
    public int deleteNewsCategoryById(Long id)
    {
        return newsCategoryMapper.deleteNewsCategoryById(id);
    }
}
