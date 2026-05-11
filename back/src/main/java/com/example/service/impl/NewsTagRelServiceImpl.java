package com.example.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapper.NewsTagRelMapper;
import com.example.domain.NewsTagRel;
import com.example.service.INewsTagRelService;

/**
 * 新闻标签关联Service业务层处理
 * 
 * @author tong
 * @date 2026-05-09
 */
@Service
public class NewsTagRelServiceImpl implements INewsTagRelService 
{
    @Autowired
    private NewsTagRelMapper newsTagRelMapper;

    /**
     * 查询新闻标签关联
     * 
     * @param id 新闻标签关联主键
     * @return 新闻标签关联
     */
    @Override
    public NewsTagRel selectNewsTagRelById(Long id)
    {
        return newsTagRelMapper.selectNewsTagRelById(id);
    }

    /**
     * 查询新闻标签关联列表
     * 
     * @param newsTagRel 新闻标签关联
     * @return 新闻标签关联
     */
    @Override
    public List<NewsTagRel> selectNewsTagRelList(NewsTagRel newsTagRel)
    {
        return newsTagRelMapper.selectNewsTagRelList(newsTagRel);
    }

    /**
     * 新增新闻标签关联
     * 
     * @param newsTagRel 新闻标签关联
     * @return 结果
     */
    @Override
    public int insertNewsTagRel(NewsTagRel newsTagRel)
    {
        return newsTagRelMapper.insertNewsTagRel(newsTagRel);
    }

    /**
     * 修改新闻标签关联
     * 
     * @param newsTagRel 新闻标签关联
     * @return 结果
     */
    @Override
    public int updateNewsTagRel(NewsTagRel newsTagRel)
    {
        return newsTagRelMapper.updateNewsTagRel(newsTagRel);
    }

    /**
     * 批量删除新闻标签关联
     * 
     * @param ids 需要删除的新闻标签关联主键
     * @return 结果
     */
    @Override
    public int deleteNewsTagRelByIds(Long[] ids)
    {
        return newsTagRelMapper.deleteNewsTagRelByIds(ids);
    }

    /**
     * 删除新闻标签关联信息
     * 
     * @param id 新闻标签关联主键
     * @return 结果
     */
    @Override
    public int deleteNewsTagRelById(Long id)
    {
        return newsTagRelMapper.deleteNewsTagRelById(id);
    }
}
