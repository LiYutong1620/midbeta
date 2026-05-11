package com.example.service;

import java.util.List;
import com.example.domain.NewsComment;

/**
 * 评论审核Service接口
 * 
 * @author tong
 * @date 2026-05-08
 */
public interface INewsCommentService 
{
    /**
     * 查询评论审核
     * 
     * @param id 评论审核主键
     * @return 评论审核
     */
    public NewsComment selectNewsCommentById(Long id);

    /**
     * 查询评论审核列表
     * 
     * @param newsComment 评论审核
     * @return 评论审核集合
     */
    public List<NewsComment> selectNewsCommentList(NewsComment newsComment);

    /**
     * 新增评论审核
     * 
     * @param newsComment 评论审核
     * @return 结果
     */
    public int insertNewsComment(NewsComment newsComment);

    /**
     * 修改评论审核
     * 
     * @param newsComment 评论审核
     * @return 结果
     */
    public int updateNewsComment(NewsComment newsComment);

    /**
     * 批量删除评论审核
     * 
     * @param ids 需要删除的评论审核主键集合
     * @return 结果
     */
    public int deleteNewsCommentByIds(Long[] ids);

    /**
     * 删除评论审核信息
     * 
     * @param id 评论审核主键
     * @return 结果
     */
    public int deleteNewsCommentById(Long id);
}
