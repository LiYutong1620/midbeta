package com.example.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapper.NewsTagMapper;
import com.example.domain.NewsTag;
import com.example.service.INewsTagService;

/**
 * 标签管理Service业务层处理
 * 
 * @author tong
 * @date 2026-05-09
 */
@Service
public class NewsTagServiceImpl implements INewsTagService 
{
    @Autowired
    private NewsTagMapper newsTagMapper;

    /**
     * 查询标签管理
     * 
     * @param id 标签管理主键
     * @return 标签管理
     */
    @Override
    public NewsTag selectNewsTagById(Long id)
    {
        return newsTagMapper.selectNewsTagById(id);
    }

    /**
     * 查询标签管理列表
     * 
     * @param newsTag 标签管理
     * @return 标签管理
     */
    @Override
    public List<NewsTag> selectNewsTagList(NewsTag newsTag)
    {
        return newsTagMapper.selectNewsTagList(newsTag);
    }

    /**
     * 新增标签管理
     * 
     * @param newsTag 标签管理
     * @return 结果
     */
    @Override
    public int insertNewsTag(NewsTag newsTag)
    {
        return newsTagMapper.insertNewsTag(newsTag);
    }

    /**
     * 修改标签管理
     * 
     * @param newsTag 标签管理
     * @return 结果
     */
    @Override
    public int updateNewsTag(NewsTag newsTag)
    {
        return newsTagMapper.updateNewsTag(newsTag);
    }

    /**
     * 批量删除标签管理
     * 
     * @param ids 需要删除的标签管理主键
     * @return 结果
     */
    @Override
    public int deleteNewsTagByIds(Long[] ids)
    {
        return newsTagMapper.deleteNewsTagByIds(ids);
    }

    /**
     * 删除标签管理信息
     * 
     * @param id 标签管理主键
     * @return 结果
     */
    @Override
    public int deleteNewsTagById(Long id)
    {
        return newsTagMapper.deleteNewsTagById(id);
    }
}
