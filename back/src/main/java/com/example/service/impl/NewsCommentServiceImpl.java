package com.example.service.impl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapper.NewsCommentMapper;
import com.example.domain.NewsComment;
import com.example.service.INewsCommentService;
import com.example.service.ISysConfigService;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * 评论审核Service业务层处理
 *
 * @author tong
 * @date 2026-05-08
 */
@Service
public class NewsCommentServiceImpl implements INewsCommentService
{
    @Autowired
    private NewsCommentMapper newsCommentMapper;

    @Autowired
    private ISysConfigService configService;

    /**
     * 查询评论审核
     *
     * @param id 评论审核主键
     * @return 评论审核
     */
    @Override
    public NewsComment selectNewsCommentById(Long id)
    {
        return newsCommentMapper.selectNewsCommentById(id);
    }

    /**
     * 查询评论审核列表
     *
     * @param newsComment 评论审核
     * @return 评论审核
     */
    @Override
    public List<NewsComment> selectNewsCommentList(NewsComment newsComment)
    {
        return newsCommentMapper.selectNewsCommentList(newsComment);
    }

    /**
     * 新增评论审核
     *
     * @param newsComment 评论审核
     * @return 结果
     */
    @Override
    public int insertNewsComment(NewsComment newsComment)
    {
        // 敏感词过滤
        String content = newsComment.getContent();
        if (content != null) {
            String sensitiveWords = configService.selectConfigByKey("sensitive_words");
            if (sensitiveWords != null && !sensitiveWords.isEmpty()) {
                String[] words = sensitiveWords.split(",");
                for (String word : words) {
                    if (word != null && !word.trim().isEmpty()) {
                        content = content.replace(word, "**");
                    }
                }
                newsComment.setContent(content);
            }
        }
        newsComment.setCreatedAt(new Date());
        return newsCommentMapper.insertNewsComment(newsComment);
    }

    /**
     * 修改评论审核
     *
     * @param newsComment 评论审核
     * @return 结果
     */
    @Override
    public int updateNewsComment(NewsComment newsComment)
    {
        // 管理员审核逻辑：自动填充审核人和审核时间
        if (newsComment.getAuditStatus() != null && newsComment.getAuditStatus() != 0) {
            newsComment.setReviewerId(SecurityUtils.getUserId());
            newsComment.setReviewTime(new Date());
        }
        return newsCommentMapper.updateNewsComment(newsComment);
    }

    /**
     * 批量删除评论审核
     *
     * @param ids 需要删除的评论审核主键
     * @return 结果
     */
    @Override
    public int deleteNewsCommentByIds(Long[] ids)
    {
        return newsCommentMapper.deleteNewsCommentByIds(ids);
    }

    /**
     * 删除评论审核信息
     *
     * @param id 评论审核主键
     * @return 结果
     */
    @Override
    public int deleteNewsCommentById(Long id)
    {
        return newsCommentMapper.deleteNewsCommentById(id);
    }
}
