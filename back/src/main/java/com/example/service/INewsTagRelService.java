package com.example.service;

import java.util.List;
import com.example.domain.NewsTagRel;

/**
 * 新闻标签关联Service接口
 * 
 * @author tong
 * @date 2026-05-09
 */
public interface INewsTagRelService 
{
    /**
     * 查询新闻标签关联
     * 
     * @param id 新闻标签关联主键
     * @return 新闻标签关联
     */
    public NewsTagRel selectNewsTagRelById(Long id);

    /**
     * 查询新闻标签关联列表
     * 
     * @param newsTagRel 新闻标签关联
     * @return 新闻标签关联集合
     */
    public List<NewsTagRel> selectNewsTagRelList(NewsTagRel newsTagRel);

    /**
     * 新增新闻标签关联
     * 
     * @param newsTagRel 新闻标签关联
     * @return 结果
     */
    public int insertNewsTagRel(NewsTagRel newsTagRel);

    /**
     * 修改新闻标签关联
     * 
     * @param newsTagRel 新闻标签关联
     * @return 结果
     */
    public int updateNewsTagRel(NewsTagRel newsTagRel);

    /**
     * 批量删除新闻标签关联
     * 
     * @param ids 需要删除的新闻标签关联主键集合
     * @return 结果
     */
    public int deleteNewsTagRelByIds(Long[] ids);

    /**
     * 删除新闻标签关联信息
     * 
     * @param id 新闻标签关联主键
     * @return 结果
     */
    public int deleteNewsTagRelById(Long id);
}
