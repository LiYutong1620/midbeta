package com.example.service;

import java.util.List;
import com.example.domain.NewsLike;

/**
 * 点赞记录Service接口
 * 
 * @author tong
 * @date 2026-05-08
 */
public interface INewsLikeService 
{
    /**
     * 查询点赞记录
     * 
     * @param id 点赞记录主键
     * @return 点赞记录
     */
    public NewsLike selectNewsLikeById(Long id);

    /**
     * 查询点赞记录列表
     * 
     * @param newsLike 点赞记录
     * @return 点赞记录集合
     */
    public List<NewsLike> selectNewsLikeList(NewsLike newsLike);

    /**
     * 新增点赞记录
     * 
     * @param newsLike 点赞记录
     * @return 结果
     */
    public int insertNewsLike(NewsLike newsLike);

    /**
     * 修改点赞记录
     * 
     * @param newsLike 点赞记录
     * @return 结果
     */
    public int updateNewsLike(NewsLike newsLike);

    /**
     * 批量删除点赞记录
     * 
     * @param ids 需要删除的点赞记录主键集合
     * @return 结果
     */
    public int deleteNewsLikeByIds(Long[] ids);

    /**
     * 删除点赞记录信息
     * 
     * @param id 点赞记录主键
     * @return 结果
     */
    public int deleteNewsLikeById(Long id);
}
